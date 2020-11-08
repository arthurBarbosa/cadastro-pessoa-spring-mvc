package com.abcode.springboot.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Phone implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String type;

    @ManyToOne
    private Person person;
}
