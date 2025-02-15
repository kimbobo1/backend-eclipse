package pack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.DailyWorkoutPlans;
import pack.entity.WorkoutSet;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutSetDto {
    private Long id;          // 세트 고유 ID
    private Long planId;      // 운동 계획 ID (DailyWorkoutPlans 참조)
    private int setNumber;    // 몇 번째 세트인지 (1세트, 2세트, 3세트 등)
    private double weight;    // 무게 (kg)
    private int reps;         // 반복 횟수
    private boolean completed; // 완료 여부

    // ✅ DTO  엔터티 변환
    public static WorkoutSet toEntity(WorkoutSetDto dto, DailyWorkoutPlans plan) {
        return WorkoutSet.builder()
                .id(dto.getId())
                .plan(plan)
                .setNumber(dto.getSetNumber())
                .weight(dto.getWeight())
                .reps(dto.getReps())
                .completed(dto.isCompleted())
                .build();
    }

   
}
