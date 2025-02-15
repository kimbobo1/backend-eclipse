package pack.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import pack.entity.DailyWorkoutPlans;
import pack.entity.User;
import pack.entity.WorkoutRecords;

@Getter
@Builder
public class WorkoutRecordsDto {
	private Long id;
    private String username;
    private String workoutName;
    private int sets;
    private int reps;
    private Double weight;
    private LocalDateTime completedAt;
    private long userId;
    private Long workoutPlanId;
    
    
    // DTO  엔터티 변환 
    public static WorkoutRecords toEntity(WorkoutRecordsDto dto, User user, DailyWorkoutPlans workoutPlan) {
        return WorkoutRecords.builder()
                .id(dto.getId())
                .user(user)
                .workoutPlan(workoutPlan)
                .sets(dto.getSets())
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .completedAt(dto.getCompletedAt())
                .build();
    }
}
