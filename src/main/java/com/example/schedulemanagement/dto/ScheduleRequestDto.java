package com.example.schedulemanagement.dto;

import lombok.Getter;

@Getter
// 요청용 DTO
public class ScheduleRequestDto {
    private String title;
    private String contents;
    private  String author;
    private  String password;
}
