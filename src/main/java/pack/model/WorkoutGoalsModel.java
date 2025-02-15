package pack.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dto.WorkoutGoalsDto;
import pack.entity.User;
import pack.entity.WorkoutGoals;
import pack.repository.UserRepository;
import pack.repository.WorkoutGoalsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutGoalsModel {

    private final WorkoutGoalsRepository workoutGoalsRepository;
    private final UserRepository userRepository;

    // 사용자별 운동 목표 조회
    public List<WorkoutGoalsDto> getGoalsByUserId(Long userId) {
        List<WorkoutGoals> goals = workoutGoalsRepository.findByUserId(userId);
        return goals.stream()
                    .map(WorkoutGoals::toDto) 
                    .collect(Collectors.toList());
    }

    // 특정 운동 목표 조회
    public WorkoutGoalsDto getGoalById(Long id) {
        Optional<WorkoutGoals> goal = workoutGoalsRepository.findById(id);
        return goal.map(WorkoutGoals::toDto).orElse(null); 
    }

    // 운동 목표 생성
    public WorkoutGoalsDto createGoal(WorkoutGoalsDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // DTO → 엔터티 변환 후 저장
        WorkoutGoals savedGoal = workoutGoalsRepository.save(WorkoutGoalsDto.toEntity(dto, user));

        return savedGoal.toDto(); 
    }

    // 운동 목표 수정
    public WorkoutGoalsDto updateGoal(Long id, WorkoutGoalsDto dto) {
        WorkoutGoals goal = workoutGoalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout Goal not found"));

        goal.setGoalName(dto.getGoalName());
        goal.setTargetSets(dto.getTargetSets());
        goal.setTargetReps(dto.getTargetReps());
        goal.setTargetDuration(dto.getTargetDuration());
        goal.setCurrentSets(dto.getCurrentSets());
        goal.setCurrentReps(dto.getCurrentReps());
        goal.setCurrentDuration(dto.getCurrentDuration());
        goal.setGoalAchieved(dto.isGoalAchieved());

        // 수정 후 저장
        WorkoutGoals updatedGoal = workoutGoalsRepository.save(goal);
        return updatedGoal.toDto();
    }

    // 운동 목표 삭제
    public boolean deleteGoal(Long id) {
        if (!workoutGoalsRepository.existsById(id)) {
            return false; 
        }
        workoutGoalsRepository.deleteById(id);
        return true; 
    }
}
