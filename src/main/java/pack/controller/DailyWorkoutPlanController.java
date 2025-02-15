package pack.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pack.dto.DailyWorkoutPlansDto;
import pack.entity.DailyWorkoutPlans;
import pack.entity.User;
import pack.entity.WorkoutTypes;
import pack.repository.DailyWorkoutPlansRepository;
import pack.repository.UserRepository;
import pack.repository.WorkoutTypesRepository;

@RestController
@RequestMapping("/api/workout-plans")  // 📌 운동 계획 관련 API 기본 경로
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class DailyWorkoutPlanController {
	
    private final DailyWorkoutPlansRepository workoutPlansRepository;
    private final UserRepository userRepository;
    private final WorkoutTypesRepository workoutTypesRepository;

    //  1. 특정 사용자 운동 계획 조회 (GET) -  특정 사용자의 운동 계획을 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DailyWorkoutPlansDto>> getWorkoutPlansByUser(@PathVariable Long userId) {
        System.out.println("🚀 특정 사용자 운동 계획 조회 요청: userId=" + userId);

        List<DailyWorkoutPlansDto> plans = workoutPlansRepository.findByUserId(userId)
                .stream()
                .map(DailyWorkoutPlans::toDto)
                .collect(Collectors.toList());

        if (plans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(plans);
    }
    
    //  2. 특정 운동 계획 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<DailyWorkoutPlansDto> getWorkoutPlanById(@PathVariable Long id) {
        System.out.println("🚀 운동 계획 단건 조회 요청: id=" + id);

        return workoutPlansRepository.findById(id)
                .map(plan -> ResponseEntity.ok(plan.toDto()))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 📌 3. 운동 계획 추가 (POST)
    @PostMapping
    public ResponseEntity<?> createWorkoutPlan(@RequestBody DailyWorkoutPlansDto dto) {
        System.out.println("🚀 운동 계획 추가 요청 받음: " + dto);

        //  요청 데이터 검증
        if (dto.getUserId() == null) {
            return ResponseEntity.badRequest().body("{\"error\": \"userId 값이 필요합니다.\"}");
        }
        if (dto.getWorkoutTypeId() == null) {
            return ResponseEntity.badRequest().body("{\"error\": \"workoutTypeId 값이 필요합니다.\"}");
        }

        //  사용자 찾기
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"사용자를 찾을 수 없습니다.\"}");
        }

        //  운동 유형 찾기
        Optional<WorkoutTypes> workoutType = workoutTypesRepository.findById(dto.getWorkoutTypeId());
        if (workoutType.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"운동 유형이 존재하지 않습니다.\"}");
        }

        //  DTO  Entity 변환 
        DailyWorkoutPlans savedPlan = workoutPlansRepository.save(DailyWorkoutPlansDto.toEntity(dto, user.get(), workoutType.get()));

        System.out.println("✅ 운동 계획 추가 완료: " + savedPlan);
        return ResponseEntity.ok(savedPlan.toDto());  
    }

    //  4. 운동 계획 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutPlan(@PathVariable Long id, @RequestBody DailyWorkoutPlansDto dto) {
        System.out.println("🚀 운동 계획 수정 요청: id=" + id);

        //  운동 계획 조회
        Optional<DailyWorkoutPlans> planOptional = workoutPlansRepository.findById(id);
        if (planOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DailyWorkoutPlans plan = planOptional.get();

        //  업데이트 수행
        plan.setDate(dto.getDate());
        plan.setSets(dto.getSets());
        plan.setReps(dto.getReps());
        plan.setWeight(dto.getWeight());
        plan.setCompleted(dto.isCompleted());

        //  수정 후 저장
        DailyWorkoutPlans updatedPlan = workoutPlansRepository.save(plan);
        return ResponseEntity.ok(updatedPlan.toDto());
    }

    //  5. 운동 계획 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutPlan(@PathVariable Long id) {
        System.out.println("🚀 운동 계획 삭제 요청: id=" + id);

        if (!workoutPlansRepository.existsById(id)) {
            return ResponseEntity.status(404).body("{\"error\": \"운동 계획을 찾을 수 없습니다.\"}");
        }

        workoutPlansRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //  6. 운동 계획 완료 처리 (PUT)
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeWorkoutPlan(@PathVariable Long id) {
        System.out.println("🚀 운동 계획 완료 요청: id=" + id);

        Optional<DailyWorkoutPlans> planOptional = workoutPlansRepository.findById(id);
        if (planOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"error\": \"운동 계획을 찾을 수 없습니다.\"}");
        }

        DailyWorkoutPlans plan = planOptional.get();
        plan.setCompleted(true);
        workoutPlansRepository.save(plan);

        System.out.println("✅ 운동 계획 완료 처리됨: id=" + id);
        return ResponseEntity.ok(plan.toDto());
    }
 //  특정 사용자의 특정 날짜 운동 계획 조회
    @GetMapping("/user/{userId}/date") 
    public ResponseEntity<List<DailyWorkoutPlansDto>> getWorkoutPlansByDate(
            @PathVariable Long userId,
            @RequestParam String date) { 

        //  날짜 변환 (YYYY-MM-DD)
        LocalDate targetDate = LocalDate.parse(date);
        System.out.println("🚀 운동 계획 요청: userId=" + userId + ", date=" + targetDate);

        //  특정 사용자 + 특정 날짜 운동 계획 조회
        List<DailyWorkoutPlansDto> workoutPlans = workoutPlansRepository.findByUserIdAndDate(userId, targetDate)
                .stream()
                .map(DailyWorkoutPlans::toDto) // ✅ Entity → DTO 변환
                .collect(Collectors.toList());

        return ResponseEntity.ok(workoutPlans);
    }
 //  특정 사용자의 특정 날짜 운동 통계 조회
    @GetMapping("/stats/{userId}")
    public ResponseEntity<Map<String, Object>> getWorkoutStats(@PathVariable Long userId, @RequestParam String date) {
        System.out.println("🚀 운동 통계 요청: userId=" + userId + ", date=" + date);

        LocalDate targetDate = LocalDate.parse(date);

        //  특정 사용자의 특정 날짜 운동 데이터 조회
        List<DailyWorkoutPlans> workoutPlans = workoutPlansRepository.findByUserIdAndDate(userId, targetDate);

        if (workoutPlans.isEmpty()) {
            //  운동 데이터가 없으면 기본값 반환
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("totalSets", 0);
                put("totalReps", 0);
                put("totalWeight", 0);
            }});
        }

        //  운동 통계 계산
        int totalSets = workoutPlans.stream().mapToInt(DailyWorkoutPlans::getSets).sum();
        int totalReps = workoutPlans.stream().mapToInt(DailyWorkoutPlans::getReps).sum();
        double totalWeight = workoutPlans.stream().mapToDouble(plan -> plan.getWeight() * plan.getReps() * plan.getSets()).sum();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSets", totalSets);
        stats.put("totalReps", totalReps);
        stats.put("totalWeight", totalWeight);

        return ResponseEntity.ok(stats);
    }
    
 //  특정 사용자가 운동한 날짜 목록 조회 API
    @GetMapping("/user/{userId}/dates")
    public ResponseEntity<List<LocalDate>> getWorkoutDatesByUser(@PathVariable Long userId) {
        System.out.println("🚀 운동한 날짜 목록 조회 요청: userId=" + userId);

        //  해당 사용자가 운동한 날짜 목록 조회 (중복 제거)
        List<LocalDate> workoutDates = workoutPlansRepository.findByUserId(userId)
                .stream()
                .map(DailyWorkoutPlans::getDate) 
                .distinct() 
                .sorted(Comparator.reverseOrder()) 
                .collect(Collectors.toList());

        if (workoutDates.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(workoutDates);
    }

}
