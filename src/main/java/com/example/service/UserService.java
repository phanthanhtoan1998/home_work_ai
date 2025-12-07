package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Phương thức đăng ký người dùng mới
    public User registerUser(String username, String password, String email, Set<Role> roles) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Lỗi: Tên đăng nhập đã tồn tại!");
        }
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Lỗi: Email đã được sử dụng!");
        }

        // Tạo đối tượng User mới
        // passwordEncoder.encode(password): Mã hóa mật khẩu trước khi lưu vào DB để bảo
        // mật
        User user = new User(username, passwordEncoder.encode(password), email, roles);

        // Lưu user vào database
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên: " + username));
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String username, User userDetails) {
        User user = findByUsername(username);
        user.setEmail(userDetails.getEmail());
        // Update other fields if needed, but be careful with password
        return userRepository.save(user);
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        User user = findByUsername(username);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
