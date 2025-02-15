package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  
@AllArgsConstructor 
public class LoginResponse {
    private String token;
    private Long userId;
    private String username;
}
