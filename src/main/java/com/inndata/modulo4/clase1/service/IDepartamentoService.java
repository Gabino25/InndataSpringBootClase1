package com.inndata.modulo4.clase1.service;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
import com.inndata.modulo4.clase1.response.DepartamentoResponse;

import java.util.List;
import java.util.Optional;

public interface IDepartamentoService {

    //Metodos Read
    public List<DepartamentoResponse> readAll();
    public Optional<DepartamentoEntity> readById(Integer id);
    //Metodo create
    public DepartamentoResponse create(DepartamentoEntity departamentoEntity);
    //Metodo update
    public DepartamentoEntity update(DepartamentoEntity departamentoEntity);
    //Metodo Delete
    public String deletebyId(Integer id);
    // Metodo para buscar departamentos por m2 mayor que un valor dado
    public List<DepartamentoEntity> findByM2GreaterThan(Integer m2);
    // Metodo para buscar departamentos por m2 menor que un valor dado y precio mayor que un valor dado
    public List<DepartamentoEntity> findByM2LessThanAndPrecioGreaterThan(Integer m2, Double precio);
    // Metodo para buscar departamentos por m2 menor que un valor dado y precio mayor que un valor dado
    public List<DepartamentoEntity> m2MenorAndPrecioMayor(Integer m2, Double precio);

}
