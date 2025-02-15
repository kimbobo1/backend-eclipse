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
import pack.dto.WorkoutGoalsDto;

@Entity
@Table(name = "Workout_Goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutGoals {
	 // 운동 목표 고유 ID (자동 증가) 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 (Users 테이블 참조) 
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 목표 이름 (예: 100 푸시업 도전) 
    @Column(nullable = false, length = 100)
    private String goalName;

    // 목표 세트 수 
    @Column
    private Integer targetSets;

    // 목표 반복 횟수 
    @Column
    private Integer targetReps;

    // 목표 운동 시간 (분 단위) 
    @Column
    private Integer targetDuration;

    // 현재 진행된 세트 수 
    @Column(nullable = false)
    private int currentSets = 0;

    // 현재 진행된 반복 횟수 
    @Column(nullable = false)
    private int currentReps = 0;

    // 현재 운동 시간 (분 단위) 
    @Column(nullable = false)
    private int currentDuration = 0;

    // 목표 달성 여부 (기본값: false) 
    @Column(nullable = false)
    private boolean goalAchieved = false;

    // 목표 생성 시간 
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 목표 수정 시간 
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 업데이트 발생 시, updatedAt 값을 현재 시간으로 변경 
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    
    
    
 // 엔티티 → DTO 변환 메서드
    public WorkoutGoalsDto toDto() {
        return WorkoutGoalsDto.builder()
                .id(this.id)
                .username(this.user.getUsername()) // User 객체에서 username 추출
                .goalName(this.goalName)
                .targetSets(this.targetSets)
                .targetReps(this.targetReps)
                .targetDuration(this.targetDuration)
                .currentSets(this.currentSets)
                .currentReps(this.currentReps)
                .currentDuration(this.currentDuration)
                .goalAchieved(this.goalAchieved)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

}
