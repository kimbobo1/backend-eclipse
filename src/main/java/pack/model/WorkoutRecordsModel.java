package pack.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dto.WorkoutRecordsDto;
import pack.entity.DailyWorkoutPlans;
import pack.entity.User;
import pack.entity.WorkoutRecords;
import pack.repository.DailyWorkoutPlansRepository;
import pack.repository.UserRepository;
import pack.repository.WorkoutRecordsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutRecordsModel {

    private final WorkoutRecordsRepository workoutRecordsRepository;
    private final UserRepository userRepository;
    private final DailyWorkoutPlansRepository dailyWorkoutPlansRepository;

    // 사용자별 운동 기록 조회
    public List<WorkoutRecordsDto> getRecordsByUserId(Long userId) {
        List<WorkoutRecords> records = workoutRecordsRepository.findByUserId(userId);
        return records.stream()
                .map(WorkoutRecords::toDto)
                .collect(Collectors.toList());
    }

    // 특정 운동 기록 조회
    public WorkoutRecordsDto getRecordById(Long id) {
        Optional<WorkoutRecords> record = workoutRecordsRepository.findById(id);
        return record.map(WorkoutRecords::toDto).orElse(null);
    }

    // 운동 기록 생성
    public WorkoutRecordsDto createRecord(WorkoutRecordsDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DailyWorkoutPlans workoutPlan = dailyWorkoutPlansRepository.findById(dto.getWorkoutPlanId())
                .orElseThrow(() -> new RuntimeException("Workout Plan not found"));

        WorkoutRecords savedRecord = workoutRecordsRepository.save(WorkoutRecordsDto.toEntity(dto, user, workoutPlan));
        return savedRecord.toDto();
    }

    // 운동 기록 수정
    public WorkoutRecordsDto updateRecord(Long id, WorkoutRecordsDto dto) {
        WorkoutRecords record = workoutRecordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout Record not found"));

        record.setSets(dto.getSets());
        record.setReps(dto.getReps());
        record.setWeight(dto.getWeight());

        WorkoutRecords updatedRecord = workoutRecordsRepository.save(record);
        return updatedRecord.toDto();
    }

    // 운동 기록 삭제
    public boolean deleteRecord(Long id) {
        if (!workoutRecordsRepository.existsById(id)) {
            return false;
        }
        workoutRecordsRepository.deleteById(id);
        return true;
    }
}
