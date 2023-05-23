package com.ua.glebkorobov.seventhpractice.service;

import com.ua.glebkorobov.seventhpractice.exception.RequestException;
import com.ua.glebkorobov.seventhpractice.exception.WrongValidation;
import com.ua.glebkorobov.seventhpractice.model.Student;
import com.ua.glebkorobov.seventhpractice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Student getStudentByIpn(long ipn) {
        Optional<Student> optionalStudent = studentRepository.findById(ipn);
        if (optionalStudent.isPresent()){
            return optionalStudent.get();
        }else {
            throw new RequestException("Wrong ipn or non-existing");
        }
    }

    public void save(Student student) {
        Long ipn = student.getIpn();
        Optional<Student> optionalStudent = studentRepository.findById(ipn);
        if (optionalStudent.isPresent()) {
            throw new RequestException("Ipn " + ipn + " already exists");
        }
        studentRepository.save(student);
    }


    public Student deleteByIpn(long ipn) {
        Optional<Student> optionalStudent = studentRepository.findById(ipn);
        if (optionalStudent.isPresent()) {
            studentRepository.deleteById(ipn);
            return optionalStudent.get();
        }else {
            throw new RequestException("Wrong ipn or non-existing");
        }
    }

    public List<Student> deleteAllStudents() {
        List<Student> students = getAllStudents();
        studentRepository.deleteAll();
        return students;
    }

    public Student updateStudentByIpn
            (Student student, String name, String surname, String email,
             String gender, LocalDate dateOfBirthday) {

        if (name != null) {
            student.setName(name);
        }
        if (surname != null) {
            student.setSurname(surname);
        }
        if (email != null) {
            student.setEmail(email);
        }
        if (gender != null) {
            student.setGender(gender.toLowerCase(Locale.ROOT));
        }
        if (dateOfBirthday != null) {
            student.setDateOfBirthday(dateOfBirthday);
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (!validator.validate(student).isEmpty()) {
            throw new WrongValidation(validator.validate(student).toString());
        }

        studentRepository.save(student);

        return student;
    }
}
