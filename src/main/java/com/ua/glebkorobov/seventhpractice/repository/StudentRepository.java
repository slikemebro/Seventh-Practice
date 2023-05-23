package com.ua.glebkorobov.seventhpractice.repository;

import com.ua.glebkorobov.seventhpractice.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
