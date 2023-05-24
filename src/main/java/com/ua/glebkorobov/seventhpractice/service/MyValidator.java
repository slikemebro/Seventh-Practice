package com.ua.glebkorobov.seventhpractice.service;

import com.ua.glebkorobov.seventhpractice.model.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.LongStream;

public class MyValidator implements ConstraintValidator<IpnValidator, Student> {

    private static final Logger logger = LogManager.getLogger(MyValidator.class);

    Student student;

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext context) {
        this.student = student;
        return isIPNSizeCorrect() && isGenderCorrect() && isDateOfBirthday() && isControlDigit();
    }

    public boolean isIPNSizeCorrect() {
        return student.getIpn() >= 1000000000L && student.getIpn() <= 9999999999L;
    }

    public boolean isGenderCorrect() {
        if (student.getGender().equals("male")) {
            return (String.valueOf(student.getIpn()).charAt(8)) % 2 != 0;
        }
        if (student.getGender().equals("female")) {
            return (String.valueOf(student.getIpn()).charAt(8)) % 2 == 0;
        } else {
            return false;
        }
    }

    public boolean isDateOfBirthday() {
        long days = LocalDate.of(1899, 12, 31).until(student.getDateOfBirthday(), ChronoUnit.DAYS);
        StringBuilder daysFromIpn = new StringBuilder();
        daysFromIpn.append(String.valueOf(student.getIpn()).substring(0, 5));

        logger.info("Days from ipn {}", daysFromIpn);
        logger.info("Correct days {}", days);

        return days == Integer.parseInt(daysFromIpn.toString());
    }

    public boolean isControlDigit() {
        LongStream longStream = String.valueOf(student.getIpn()).chars().mapToLong(Character::getNumericValue);
        long[] arr = longStream.toArray();

        long controlSum = arr[0] * (-1) + arr[1] * 5 + arr[2] * 7 + arr[3] * 9 + arr[4] * 4 + arr[5] * 6 + arr[6] * 10 +
                arr[7] * 5 + arr[8] * 7;

        long controlNumber = controlSum % 11;

        logger.info("Control sum = {}, control number = {}", controlSum, controlNumber);

        if (controlNumber > 9) {
            controlNumber %= 10;
        }

        logger.info("Control number = {}", controlNumber);

        return controlNumber == arr[arr.length - 1];
    }
}
