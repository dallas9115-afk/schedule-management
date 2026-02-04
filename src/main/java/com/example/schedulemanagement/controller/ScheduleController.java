package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto){
        return scheduleService.createSchedule(requestDto);
    }
    // 선택 일정 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }
    // 전체 일정 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    // 일정 수정
    // Method : PUT , URL: /api/schedules/{id}
    @PutMapping("/schedules/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // 일정  삭제
    // Method : PUT , URL: /api/schedules/{id}
    // 삭제 시 비밀번호가 body 에 담겨져서 보내져야 하므로 @RequestBody 필요.
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.deleteSchedule(id, requestDto);
    }

}
