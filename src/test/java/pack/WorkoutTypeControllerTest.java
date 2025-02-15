package pack;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


import pack.dto.WorkoutTypesDto;


@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // 모든 운동 유형 조회 테스트
    @Test
    public void testGetAllWorkoutTypes_Success() throws Exception {
        mockMvc.perform(get("/api/workout-types"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    // 운동 유형 추가 테스트
    @Test
    public void testCreateWorkoutType_Success() throws Exception {
        WorkoutTypesDto dto = new WorkoutTypesDto();
        dto.setWorkoutName("Test Workout");
        dto.setCategory("Test Category");
        dto.setDescription("Test Description");
        
        mockMvc.perform(post("/api/workout-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists());
    }
    
    // 특정 운동 유형 조회 테스트 (id=1이 존재해야 함)
    @Test
    public void testGetWorkoutTypeById_Success() throws Exception {
        mockMvc.perform(get("/api/workout-types/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    // 운동 유형 삭제 테스트 (id=1이 존재해야 함)
    @Test
    public void testDeleteWorkoutType_Success() throws Exception {
        mockMvc.perform(delete("/api/workout-types/{id}", 1))
            .andExpect(status().isNoContent());
    }
    
    // 존재하지 않는 운동 유형 삭제 테스트 (예: id=99999)
    @Test
    public void testDeleteWorkoutType_NotFound() throws Exception {
        mockMvc.perform(delete("/api/workout-types/{id}", 99999))
            .andExpect(status().isNotFound());
    }
}
