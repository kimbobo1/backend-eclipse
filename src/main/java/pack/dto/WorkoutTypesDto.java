package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pack.entity.WorkoutTypes;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor  
@AllArgsConstructor
@Builder
public class WorkoutTypesDto {
    private Long id;
    private String workoutName;
    private String category;
    private String description;
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간

    // DTO 엔터티 변환 메서드
    public static WorkoutTypes toEntity(WorkoutTypesDto dto) {
        return WorkoutTypes.builder()
                .id(dto.getId())
                .workoutName(dto.getWorkoutName())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();
    }
}
