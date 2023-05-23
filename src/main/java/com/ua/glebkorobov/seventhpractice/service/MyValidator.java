package com.ua.glebkorobov.seventhpractice.service;

import com.ua.glebkorobov.seventhpractice.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MyValidator implements ConstraintValidator<IpnValidator, Student> {

    private static final Logger logger = LogManager.getLogger(MyValidator.class);

    Student student;

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext context) {
        this.student = student;
        return isIPNSizeCorrect() && isGenderCorrect() && isDateOfBirthday();
    }

    private boolean isIPNSizeCorrect() {
        return student.getIpn() >= 1000000000L && student.getIpn() <= 9999999999L;
    }

    private boolean isGenderCorrect() {
        if (student.getGender().equals("male")) {
            return (String.valueOf(student.getIpn()).charAt(8)) % 2 != 0;
        }
        if (student.getGender().equals("female")) {
            return (String.valueOf(student.getIpn()).charAt(8)) % 2 == 0;
        } else {
            return false;
        }
    }

    private boolean isDateOfBirthday() {
        long days = LocalDate.of(1899, 12, 31).until(student.getDateOfBirthday(), ChronoUnit.DAYS);
        StringBuilder daysFromIpn = new StringBuilder();
        daysFromIpn.append(String.valueOf(student.getIpn()).substring(0, 5));
        logger.info("Days from ipn {}",daysFromIpn);
        logger.info("Correct days {}",days);
        return days == Integer.parseInt(daysFromIpn.toString());
    }
}
