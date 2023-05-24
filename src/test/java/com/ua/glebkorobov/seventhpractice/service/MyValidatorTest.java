package com.ua.glebkorobov.seventhpractice.service;

import com.ua.glebkorobov.seventhpractice.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyValidatorTest {

    MyValidator myValidator;

    @BeforeEach
    void createMyValidator() {
        myValidator = new MyValidator();
    }

    @Test
    void isValid() {
        Student student = new Student(3799567899L, "Hlib", "Korobov",
                LocalDate.of(2004, 1, 10), "test@gmail.com", "male");

        assertTrue(myValidator.isValid(student, null));
    }

    @Test
    void isIPNSizeCorrect() {
        Student student = new Student(3799567899L, "Hlib", "Korobov",
                LocalDate.of(2004, 1, 10), "test@gmail.com", "male");

        myValidator.student = student;

        assertTrue(myValidator.isIPNSizeCorrect());

        student.setIpn(123);

        assertFalse(myValidator.isIPNSizeCorrect());
    }

    @Test
    void isGenderCorrect() {
        Student student = new Student(3799567899L, "Hlib", "Korobov",
                LocalDate.of(2004, 1, 10), "test@gmail.com", "male");

        myValidator.student = student;

        assertTrue(myValidator.isGenderCorrect());

        student.setGender("female");

        assertFalse(myValidator.isGenderCorrect());

        student.setIpn(3799567889L);

        assertTrue(myValidator.isGenderCorrect());
    }

    @Test
    void isDateOfBirthday() {
        Student student = new Student(3799567899L, "Hlib", "Korobov",
                LocalDate.of(2004, 1, 10), "test@gmail.com", "male");

        myValidator.student = student;

        assertTrue(myValidator.isDateOfBirthday());

        student.setDateOfBirthday(LocalDate.now());

        assertFalse(myValidator.isDateOfBirthday());
    }

    @Test
    void isControlDigit() {
        Student student = new Student(3799567899L, "Hlib", "Korobov",
                LocalDate.of(2004, 1, 10), "test@gmail.com", "male");

        myValidator.student = student;

        assertTrue(myValidator.isControlDigit());

        student.setIpn(3799567897L);

        assertFalse(myValidator.isControlDigit());
    }
}