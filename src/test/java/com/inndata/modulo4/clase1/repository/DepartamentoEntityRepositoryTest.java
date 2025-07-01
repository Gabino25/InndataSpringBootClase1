package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Departamento Repository Tests")
class DepartamentoEntityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private DepartamentoEntity departamentoEntity1;
    private DepartamentoEntity departamentoEntity2;
    private DepartamentoEntity departamentoEntity3;

    @BeforeEach
    void setUp() {
        // Create test data
        departamentoEntity1 = new DepartamentoEntity();
        departamentoEntity1.setM2(80);
        departamentoEntity1.setPrecio(150000.0);

        departamentoEntity2 = new DepartamentoEntity();
        departamentoEntity2.setM2(120);
        departamentoEntity2.setPrecio(220000.0);

        departamentoEntity3 = new DepartamentoEntity();
        departamentoEntity3.setM2(95);
        departamentoEntity3.setPrecio(180000.0);
    }

    @Test
    @DisplayName("Should save and retrieve a Departamento")
    void testSaveAndFindById() {
        // Given
        DepartamentoEntity savedDepartamentoEntity = entityManager.persistAndFlush(departamentoEntity1);
        
        // When
        Optional<DepartamentoEntity> found = departamentoRepository.findById(savedDepartamentoEntity.getId());
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getM2()).isEqualTo(80);
        assertThat(found.get().getPrecio()).isEqualTo(150000.0);
    }

    @Test
    @DisplayName("Should return empty Optional when Departamento not found")
    void testFindByIdNotFound() {
        // When
        Optional<DepartamentoEntity> found = departamentoRepository.findById(999);
        
        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should save a Departamento and generate ID")
    void testSaveDepartamento() {
        // When
        DepartamentoEntity saved = departamentoRepository.save(departamentoEntity1);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isEqualTo(80);
        assertThat(saved.getPrecio()).isEqualTo(150000.0);
    }

    @Test
    @DisplayName("Should find all Departamentos")
    void testFindAll() {
        // Given
        entityManager.persistAndFlush(departamentoEntity1);
        entityManager.persistAndFlush(departamentoEntity2);
        entityManager.persistAndFlush(departamentoEntity3);
        
        // When
        List<DepartamentoEntity> departamentoEntities = departamentoRepository.findAll();
        
        // Then
        assertThat(departamentoEntities).hasSize(3);
        assertThat(departamentoEntities).extracting(DepartamentoEntity::getM2)
                .containsExactlyInAnyOrder(80, 120, 95);
    }

    @Test
    @DisplayName("Should return empty list when no Departamentos exist")
    void testFindAllEmpty() {
        // When
        List<DepartamentoEntity> departamentoEntities = departamentoRepository.findAll();
        
        // Then
        assertThat(departamentoEntities).isEmpty();
    }

    @Test
    @DisplayName("Should update an existing Departamento")
    void testUpdateDepartamento() {
        // Given
        DepartamentoEntity saved = entityManager.persistAndFlush(departamentoEntity1);
        
        // When
        saved.setM2(100);
        saved.setPrecio(175000.0);
        DepartamentoEntity updated = departamentoRepository.save(saved);
        
        // Then
        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getM2()).isEqualTo(100);
        assertThat(updated.getPrecio()).isEqualTo(175000.0);
    }

    @Test
    @DisplayName("Should delete a Departamento")
    void testDeleteDepartamento() {
        // Given
        DepartamentoEntity saved = entityManager.persistAndFlush(departamentoEntity1);
        Integer departamentoId = saved.getId();
        
        // When
        departamentoRepository.delete(saved);
        entityManager.flush();
        
        // Then
        Optional<DepartamentoEntity> deleted = departamentoRepository.findById(departamentoId);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should delete Departamento by ID")
    void testDeleteById() {
        // Given
        DepartamentoEntity saved = entityManager.persistAndFlush(departamentoEntity1);
        Integer departamentoId = saved.getId();
        
        // When
        departamentoRepository.deleteById(departamentoId);
        entityManager.flush();
        
        // Then
        Optional<DepartamentoEntity> deleted = departamentoRepository.findById(departamentoId);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should check if Departamento exists by ID")
    void testExistsById() {
        // Given
        DepartamentoEntity saved = entityManager.persistAndFlush(departamentoEntity1);
        
        // When & Then
        assertThat(departamentoRepository.existsById(saved.getId())).isTrue();
        assertThat(departamentoRepository.existsById(999)).isFalse();
    }

    @Test
    @DisplayName("Should count total number of Departamentos")
    void testCount() {
        // Given
        entityManager.persistAndFlush(departamentoEntity1);
        entityManager.persistAndFlush(departamentoEntity2);
        
        // When
        long count = departamentoRepository.count();
        
        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should save multiple Departamentos")
    void testSaveAll() {
        // Given
        List<DepartamentoEntity> departamentoEntities = List.of(departamentoEntity1, departamentoEntity2, departamentoEntity3);
        
        // When
        List<DepartamentoEntity> saved = departamentoRepository.saveAll(departamentoEntities);
        
        // Then
        assertThat(saved).hasSize(3);
        assertThat(saved).allMatch(d -> d.getId() != null);
    }

    @Test
    @DisplayName("Should delete all Departamentos")
    void testDeleteAll() {
        // Given
        entityManager.persistAndFlush(departamentoEntity1);
        entityManager.persistAndFlush(departamentoEntity2);
        
        // When
        departamentoRepository.deleteAll();
        entityManager.flush();
        
        // Then
        assertThat(departamentoRepository.count()).isZero();
    }

    @Test
    @DisplayName("Should handle null values properly")
    void testSaveWithNullValues() {
        // Given
        DepartamentoEntity departamentoEntityWithNulls = new DepartamentoEntity();
        departamentoEntityWithNulls.setM2(null);
        departamentoEntityWithNulls.setPrecio(null);
        
        // When
        DepartamentoEntity saved = departamentoRepository.save(departamentoEntityWithNulls);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isNull();
        assertThat(saved.getPrecio()).isNull();
    }

    @Test
    @DisplayName("Should save Departamento with zero values")
    void testSaveWithZeroValues() {
        // Given
        DepartamentoEntity departamentoEntityWithZeros = new DepartamentoEntity();
        departamentoEntityWithZeros.setM2(0);
        departamentoEntityWithZeros.setPrecio(0.0);
        
        // When
        DepartamentoEntity saved = departamentoRepository.save(departamentoEntityWithZeros);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isZero();
        assertThat(saved.getPrecio()).isZero();
    }

    @Test
    @DisplayName("Should save Departamento with large values")
    void testSaveWithLargeValues() {
        // Given
        DepartamentoEntity departamentoEntityWithLargeValues = new DepartamentoEntity();
        departamentoEntityWithLargeValues.setM2(Integer.MAX_VALUE);
        departamentoEntityWithLargeValues.setPrecio(Double.MAX_VALUE);
        
        // When
        DepartamentoEntity saved = departamentoRepository.save(departamentoEntityWithLargeValues);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isEqualTo(Integer.MAX_VALUE);
        assertThat(saved.getPrecio()).isEqualTo(Double.MAX_VALUE);
    }
}
