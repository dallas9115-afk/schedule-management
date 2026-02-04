package com.example.schedulemanagement.entity;

import com.example.schedulemanagement.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Comment extends TImestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    //연관관계를 글의 ID(숫자) 만 저장해서 연결
    @Column(nullable = false)
    private Long scheduleId;

    // 생성자(일정 ID를 받아와서 저장)
    public Comment(CommentRequestDto requestDto, Long scheduleId){
        this.contents = requestDto.getContents();
        this.contents = requestDto.getContents();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
        this.scheduleId = scheduleId;
    }
}
