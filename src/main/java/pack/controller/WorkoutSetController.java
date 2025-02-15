package pack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.WorkoutSetDto;
import pack.entity.DailyWorkoutPlans;
import pack.entity.WorkoutSet;
import pack.repository.DailyWorkoutPlansRepository;
import pack.repository.WorkoutSetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workout-sets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class WorkoutSetController {

    private final WorkoutSetRepository workoutSetRepository;
    private final DailyWorkoutPlansRepository dailyWorkoutPlansRepository;

    //  1. 특정 운동 계획의 모든 세트 가져오기
    @GetMapping("/plan/{planId}")
    public ResponseEntity<?> getWorkoutSetsByPlan(@PathVariable Long planId) {
        Optional<DailyWorkoutPlans> plan = dailyWorkoutPlansRepository.findById(planId);
        if (plan.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"운동 계획을 찾을 수 없습니다.\"}");
        }

        List<WorkoutSetDto> workoutSets = workoutSetRepository.findByPlan(plan.get())
                .stream()
                .map(WorkoutSet::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(workoutSets);
    }
    //  2. 세트 추가 (운동 계획에 새로운 세트 추가)
    @PostMapping("/plan/{planId}")
    public ResponseEntity<?> addWorkoutSet(@PathVariable Long planId, @RequestBody WorkoutSetDto dto) {
        Optional<DailyWorkoutPlans> planOpt = dailyWorkoutPlansRepository.findById(planId);
        if (planOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"운동 계획을 찾을 수 없습니다.\"}");
        }

        DailyWorkoutPlans plan = planOpt.get();
        WorkoutSet newSet = WorkoutSetDto.toEntity(dto, plan);
        workoutSetRepository.save(newSet);

        return ResponseEntity.ok(newSet.toDto());
    }

    //  3. 세트 삭제 (ID로 삭제)
    @DeleteMapping("/{setId}")
    public ResponseEntity<?> deleteWorkoutSet(@PathVariable Long setId) {
        if (!workoutSetRepository.existsById(setId)) {
            return ResponseEntity.notFound().build();
        }

        workoutSetRepository.deleteById(setId);
        return ResponseEntity.noContent().build();
    }

    //  4. 세트 수정 (무게, 반복 횟수 변경)
    @PutMapping("/{setId}")
    public ResponseEntity<?> updateWorkoutSet(@PathVariable Long setId, @RequestBody WorkoutSetDto dto) {
        Optional<WorkoutSet> setOpt = workoutSetRepository.findById(setId);
        if (setOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WorkoutSet set = setOpt.get();
        set.setWeight(dto.getWeight());
        set.setReps(dto.getReps());
        set.setCompleted(dto.isCompleted());

        workoutSetRepository.save(set);
        return ResponseEntity.ok(set.toDto());
    }

    //  5. 세트 완료 상태 토글 (체크박스)
    @PatchMapping("/{setId}/toggle-complete")
    public ResponseEntity<?> toggleComplete(@PathVariable Long setId) {
        Optional<WorkoutSet> setOpt = workoutSetRepository.findById(setId);
        if (setOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WorkoutSet set = setOpt.get();
        set.setCompleted(!set.isCompleted());  // 현재 상태 반전
        workoutSetRepository.save(set);

        return ResponseEntity.ok(set.toDto());
    }
}
