package pack;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import pack.dto.DailyWorkoutPlansDto;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // 특정 날짜의 운동 계획 조회 테스트
    @Test
    public void testGetWorkoutPlansByDate_Success() throws Exception {
        Long userId = 1L;
        String date = "2025-02-14";
        
        mockMvc.perform(get("/api/workout-plans/user/{userId}/date", userId)
                .param("date", date)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());
    }
    
    // 운동 계획 추가 테스트
    @Test
    public void testAddWorkoutPlan_Success() throws Exception {
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
    
    // 운동 계획 삭제 테스트 (존재하는 운동 계획 ID를 가정)
    @Test
    public void testDeleteWorkoutPlan_Success() throws Exception {
        Long planId = 1L; // 테스트 전 미리 planId=1인 데이터가 존재해야 함
        
        mockMvc.perform(delete("/api/workout-plans/{id}", planId)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isNoContent());
    }
    
    // 운동 통계 조회 테스트
    @Test
    public void testGetWorkoutStats_Success() throws Exception {
        Long userId = 1L;
        String date = "2025-02-14";
        
        mockMvc.perform(get("/api/workout-plans/stats/{userId}", userId)
                .param("date", date)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalSets").exists())
            .andExpect(jsonPath("$.totalReps").exists())
            .andExpect(jsonPath("$.totalWeight").exists());
    }
    
    // 운동 날짜 목록 조회 테스트
    @Test
    public void testGetWorkoutDatesByUser_Success() throws Exception {
        Long userId = 1L;
        
        mockMvc.perform(get("/api/workout-plans/user/{userId}/dates", userId)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
