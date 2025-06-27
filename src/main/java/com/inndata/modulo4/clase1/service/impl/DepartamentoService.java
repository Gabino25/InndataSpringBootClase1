package com.inndata.modulo4.clase1.service.impl;

import com.inndata.modulo4.clase1.entity.Departamento;
import com.inndata.modulo4.clase1.repository.DepartamentoRepository;
import com.inndata.modulo4.clase1.service.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService implements IDepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;
    @Override
    public List<Departamento> readAll() {
        return departamentoRepository.findAll()
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

    @Override
    public Optional<Departamento> readById(Integer id) {
        return departamentoRepository.findById(id);
    }

    @Override
    public Departamento create(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento update(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @Override
    public String deletebyId(Integer id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        if(departamento.isPresent()){
            Departamento departamentoValor = departamento.get();
            departamentoValor.setActivo(false); // Cambiamos el estado a inactivo Borrado Lógico
            departamentoRepository.save(departamentoValor);
            //departamentoRepository.deleteById(id); // Descomentar si se desea eliminar físicamente el registro
            return("El departamento se elimino");
        }else{
            return("No existia el departamento");
        }
    }


    @Override
    public List<Departamento> findByM2GreaterThan(Integer m2) {
        return departamentoRepository.findByM2GreaterThan(m2)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

    @Override
    public List<Departamento> findByM2LessThanAndPrecioGreaterThan(Integer m2, Double precio) {
        return departamentoRepository.findByM2LessThanAndPrecioGreaterThan(m2, precio)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

    @Override
    public List<Departamento> m2MenorAndPrecioMayor(Integer m2, Double precio) {
        return departamentoRepository.m2MenorAndPrecioMayor(m2, precio)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

}
