package pack.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dto.DailyWorkoutPlansDto;
import pack.entity.DailyWorkoutPlans;
import pack.entity.User;
import pack.entity.WorkoutTypes;
import pack.repository.DailyWorkoutPlansRepository;
import pack.repository.UserRepository;
import pack.repository.WorkoutTypesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyWorkoutPlansModel {

    private final DailyWorkoutPlansRepository dailyWorkoutPlansRepository;
    private final UserRepository userRepository;
    private final WorkoutTypesRepository workoutTypesRepository;

    // 사용자별 운동 계획 조회
    public List<DailyWorkoutPlansDto> getWorkoutPlansByUserId(Long userId) {
        List<DailyWorkoutPlans> plans = dailyWorkoutPlansRepository.findByUserId(userId);
        return plans.stream()
                    .map(DailyWorkoutPlans::toDto)  
                    .collect(Collectors.toList());
    }

    // 특정 운동 계획 조회
    public DailyWorkoutPlansDto getWorkoutPlanById(Long id) {
        Optional<DailyWorkoutPlans> plan = dailyWorkoutPlansRepository.findById(id);
        return plan.map(DailyWorkoutPlans::toDto).orElse(null); 
    }

    // 운동 계획 생성
    public DailyWorkoutPlansDto createWorkoutPlan(DailyWorkoutPlansDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutTypes workoutType = workoutTypesRepository.findById(dto.getWorkoutTypeId())
                .orElseThrow(() -> new RuntimeException("Workout Type not found"));

      
        DailyWorkoutPlans savedPlan = dailyWorkoutPlansRepository.save(DailyWorkoutPlansDto.toEntity(dto, user, workoutType));

        return savedPlan.toDto(); 
    }

    // 운동 계획 수정
    public DailyWorkoutPlansDto updateWorkoutPlan(Long id, DailyWorkoutPlansDto dto) {
        DailyWorkoutPlans plan = dailyWorkoutPlansRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout Plan not found"));

        plan.setDate(dto.getDate());
        plan.setSets(dto.getSets());
        plan.setReps(dto.getReps());
        plan.setWeight(dto.getWeight());
        plan.setCompleted(dto.isCompleted());

        // 수정 후 저장
        DailyWorkoutPlans updatedPlan = dailyWorkoutPlansRepository.save(plan);
        return updatedPlan.toDto(); 
    }

    // 운동 계획 삭제
    public boolean deleteWorkoutPlan(Long id) {
        if (!dailyWorkoutPlansRepository.existsById(id)) {
            return false; 
        }
        dailyWorkoutPlansRepository.deleteById(id); 
        return true; 
    }
}
