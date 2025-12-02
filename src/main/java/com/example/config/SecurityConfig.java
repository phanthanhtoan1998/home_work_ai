package com.example.config;

import com.example.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Cấu hình bộ lọc bảo mật (Security Filter Chain)
    // Đây là nơi quy định các quy tắc bảo mật cho từng đường dẫn (URL)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF để đơn giản hóa việc test API (trong thực tế nên bật)
                .authorizeHttpRequests(auth -> auth
                        // Cho phép truy cập tự do (không cần đăng nhập) vào các đường dẫn này
                        .requestMatchers("/api/auth/**", "/register", "/login", "/css/**", "/js/**").permitAll()

                        // Chỉ cho phép user có quyền ADMIN truy cập vào /admin/**
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Chỉ cho phép user có quyền TEACHER truy cập vào /teacher/**
                        .requestMatchers("/teacher/**").hasRole("TEACHER")

                        // Chỉ cho phép user có quyền STUDENT truy cập vào /student/**
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // Tất cả các request còn lại đều phải đăng nhập mới được truy cập
                        .anyRequest().authenticated())
                // Cấu hình trang đăng nhập (Form Login)
                .formLogin(form -> form
                        .loginPage("/login") // Đường dẫn đến trang login tùy chỉnh (nếu có)
                        .defaultSuccessUrl("/") // Chuyển hướng về trang chủ sau khi đăng nhập thành công
                        .permitAll() // Cho phép tất cả mọi người truy cập trang login
                )
                // Cấu hình đăng xuất (Logout)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Chuyển hướng về trang login sau khi đăng xuất
                        .permitAll());

        return http.build();
    }

    // Bean mã hóa mật khẩu. Sử dụng BCrypt là chuẩn bảo mật hiện nay.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình Provider xác thực, kết nối giữa UserDetailsService và
    // PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Cung cấp cách lấy thông tin user từ DB
        authProvider.setPasswordEncoder(passwordEncoder()); // Cung cấp cách mã hóa/kiểm tra mật khẩu
        return authProvider;
    }

    // Bean quản lý quá trình xác thực chung của hệ thống
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
