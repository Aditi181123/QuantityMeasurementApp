import { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/api';

const AuthContext = createContext(null);

/**
 * AuthProvider
 * Manages authentication state and provides auth methods to all components
 */
export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Check for existing session on mount
  useEffect(() => {
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userId = localStorage.getItem('userId');

    if (token && username) {
      setUser({ token, username, userId });
    }
    setLoading(false);
  }, []);

  /**
   * Login user
   */
  const login = async (username, password) => {
    const response = await authService.login(username, password);
    
    // Store in localStorage
    localStorage.setItem('token', response.token);
    localStorage.setItem('username', response.username);
    localStorage.setItem('userId', response.userId);

    // Update state
    setUser({
      token: response.token,
      username: response.username,
      userId: response.userId,
    });

    return response;
  };

  /**
   * Signup user
   */
  const signup = async (username, email, password) => {
    const response = await authService.signup(username, email, password);
    
    localStorage.setItem('token', response.token);
    localStorage.setItem('username', response.username);
    localStorage.setItem('userId', response.userId);

    setUser({
      token: response.token,
      username: response.username,
      userId: response.userId,
    });

    return response;
  };

  /**
   * Google OAuth login
   */
  const googleLogin = async (googleUser) => {
    const response = await authService.googleAuth(googleUser);
    
    localStorage.setItem('token', response.token);
    localStorage.setItem('username', response.username);
    localStorage.setItem('userId', response.userId);

    setUser({
      token: response.token,
      username: response.username,
      userId: response.userId,
    });

    return response;
  };

  /**
   * Logout user
   */
  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('userId');
    setUser(null);
  };

  const value = {
    user,
    loading,
    login,
    signup,
    googleLogin,
    logout,
    isAuthenticated: !!user,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

/**
 * useAuth hook
 * Access auth context in any component
 */
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
