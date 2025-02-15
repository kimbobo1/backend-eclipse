package pack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pack.dto.WorkoutGoalsDto;
import pack.entity.User;
import pack.entity.WorkoutGoals;
import pack.repository.UserRepository;
import pack.repository.WorkoutGoalsRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/workout-goals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class WorkoutGoalsController {

    private final WorkoutGoalsRepository workoutGoalsRepository;
    private final UserRepository userRepository;

    //  1. 특정 사용자 운동 목표 조회 (GET)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutGoalsDto>> getWorkoutGoalsByUser(@PathVariable long userId) {
        List<WorkoutGoalsDto> goals = workoutGoalsRepository.findByUserId(userId)
                .stream()
                .map(WorkoutGoals::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(goals);
    }

    //  2. 운동 목표 추가 (POST)
    @PostMapping
    public ResponseEntity<?> createWorkoutGoal(@RequestBody WorkoutGoalsDto dto) {
        System.out.println("🚀 운동 목표 추가 요청: " + dto);

        // ✅ 사용자 찾기
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"사용자를 찾을 수 없습니다.\"}");
        }

        User user = userOptional.get();
        WorkoutGoals newGoal = WorkoutGoalsDto.toEntity(dto, user);
        workoutGoalsRepository.save(newGoal);

        return ResponseEntity.ok(newGoal.toDto());
    }

    //  3. 운동 목표 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutGoal(@PathVariable Long id, @RequestBody WorkoutGoalsDto dto) {
        System.out.println("🚀 운동 목표 수정 요청: id=" + id + ", dto=" + dto);

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

        WorkoutGoals updatedGoal = workoutGoalsRepository.save(goal);
        return ResponseEntity.ok(updatedGoal.toDto());
    }

    //  4. 운동 목표 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutGoal(@PathVariable Long id) {
        System.out.println("🚀 운동 목표 삭제 요청: id=" + id);

        if (!workoutGoalsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        workoutGoalsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
 //  목표 달성 여부 업데이트 (PATCH)
    @PatchMapping("/{id}/achieve")
    public ResponseEntity<?> updateGoalAchieved(@PathVariable Long id, @RequestBody boolean achieved) {
        System.out.println("🚀 목표 달성 상태 변경 요청: id=" + id + ", achieved=" + achieved);

        Optional<WorkoutGoals> goalOptional = workoutGoalsRepository.findById(id);
        if (goalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WorkoutGoals goal = goalOptional.get();
        goal.setGoalAchieved(achieved);
        workoutGoalsRepository.save(goal);

        return ResponseEntity.ok(goal.toDto());
    }
    @PatchMapping("/{id}/increase")
    public ResponseEntity<?> increaseCurrentSets(@PathVariable Long id) {
        Optional<WorkoutGoals> goalOptional = workoutGoalsRepository.findById(id);
        if (goalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WorkoutGoals goal = goalOptional.get();
        if (goal.getCurrentSets() < goal.getTargetSets()) {
            goal.setCurrentSets(goal.getCurrentSets() + 1);
            workoutGoalsRepository.save(goal);
        }

        return ResponseEntity.ok(goal.toDto());
    }
}
