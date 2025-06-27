package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.Departamento;
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
class DepartamentoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Departamento departamento1;
    private Departamento departamento2;
    private Departamento departamento3;

    @BeforeEach
    void setUp() {
        // Create test data
        departamento1 = new Departamento();
        departamento1.setM2(80);
        departamento1.setPrecio(150000.0);

        departamento2 = new Departamento();
        departamento2.setM2(120);
        departamento2.setPrecio(220000.0);

        departamento3 = new Departamento();
        departamento3.setM2(95);
        departamento3.setPrecio(180000.0);
    }

    @Test
    @DisplayName("Should save and retrieve a Departamento")
    void testSaveAndFindById() {
        // Given
        Departamento savedDepartamento = entityManager.persistAndFlush(departamento1);
        
        // When
        Optional<Departamento> found = departamentoRepository.findById(savedDepartamento.getId());
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getM2()).isEqualTo(80);
        assertThat(found.get().getPrecio()).isEqualTo(150000.0);
    }

    @Test
    @DisplayName("Should return empty Optional when Departamento not found")
    void testFindByIdNotFound() {
        // When
        Optional<Departamento> found = departamentoRepository.findById(999);
        
        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should save a Departamento and generate ID")
    void testSaveDepartamento() {
        // When
        Departamento saved = departamentoRepository.save(departamento1);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isEqualTo(80);
        assertThat(saved.getPrecio()).isEqualTo(150000.0);
    }

    @Test
    @DisplayName("Should find all Departamentos")
    void testFindAll() {
        // Given
        entityManager.persistAndFlush(departamento1);
        entityManager.persistAndFlush(departamento2);
        entityManager.persistAndFlush(departamento3);
        
        // When
        List<Departamento> departamentos = departamentoRepository.findAll();
        
        // Then
        assertThat(departamentos).hasSize(3);
        assertThat(departamentos).extracting(Departamento::getM2)
                .containsExactlyInAnyOrder(80, 120, 95);
    }

    @Test
    @DisplayName("Should return empty list when no Departamentos exist")
    void testFindAllEmpty() {
        // When
        List<Departamento> departamentos = departamentoRepository.findAll();
        
        // Then
        assertThat(departamentos).isEmpty();
    }

    @Test
    @DisplayName("Should update an existing Departamento")
    void testUpdateDepartamento() {
        // Given
        Departamento saved = entityManager.persistAndFlush(departamento1);
        
        // When
        saved.setM2(100);
        saved.setPrecio(175000.0);
        Departamento updated = departamentoRepository.save(saved);
        
        // Then
        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getM2()).isEqualTo(100);
        assertThat(updated.getPrecio()).isEqualTo(175000.0);
    }

    @Test
    @DisplayName("Should delete a Departamento")
    void testDeleteDepartamento() {
        // Given
        Departamento saved = entityManager.persistAndFlush(departamento1);
        Integer departamentoId = saved.getId();
        
        // When
        departamentoRepository.delete(saved);
        entityManager.flush();
        
        // Then
        Optional<Departamento> deleted = departamentoRepository.findById(departamentoId);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should delete Departamento by ID")
    void testDeleteById() {
        // Given
        Departamento saved = entityManager.persistAndFlush(departamento1);
        Integer departamentoId = saved.getId();
        
        // When
        departamentoRepository.deleteById(departamentoId);
        entityManager.flush();
        
        // Then
        Optional<Departamento> deleted = departamentoRepository.findById(departamentoId);
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Should check if Departamento exists by ID")
    void testExistsById() {
        // Given
        Departamento saved = entityManager.persistAndFlush(departamento1);
        
        // When & Then
        assertThat(departamentoRepository.existsById(saved.getId())).isTrue();
        assertThat(departamentoRepository.existsById(999)).isFalse();
    }

    @Test
    @DisplayName("Should count total number of Departamentos")
    void testCount() {
        // Given
        entityManager.persistAndFlush(departamento1);
        entityManager.persistAndFlush(departamento2);
        
        // When
        long count = departamentoRepository.count();
        
        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should save multiple Departamentos")
    void testSaveAll() {
        // Given
        List<Departamento> departamentos = List.of(departamento1, departamento2, departamento3);
        
        // When
        List<Departamento> saved = departamentoRepository.saveAll(departamentos);
        
        // Then
        assertThat(saved).hasSize(3);
        assertThat(saved).allMatch(d -> d.getId() != null);
    }

    @Test
    @DisplayName("Should delete all Departamentos")
    void testDeleteAll() {
        // Given
        entityManager.persistAndFlush(departamento1);
        entityManager.persistAndFlush(departamento2);
        
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
        Departamento departamentoWithNulls = new Departamento();
        departamentoWithNulls.setM2(null);
        departamentoWithNulls.setPrecio(null);
        
        // When
        Departamento saved = departamentoRepository.save(departamentoWithNulls);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isNull();
        assertThat(saved.getPrecio()).isNull();
    }

    @Test
    @DisplayName("Should save Departamento with zero values")
    void testSaveWithZeroValues() {
        // Given
        Departamento departamentoWithZeros = new Departamento();
        departamentoWithZeros.setM2(0);
        departamentoWithZeros.setPrecio(0.0);
        
        // When
        Departamento saved = departamentoRepository.save(departamentoWithZeros);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isZero();
        assertThat(saved.getPrecio()).isZero();
    }

    @Test
    @DisplayName("Should save Departamento with large values")
    void testSaveWithLargeValues() {
        // Given
        Departamento departamentoWithLargeValues = new Departamento();
        departamentoWithLargeValues.setM2(Integer.MAX_VALUE);
        departamentoWithLargeValues.setPrecio(Double.MAX_VALUE);
        
        // When
        Departamento saved = departamentoRepository.save(departamentoWithLargeValues);
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getM2()).isEqualTo(Integer.MAX_VALUE);
        assertThat(saved.getPrecio()).isEqualTo(Double.MAX_VALUE);
    }
}
