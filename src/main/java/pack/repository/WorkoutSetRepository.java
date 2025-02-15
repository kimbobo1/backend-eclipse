package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.entity.WorkoutSet;
import pack.entity.DailyWorkoutPlans;
import java.util.List;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
    // ✅ DailyWorkoutPlans의 ID를 기준으로 WorkoutSet 조회
    List<WorkoutSet> findByPlan(DailyWorkoutPlans plan);
}
