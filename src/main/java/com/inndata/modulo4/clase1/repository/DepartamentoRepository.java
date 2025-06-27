package com.inndata.modulo4.clase1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inndata.modulo4.clase1.entity.Departamento;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    public List<Departamento> findByM2GreaterThan(Integer m2);

    public List<Departamento> findByM2LessThanAndPrecioGreaterThan(Integer m2,Double precio);

    @Query(value = "select * from departamento where m2 < :m2 and precio > :precio;", nativeQuery = true)
    public List<Departamento> m2MenorAndPrecioMayor(Integer m2, Double precio);

}
