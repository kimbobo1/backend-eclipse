package pack.model;


import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pack.dto.UserDto;
import pack.entity.User;
import pack.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserModel {
    private final UserRepository userRepository;

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::toDto).orElse(null);
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserDto.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return savedUser.toDto();
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            User updatedUser = userRepository.save(user);
            return updatedUser.toDto();
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            userRepository.delete(existingUser.get());
            return true;
        }
        return false;
    }
}
