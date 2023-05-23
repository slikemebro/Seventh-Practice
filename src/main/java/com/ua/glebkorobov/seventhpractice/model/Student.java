package com.ua.glebkorobov.seventhpractice.model;

import com.ua.glebkorobov.seventhpractice.service.IpnValidator;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@IpnValidator
public class Student {
    @Id
    @Column(unique = true)
    @NotNull
    @Range(min = 1000000000, max = 9999999999L)
    @Positive
    private long ipn;

    @Column
    @NotBlank
    @Size(min = 4, max = 30)
    private String name;

    @Column
    @NotBlank
    @Size(min = 4, max = 30)
    private String surname;

    @Past
    private LocalDate dateOfBirthday;

    @Column(unique = true)
    @Email
    private String email;

    @Pattern(regexp = "^(male|female)$")
    private String gender;
}
