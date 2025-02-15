package pack.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import pack.entity.User;
import pack.entity.WorkoutGoals;

@Getter
@Builder
public class WorkoutGoalsDto {
    private Long id;
    private String username;
    private String goalName;
    private int targetSets;
    private int targetReps;
    private int targetDuration;
    private int currentSets;
    private int currentReps;
    private int currentDuration;
    private boolean goalAchieved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    // DTO  엔티티 변환
    public static WorkoutGoals toEntity(WorkoutGoalsDto dto, User user) {
        return WorkoutGoals.builder()
                .user(user)
                .goalName(dto.getGoalName())
                .targetSets(dto.getTargetSets())
                .targetReps(dto.getTargetReps())
                .targetDuration(dto.getTargetDuration())
                .currentSets(dto.getCurrentSets())
                .currentReps(dto.getCurrentReps())
                .currentDuration(dto.getCurrentDuration())
                .goalAchieved(dto.isGoalAchieved())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
