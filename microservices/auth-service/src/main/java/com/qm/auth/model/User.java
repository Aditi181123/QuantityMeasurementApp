package com.qm.auth.model;

import jakarta.persistence.*;  // JPA annotations for ORM mapping

/**
 * @Entity - Marks this class as a JPA entity (mapped to a database table)
 * @Table - Specifies the database table name (optional if name matches class)
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    /**
     * @Column - Maps to database column
     * unique = true - No two users can have the same username
     * nullable = false - Username is required
     */
    @Column(unique = true, nullable = false)
    private String username;  // Login username

    /**
     * unique = true - Email must be unique in the system
     * nullable = false - Email is required
     */
    @Column(unique = true, nullable = false)
    private String email;  // User's email address

    /**
     * nullable = false - Password is required (even for OAuth users, we store a hash)
     * Note: For OAuth users, this contains a placeholder hash, not their real password
     */
    @Column(nullable = false)
    private String password;  // BCrypt hashed password

    
    /**
     * OAuth provider name (e.g., "google", "github")
     * NULL for regular username/password users
     */
    private String provider;

    /**
     * User's ID in the OAuth provider's system
     * Example: Google's user ID string
     * Combined with provider, this is unique across OAuth users
     */
    private String providerId;

    /**
     * @Column - Specifies column name (created_at in database)
     * User creation timestamp
     */
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    /**
     * Last successful login timestamp
     */
    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;


    /**
     * Default constructor required by JPA
     * JPA needs this to instantiate entities when loading from database
     */
    public User() {
        this.createdAt = java.time.LocalDateTime.now();
    }

    /**
     * Constructor for creating regular (non-OAuth) users
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = java.time.LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }

    public java.time.LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(java.time.LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
}
