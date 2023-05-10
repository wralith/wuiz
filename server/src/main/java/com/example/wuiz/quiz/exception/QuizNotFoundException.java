package com.example.wuiz.quiz.exception;

public class QuizNotFoundException extends Exception {
    public QuizNotFoundException() {
        super("Quiz not found");
    }

    public QuizNotFoundException(Integer id) {
        super("Quiz with id " + id + " not found");
    }
}
