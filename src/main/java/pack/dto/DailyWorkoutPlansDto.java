package pack.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.entity.DailyWorkoutPlans;
import pack.entity.User;
import pack.entity.WorkoutTypes;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutPlansDto {
    private Long id;              // 운동 계획 ID
    private String username;       // 사용자 이름
    private String workoutName;    // 운동 이름
    private LocalDate date;        // 운동 날짜
    private int sets;              // 목표 세트 수
    private int reps;              // 목표 반복 횟수
    private Double weight;         // 세트 당 무게
    private boolean completed;     // 완료 여부
    private Long userId;           // 사용자 ID
    private Long workoutTypeId;    // 운동 종류 ID
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간
    private List<WorkoutSetDto> workoutSets; //  운동 세트 리스트 추가

    // DTO → 엔터티 변환
    public static DailyWorkoutPlans toEntity(DailyWorkoutPlansDto dto, User user, WorkoutTypes workoutType) {
        return DailyWorkoutPlans.builder()
                .id(dto.getId())
                .user(user)
                .workoutType(workoutType)
                .date(dto.getDate())
                .sets(dto.getSets())
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .completed(dto.isCompleted())
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now()) // ✅ 기본값 설정
                .updatedAt(LocalDateTime.now()) // ✅ 수정 시간 기본값 설정
                .build();
    }
}
