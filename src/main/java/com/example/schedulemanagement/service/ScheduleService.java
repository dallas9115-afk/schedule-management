package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.CommentResponseDto;
import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.CommentRepository;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 의존성 주입
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        validateInput(requestDto); // 유효값 검사 먼저 수행
        Schedule schedule = new Schedule(requestDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    } // 생성자 및 메서드 호출과 동일

    // 선택 일정 조회 기능 구현
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 일정이 없습니다."));

        List<CommentResponseDto> commentList = commentRepository.findAllByScheduleIdOrderByModifiedAtDesc(id)
                .stream()
                .map(CommentResponseDto::new)
                .toList();

        return new ScheduleResponseDto(schedule, commentList);
    }
    // id 기준으로 찾은 후 있으면 반환, 없으면 에러 throw

    // 전체 일정 조회 기능 구현
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }
    // 수정일 순으로 모든 성분을 가져와 stream에 넣고, DTO의 형태에 맞춰 넣음

    // 일정 수정 기능 구현
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto){
        validateInput(requestDto);
        //해당 일정이 있는지 확인하고, 없으면 에러 반환
        Schedule schedule = findSchedule(id);

        // 비밀번호 체크 로직
        // requestDto.getpassword 를 통해 받은 비밀번호와 DB의 비밀번호 불일치 시 에러 반환
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 일치 시 수정 및 수정된 결과 반환
        schedule.update(requestDto);

        return new ScheduleResponseDto(schedule);
    }

    public Long deleteSchedule(Long id, ScheduleRequestDto requestDto){
        // 해당 일정이 있는지 확인, 없으면 에러 반환
        Schedule schedule = findSchedule(id);

        //위와 같은 비밀번호 체크 로직
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(schedule);

        return id; //삭제한 ID 반환

    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정이 없습니다.")
        );
    }

    private void validateInput(ScheduleRequestDto requestDto) {
        // 1. 제목 검증 (필수 + 30자 제한)
        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수값입니다.");
        }
        if (requestDto.getTitle().length() > 30) {
            throw new IllegalArgumentException("제목은 최대 30자까지 입력 가능합니다.");
        }

        // 2. 내용 검증 (필수 + 200자 제한)
        if (requestDto.getContents() == null || requestDto.getContents().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수값입니다.");
        }
        if (requestDto.getContents().length() > 200) {
            throw new IllegalArgumentException("내용은 최대 200자까지 입력 가능합니다.");
        }

        // 3. 작성자 검증 (필수)
        if (requestDto.getAuthor() == null || requestDto.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("작성자는 필수값입니다.");
        }

        // 4. 비밀번호 검증 (필수)
        if (requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수값입니다.");
        }
    }

}
