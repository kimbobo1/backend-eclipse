package pack.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dto.WorkoutTypesDto;
import pack.entity.WorkoutTypes;
import pack.repository.WorkoutTypesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutTypesModel {

    private final WorkoutTypesRepository workoutTypesRepository;

    // 모든 운동 종류 조회
    public List<WorkoutTypesDto> getAllWorkoutTypes() {
        List<WorkoutTypes> workoutTypes = workoutTypesRepository.findAll();
        return workoutTypes.stream()
                .map(WorkoutTypes::toDto)
                .collect(Collectors.toList());
    }

    // 특정 운동 종류 조회 (ID 기준)
    public WorkoutTypesDto getWorkoutTypeById(Long id) {
        Optional<WorkoutTypes> workoutType = workoutTypesRepository.findById(id);
        return workoutType.map(WorkoutTypes::toDto).orElse(null);
    }

    // 새로운 운동 종류 추가
    public WorkoutTypesDto createWorkoutType(WorkoutTypesDto dto) {
        WorkoutTypes newWorkoutType = WorkoutTypes.builder()
                .workoutName(dto.getWorkoutName())
                .description(dto.getDescription())
                .build();
        
        WorkoutTypes savedWorkoutType = workoutTypesRepository.save(newWorkoutType);
        return savedWorkoutType.toDto();
    }

    // 운동 종류 수정
    public WorkoutTypesDto updateWorkoutType(Long id, WorkoutTypesDto dto) {
        WorkoutTypes workoutType = workoutTypesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout Type not found"));

        workoutType.setWorkoutName(dto.getWorkoutName());
        workoutType.setDescription(dto.getDescription());

        WorkoutTypes updatedWorkoutType = workoutTypesRepository.save(workoutType);
        return updatedWorkoutType.toDto();
    }

    // 운동 종류 삭제
    public boolean deleteWorkoutType(Long id) {
        if (!workoutTypesRepository.existsById(id)) {
            return false;
        }
        workoutTypesRepository.deleteById(id);
        return true;
    }
}
