package pack.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pack.dto.WorkoutRecordsDto;

@Entity
@Table(name="Workout_Records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRecords {
	// 운동 기록 고유 ID (자동 증가) 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 (Users 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 운동 계획 (Daily_Workout_Plans 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private DailyWorkoutPlans workoutPlan;

    // 운동 종류 (Workout_Types 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "workout_type_id", nullable = false)
    private WorkoutTypes workoutType;

    // 수행한 세트 수 
    @Column(nullable = false)
    private int sets;

    // 세트 당 반복 횟수 
    @Column(nullable = false)
    private int reps;

    // 세트 당 중량 (kg, 선택 사항) 
    @Column
    private Double weight;

    // 운동 완료 시간 
    @Column(nullable = false)
    private LocalDateTime completedAt = LocalDateTime.now();

    // 운동 기록 생성 시간 
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 운동 기록 수정 시간 
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 업데이트 발생 시, updatedAt 값을 현재 시간으로 변경 
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    
    // 엔터티 → DTO 변환 
    public WorkoutRecordsDto toDto() {
        return WorkoutRecordsDto.builder()
                .id(this.id)
                .username(this.user.getUsername())
                .workoutName(this.workoutPlan.getWorkoutType().getWorkoutName())
                .sets(this.sets)
                .reps(this.reps)
                .weight(this.weight)
                .completedAt(this.completedAt)
                .build();
    }
}
