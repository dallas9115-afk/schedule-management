package com.example.schedulemanagement.controller;

import com.example.schedulemanagement.dto.CommentRequestDto;
import com.example.schedulemanagement.dto.CommentResponseDto;
import com.example.schedulemanagement.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    // POST /api/schedules/{scheduleId}/comments
    @PostMapping("/schedules/{scheduleId}/comments")
    public CommentResponseDto createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(scheduleId, requestDto);
    }
}
