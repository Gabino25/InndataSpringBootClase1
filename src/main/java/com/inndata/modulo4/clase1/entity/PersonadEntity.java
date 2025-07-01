package com.inndata.modulo4.clase1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PERSONAD")
public class PersonadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "EDAD")
    private Integer edad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DEPARTAMENTO")
    private DepartamentoEntity idDepartamento;

    @Column(name = "ACTIVO")
    private Boolean activo;



}
