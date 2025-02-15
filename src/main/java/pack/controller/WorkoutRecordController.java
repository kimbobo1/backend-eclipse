package pack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.dto.WorkoutRecordsDto;
import pack.model.WorkoutRecordsModel;

import java.util.List;

@RestController
@RequestMapping("/workout-records")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class WorkoutRecordController {

    private final WorkoutRecordsModel workoutRecordsModel;

    // 사용자별 운동 기록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutRecordsDto>> getRecordsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutRecordsModel.getRecordsByUserId(userId));
    }

    // 특정 운동 기록 조회
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRecordsDto> getRecordById(@PathVariable Long id) {
        WorkoutRecordsDto record = workoutRecordsModel.getRecordById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    // 운동 기록 생성
    @PostMapping
    public ResponseEntity<WorkoutRecordsDto> createRecord(@RequestBody WorkoutRecordsDto dto) {
        return ResponseEntity.ok(workoutRecordsModel.createRecord(dto));
    }

    // 운동 기록 수정
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutRecordsDto> updateRecord(@PathVariable Long id, @RequestBody WorkoutRecordsDto dto) {
        return ResponseEntity.ok(workoutRecordsModel.updateRecord(id, dto));
    }

    // 운동 기록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        return workoutRecordsModel.deleteRecord(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}