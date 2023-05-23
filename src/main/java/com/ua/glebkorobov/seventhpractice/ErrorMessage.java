package com.ua.glebkorobov.seventhpractice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime localDateTime;
    private String message;
    private String description;
}