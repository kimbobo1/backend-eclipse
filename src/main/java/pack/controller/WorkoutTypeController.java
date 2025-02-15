package pack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.WorkoutTypesDto;
import pack.entity.WorkoutTypes;
import pack.repository.WorkoutTypesRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workout-types") 
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class WorkoutTypeController {

    private final WorkoutTypesRepository workoutTypeRepository;

    // ✅ 모든 운동 유형 조회
    @GetMapping
    public ResponseEntity<List<WorkoutTypesDto>> getAllWorkoutTypes() {
        List<WorkoutTypesDto> workoutTypes = workoutTypeRepository.findAll()
                .stream()
                .map(WorkoutTypes::toDto) 
                .collect(Collectors.toList());

        if (workoutTypes.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }

        return ResponseEntity.ok(workoutTypes);
    }

    //  운동 유형 추가
    @PostMapping
    public ResponseEntity<?> createWorkoutType(@RequestBody WorkoutTypesDto dto) {
        
        if (workoutTypeRepository.findByWorkoutName(dto.getWorkoutName()).isPresent()) {
            return ResponseEntity.badRequest().body("{\"error\": \"이미 존재하는 운동입니다.\"}");
        }

        //  운동 저장
        WorkoutTypes workoutType = WorkoutTypes.builder()
                .workoutName(dto.getWorkoutName())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .build();

        WorkoutTypes savedWorkoutType = workoutTypeRepository.save(workoutType);
        return ResponseEntity.ok(savedWorkoutType.toDto());
    }

    //  특정 운동 유형 조회
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutTypesDto> getWorkoutTypeById(@PathVariable Long id) {
        return workoutTypeRepository.findById(id)
                .map(workoutType -> ResponseEntity.ok(workoutType.toDto()))
                .orElse(ResponseEntity.notFound().build());
    }

    //  운동 유형 삭제 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutType(@PathVariable Long id) {
        if (!workoutTypeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        workoutTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
