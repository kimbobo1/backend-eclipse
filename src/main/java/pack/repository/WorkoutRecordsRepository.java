package pack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pack.entity.WorkoutRecords;

@Repository
public interface WorkoutRecordsRepository extends JpaRepository<WorkoutRecords, Long>{
	List<WorkoutRecords> findByUserId(Long userId);
}
