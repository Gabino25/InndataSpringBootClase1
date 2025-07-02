package com.inndata.modulo4.clase1.service.impl;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
import com.inndata.modulo4.clase1.repository.DepartamentoRepository;
import com.inndata.modulo4.clase1.response.DepartamentoResponse;
import com.inndata.modulo4.clase1.service.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class DepartamentoService implements IDepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;
    @Override
    public List<DepartamentoResponse> readAll() {

        Function<DepartamentoEntity, DepartamentoResponse> mapToResponse = new Function<DepartamentoEntity, DepartamentoResponse>() {
            @Override
            public DepartamentoResponse apply(DepartamentoEntity departamentoEntity) {
                DepartamentoResponse departamentoResponse = new DepartamentoResponse();
                departamentoResponse.setId(departamentoEntity.getId());
                departamentoResponse.setPrecio(departamentoEntity.getPrecio());
                return departamentoResponse;
            }
        };

        return departamentoRepository.findAll()
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .map(mapToResponse)
                                     .toList();
    }

    @Override
    public Optional<DepartamentoEntity> readById(Integer id) {
        return departamentoRepository.findById(id);
    }

    @Override
    public DepartamentoResponse create(DepartamentoEntity departamentoEntity) {
        DepartamentoEntity departamentoEntitySaved = departamentoRepository.save(departamentoEntity);
        DepartamentoResponse departamentoResponse = new DepartamentoResponse();
        departamentoResponse.setId(departamentoEntitySaved.getId());
        departamentoResponse.setPrecio(departamentoEntitySaved.getPrecio());
        return departamentoResponse;
    }

    @Override
    public DepartamentoEntity update(DepartamentoEntity departamentoEntity) {
        return departamentoRepository.save(departamentoEntity);
    }

    @Override
    public String deletebyId(Integer id) {
        Optional<DepartamentoEntity> departamento = departamentoRepository.findById(id);
        if(departamento.isPresent()){
            DepartamentoEntity departamentoEntityValor = departamento.get();
            departamentoEntityValor.setActivo(false); // Cambiamos el estado a inactivo Borrado Lógico
            departamentoRepository.save(departamentoEntityValor);
            //departamentoRepository.deleteById(id); // Descomentar si se desea eliminar físicamente el registro
            return("El departamento se elimino");
        }else{
            return("No existia el departamento");
        }
    }


    @Override
    public List<DepartamentoEntity> findByM2GreaterThan(Integer m2) {
        return departamentoRepository.findByM2GreaterThan(m2)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

    @Override
    public List<DepartamentoEntity> findByM2LessThanAndPrecioGreaterThan(Integer m2, Double precio) {
        return departamentoRepository.findByM2LessThanAndPrecioGreaterThan(m2, precio)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

    @Override
    public List<DepartamentoEntity> m2MenorAndPrecioMayor(Integer m2, Double precio) {
        return departamentoRepository.m2MenorAndPrecioMayor(m2, precio)
                                     .stream()
                                     .filter(departamento -> departamento.getActivo())
                                     .toList();
    }

}
