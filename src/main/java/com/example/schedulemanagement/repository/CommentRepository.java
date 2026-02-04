package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글 생성 10개 달기 제한용 count
    long countByScheduleId(Long scheduleId);
    List<Comment> findAllByScheduleIdOrderByModifiedAtDesc(Long scheduleId);
}
