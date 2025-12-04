package com.example.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // Tên bảng trong database sẽ là 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng (Auto Increment)
    private Long id;

    @Column(unique = true, nullable = false) // Username là duy nhất và không được để trống
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false) // Email là duy nhất và không được để trống
    private String email;

    // Lưu danh sách các quyền (Roles) của user
    // FetchType.EAGER: Khi lấy User thì lấy luôn cả Roles
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING) // Lưu Role dưới dạng chuỗi (ADMIN, TEACHER...) thay vì số
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
