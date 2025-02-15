package pack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pack.dto.LoginRequest;
import pack.dto.LoginResponse;
import pack.dto.UserDto;
import pack.entity.User;
import pack.repository.UserRepository;
import pack.util.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    //  특정 사용자 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(value.toDto()))
                   .orElse(ResponseEntity.notFound().build());
    }

    //  회원가입 (사용자 등록) API
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("{\"error\": \"User already exists\"}");
        }
        
        // 비밀번호 암호화 후 사용자 생성
        User newUser = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
                
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser.toDto());
    }

    //  사용자 정보 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            if(userDto.getPassword() != null && !userDto.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userRepository.save(user);
            return ResponseEntity.ok(user.toDto());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //  사용자 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //  로그인 API (토큰 발급 + 사용자 ID 반환)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());

                //  로그인 시 사용자 ID도 반환
                return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getUsername()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Invalid credentials\"}");
    }

    //  현재 로그인된 사용자 정보 조회 (JWT 토큰 검증)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        System.out.println("🚀 현재 로그인된 사용자 정보 요청!");

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"토큰이 없습니다.\"}");
        }

        //  JWT 토큰에서 이메일 추출
        String email = jwtUtil.extractUsername(token.substring(7)); // "Bearer " 제거

        //  데이터베이스에서 사용자 찾기
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"사용자를 찾을 수 없습니다.\"}");
        }

        return ResponseEntity.ok(user.get().toDto());
    }
}
