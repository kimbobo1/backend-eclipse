package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.WorkoutSetDto;

@Entity
@Table(name = "Workout_Sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 세트 고유 ID (자동 증가)

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false) // DailyWorkoutPlans 엔터티와 연결
    private DailyWorkoutPlans plan;

    @Column(nullable = false)
    private int setNumber; // 몇 번째 세트인지 (1세트, 2세트, 3세트 등)

    @Column(nullable = false)
    private double weight; // 무게 (kg)

    @Column(nullable = false)
    private int reps; // 반복 횟수

    @Column(nullable = false)
    private boolean completed = false; // 완료 여부 (기본값: false)

    // ✅ 세트 완료 상태 변경
    public void completeSet() {
        this.completed = true;
    }

    // ✅ 엔티티 → DTO 변환 메서드
    public WorkoutSetDto toDto() {
        return WorkoutSetDto.builder()
                .id(this.id)
                .planId(this.plan.getId())
                .setNumber(this.setNumber)
                .weight(this.weight)
                .reps(this.reps)
                .completed(this.completed)
                .build();
    }
}
