package pack.entity;

import java.time.LocalDate;
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
import pack.dto.DailyWorkoutPlansDto;

@Entity
@Table(name = "Daily_Workout_Plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWorkoutPlans {
	// 운동 계획 고유 ID (자동 증가) 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 (Users 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 운동 날짜 
    @Column(nullable = false)
    private LocalDate date;

    // 운동 종류 (Workout_Types 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "workout_type_id", nullable = false)
    private WorkoutTypes workoutType;

    // 세트 수 
    @Column(nullable = false)
    private int sets;

    // 반복 횟수 
    @Column(nullable = false)
    private int reps;

    // 세트 당 중량 (kg, 선택 사항) 
    @Column
    private Double weight;

    // 운동 완료 여부  
    @Column(nullable = false)
    private boolean completed = false;

    // 운동 계획 생성 시간 
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    // 운동 계획 수정 시간 
 // 운동 계획 수정 시간
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

   
    
    //엔티 → DTO 변환
    public DailyWorkoutPlansDto toDto() {
        return DailyWorkoutPlansDto.builder()
                .id(this.id)
                .username(this.user.getUsername())
                .workoutName(this.workoutType.getWorkoutName())
                .date(this.date)
                .sets(this.sets)
                .reps(this.reps)
                .weight(this.weight)
                .completed(this.completed)
                .userId(this.user.getId())
                .workoutTypeId(this.workoutType.getId())
                .createdAt(this.createdAt)  // ✅ 추가됨
                .updatedAt(this.updatedAt)  // ✅ 추가됨
                .build();
    }

}
