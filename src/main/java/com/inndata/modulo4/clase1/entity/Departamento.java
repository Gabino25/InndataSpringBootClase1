package com.inndata.modulo4.clase1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DEPARTAMENTO")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "M2")
    private Integer m2;

    @Column(name = "PRECIO")
    private Double precio;

    @Column(name = "ACTIVO")
    private Boolean activo;

}
