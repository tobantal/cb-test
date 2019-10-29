package ru.cb.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    @NotBlank(message = "must be not blank!")
    @Size(max = 20)
    private String lastName;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 10)
    private String firstName;

    @NotBlank
    @Column(name = "company")
    private String company;

    @NotBlank
    @Column(name = "address")
    private String address;

}