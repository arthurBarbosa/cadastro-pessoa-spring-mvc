package com.abcode.springboot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio.")
    private String name;

    @NotBlank(message = "Sobrenome não pode ser vazio.")
    private String surname;

    @Positive(message = "Idade não pode ser menor ou igual a zero.")
    private int age;

    @OneToMany(mappedBy = "person")
    private List<Phone> phones;

}
