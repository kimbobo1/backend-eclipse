package pack;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pack.dto.DailyWorkoutPlansDto; // 실제 DTO 패키지 경로에 맞게 수정

@SpringBootTest
@AutoConfigureMockMvc
class Kimbobo1ApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void contextLoads() {
        // 애플리케이션 컨텍스트 로드 테스트
    }
    
    @Test
    void testGetWorkoutPlansByDate_Success() throws Exception {
        Long userId = 1L;
        String date = "2025-02-14";
        
        mockMvc.perform(get("/api/workout-plans/user/{userId}/date", userId)
                .param("date", date)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    void testAddWorkoutPlan_Success() throws Exception {
        // DTO 생성 - 실제 필드 및 패키지명은 프로젝트에 맞게 수정하세요.
        DailyWorkoutPlansDto dto = new DailyWorkoutPlansDto();
        dto.setUserId(1L);
        dto.setWorkoutTypeId(2L);
        dto.setDate(LocalDate.of(2025, 2, 14));
        dto.setSets(3);
        dto.setReps(10);
        dto.setWeight(50.0);
        dto.setCompleted(false);
        
        mockMvc.perform(post("/api/workout-plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }
}
