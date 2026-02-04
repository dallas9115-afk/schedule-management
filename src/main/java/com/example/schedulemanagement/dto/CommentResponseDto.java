package com.example.schedulemanagement.dto;

import com.example.schedulemanagement.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
// 응답에는 비밀번호를 제외, 일정 ID 포함하여 반환
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String author;
    private Long scheduleId; // 어느 일정에 달린 건지 알려줌
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.author = comment.getAuthor();
        this.scheduleId = comment.getScheduleId();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
