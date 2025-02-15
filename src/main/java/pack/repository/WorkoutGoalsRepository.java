package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.WorkoutGoals;

@Repository
public interface WorkoutGoalsRepository extends JpaRepository<WorkoutGoals, Long>{
	List<WorkoutGoals> findByUserId(Long userId);

    // ✅ 특정 사용자의 목표 개수 조회
    long countByUserId(Long userId);

    // ✅ 특정 사용자의 완료된 목표 개수 조회
    long countByUserIdAndGoalAchievedTrue(Long userId);
}
