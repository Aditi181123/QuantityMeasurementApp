package com.qm.auth.security;

import com.qm.auth.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════════════╗
 * ║                                    JWT FILTER                                    ║
 * ║                                                                                  ║
 * ║  HTTP Filter that intercepts every request to validate JWT tokens.             ║
 * ║                                                                                  ║
 * ║  Request Flow:                                                                  ║
 * ║  ┌──────────┐    ┌───────────┐    ┌──────────────┐    ┌────────────────┐     ║
 * ║  │ Request  │───▶│ JWT Filter │───▶│ Controller   │───▶│ Response       │     ║
 * ║  │ + Token  │    │ Validate  │    │ (if valid)   │    │                │     ║
 * ║  └──────────┘    └───────────┘    └──────────────┘    └────────────────┘     ║
 * ║                        │                                                   ║
 * ║                        │ No token or invalid                                ║
 * ║                        ▼                                                   ║
 * ║                   ┌──────────┐                                              ║
 * ║                   │ Continue │ (without authentication)                       ║
 * ║                   │(anonymous)│                                             ║
 * ║                   └──────────┘                                              ║
 * ╚═══════════════════════════════════════════════════════════════════════════════════╝
 */

/**
 * @Component - Register as Spring bean (automatically added to filter chain)
 * 
 * OncePerRequestFilter - Spring's base class for filters
 * Guarantees: Filter runs exactly once per HTTP request
 * (Important for concurrent requests, prevents double-processing)
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
     * DEPENDENCIES
     * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
     */
    
    private final JwtUtil jwtUtil;
    
    /**
     * UserDetailsService - Interface for loading user data
     * Implemented by AuthService to load from database
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection (preferred over @Autowired field injection)
     * 
     * Benefits:
     * - Explicit dependencies
     * - Easier to test (can mock)
     * - Thread-safe
     */
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * ════════════════════════════════════════════════════════════════════════════════
     * FILTER LOGIC
     * ════════════════════════════════════════════════════════════════════════════════
     */

    /**
     * Main filter method - called for every HTTP request
     * 
     * @param request - HTTP request (contains headers, body, etc.)
     * @param response - HTTP response (where we write response)
     * @param filterChain - Next filter/controller in chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        /**
         * Step 1: Extract Authorization header
         * 
         * Format: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
         * 
         * We need to:
         * 1. Check if header exists
         * 2. Check if it starts with "Bearer "
         * 3. Extract the token part
         */
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        /**
         * Extract token from header
         * Only process if header starts with "Bearer "
         */
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Remove "Bearer " prefix (7 characters)
            
            /**
             * Validate token
             * jwtUtil.validateToken() checks:
             * - Signature is valid
             * - Token not expired
             * - Token properly formatted
             */
            if (jwtUtil.validateToken(token)) {
                username = jwtUtil.getUsernameFromToken(token);
            }
        }

        /**
         * Step 2: Set up Spring Security authentication
         * 
         * If we have a valid username:
         * 1. Load user details from database
         * 2. Create authentication token
         * 3. Set in SecurityContext
         * 
         * SecurityContext is ThreadLocal - available throughout request processing
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            /**
             * Load user details from database
             * This includes:
             * - Username
             * - Password (for comparison, but we already validated token)
             * - Authorities (roles)
             */
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            /**
             * UsernamePasswordAuthenticationToken
             * 
             * This is Spring Security's representation of an authenticated user.
             * Three-argument constructor:
             * 1. Principal (user details)
             * 2. Credentials (password - null since we used token)
             * 3. Authorities (roles/permissions)
             */
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                            userDetails,      // Principal
                            null,             // Credentials (null - authenticated via JWT)
                            userDetails.getAuthorities()  // Roles
                    );
            
            /**
             * WebAuthenticationDetailsSource
             * 
             * Attaches additional request details:
             * - Remote IP address
             * - Session ID
             * - Request URI
             * 
             * This information is useful for:
             * - Audit logging
             * - Security monitoring
             * - Detecting suspicious activity
             */
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            /**
             * Set authentication in SecurityContext
             * 
             * Once set:
             * - @PreAuthorize("isAuthenticated()") passes
             * - SecurityContextHolder.getContext().getAuthentication() returns this
             * - @AuthenticationPrincipal returns the user
             */
            SecurityContextHolder.getContext().setAuthentication(authToken);
            
            /**
             * Also store userId in request attributes
             * 
             * This allows controllers to access userId without parsing JWT again:
             * Long userId = (Long) request.getAttribute("userId");
             */
            request.setAttribute("userId", jwtUtil.getUserIdFromToken(token));
        }

        /**
         * Step 3: Continue filter chain
         * 
         * filterChain.doFilter() continues to:
         * - Next filter (if any)
         * - DispatcherServlet
         * - Controller
         * 
         * This is called regardless of authentication status:
         * - If authenticated: controller processes with user context
         * - If not: controller decides if authentication is needed
         */
        filterChain.doFilter(request, response);
    }
}
