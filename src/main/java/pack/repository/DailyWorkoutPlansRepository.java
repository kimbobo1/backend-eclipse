package pack.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.DailyWorkoutPlans;

@Repository
public interface DailyWorkoutPlansRepository extends JpaRepository<DailyWorkoutPlans, Long>{
	 List<DailyWorkoutPlans> findByUserIdAndDate(Long userId, LocalDate date);
	List<DailyWorkoutPlans> findByUserId(Long userId);
}
