package com.example.schedulemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter           // [필수] JSON 데이터를 변수에 집어넣으려면 필요!
@NoArgsConstructor // [필수] 기본 생성자가 있어야 Jackson이 객체를 만듦!

public class CommentRequestDto {
    private  String contents;
    private  String author;
    private  String password;
}


