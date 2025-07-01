package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartamentoRepository extends JpaRepository<DepartamentoEntity, Integer> {

    public List<DepartamentoEntity> findByM2GreaterThan(Integer m2);

    public List<DepartamentoEntity> findByM2LessThanAndPrecioGreaterThan(Integer m2, Double precio);

    @Query(value = "select * from departamento where m2 < :m2 and precio > :precio;", nativeQuery = true)
    public List<DepartamentoEntity> m2MenorAndPrecioMayor(Integer m2, Double precio);

}
