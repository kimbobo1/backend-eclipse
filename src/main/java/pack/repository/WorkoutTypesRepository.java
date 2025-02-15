package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pack.entity.WorkoutTypes;

import java.util.Optional;

public interface WorkoutTypesRepository extends JpaRepository<WorkoutTypes, Long> {
    Optional<WorkoutTypes> findByWorkoutName(String workoutName); // ✅ 운동명으로 조회하는 메서드 추가
}
