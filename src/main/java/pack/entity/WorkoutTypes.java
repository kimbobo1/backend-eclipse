package pack.entity;

import jakarta.persistence.*;
import lombok.*;
import pack.dto.WorkoutTypesDto;
import java.time.LocalDateTime;

@Entity
@Table(name = "Workout_Types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String workoutName;

    @Column(nullable = false, length = 50)
    private String category; //  카테고리 추가 

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; //  생성일

    @Column(nullable = false)
    private LocalDateTime updatedAt; //  수정일

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ WorkoutTypes -> WorkoutTypesDto 변환 메서드 추가
    public WorkoutTypesDto toDto() {
        return WorkoutTypesDto.builder()
                .id(this.id)
                .workoutName(this.workoutName)
                .category(this.category)
                .description(this.description)
                .build();
    }
}
