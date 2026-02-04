package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 의존성 주입
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public final ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    } // 생성자 및 메서드 호출과 동일

    // 선택 일정 조회 기능 구현
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 없습니다."));
        return new ScheduleResponseDto(schedule);
    }
    // id 기준으로 찾은 후 있으면 반환, 없으면 에러 throw

    // 전체 일정 조회 기능 구현
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }
    // 수정일 순으로 모든 성분을 가져와 stream에 넣고, DTO의 형태에 맞춰 넣음
}
