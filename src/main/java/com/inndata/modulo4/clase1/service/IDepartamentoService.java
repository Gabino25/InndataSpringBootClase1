package com.inndata.modulo4.clase1.service;

import com.inndata.modulo4.clase1.entity.Departamento;

import java.util.List;
import java.util.Optional;

public interface IDepartamentoService {

    //Metodos Read
    public List<Departamento> readAll();
    public Optional<Departamento> readById(Integer id);
    //Metodo create
    public Departamento create(Departamento departamento);
    //Metodo update
    public Departamento update(Departamento departamento);
    //Metodo Delete
    public String deletebyId(Integer id);
    // Metodo para buscar departamentos por m2 mayor que un valor dado
    public List<Departamento> findByM2GreaterThan(Integer m2);
    // Metodo para buscar departamentos por m2 menor que un valor dado y precio mayor que un valor dado
    public List<Departamento> findByM2LessThanAndPrecioGreaterThan(Integer m2, Double precio);
    // Metodo para buscar departamentos por m2 menor que un valor dado y precio mayor que un valor dado
    public List<Departamento> m2MenorAndPrecioMayor(Integer m2, Double precio);

}
