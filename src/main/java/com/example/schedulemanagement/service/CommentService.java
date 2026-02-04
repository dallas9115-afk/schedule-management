package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.CommentRequestDto;
import com.example.schedulemanagement.dto.CommentResponseDto;
import com.example.schedulemanagement.entity.Comment;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.repository.CommentRepository;
import com.example.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository; // 일정 존재 여부 확인용 변수

    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto){
        // 댓글이 비었거나 100자 이상일 시 오류 반환
        String contents = requestDto.getContents();
        if(contents == null || contents.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
        }
        if(contents.length() > 100) {
            throw new IllegalArgumentException("댓글 내용은 100자를 넘을 수 없습니다.");
        }

        // 일정의 존재 여부 확인, 없는 일정이면 오류 반환
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 댓글이 이미 10개 이상 달렸는지 확인
        long count = commentRepository.countByScheduleId(scheduleId);
        if (count >= 10){
            throw new IllegalArgumentException("댓글은 최대 10개까지만 달 수 있습니다.");
        }

        // 위의 조건을 통과한 값을 저장(scheduleId 숫자만)
        Comment comment = new Comment(requestDto, scheduleId);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }
}
