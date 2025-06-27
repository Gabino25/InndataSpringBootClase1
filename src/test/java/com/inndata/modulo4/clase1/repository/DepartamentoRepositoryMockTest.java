package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.Departamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Departamento Repository Integration Tests with MockBean")
class DepartamentoRepositoryMockTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    private Departamento testDepartamento1;
    private Departamento testDepartamento2;

    @BeforeEach
    void setUp() {
        testDepartamento1 = new Departamento();
        testDepartamento1.setId(1);
        testDepartamento1.setM2(75);
        testDepartamento1.setPrecio(135000.0);

        testDepartamento2 = new Departamento();
        testDepartamento2.setId(2);
        testDepartamento2.setM2(110);
        testDepartamento2.setPrecio(200000.0);
    }

    @Test
    @DisplayName("Should mock findAll method successfully")
    void testMockFindAll() {
        // Given
        List<Departamento> mockDepartamentos = Arrays.asList(testDepartamento1, testDepartamento2);
        when(departamentoRepository.findAll()).thenReturn(mockDepartamentos);

        // When
        List<Departamento> result = departamentoRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getM2()).isEqualTo(75);
        assertThat(result.get(1).getPrecio()).isEqualTo(200000.0);
        verify(departamentoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should mock findById method successfully")
    void testMockFindById() {
        // Given
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamento1));
        when(departamentoRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Departamento> existingResult = departamentoRepository.findById(1);
        Optional<Departamento> nonExistingResult = departamentoRepository.findById(999);

        // Then
        assertThat(existingResult).isPresent();
        assertThat(existingResult.get().getId()).isEqualTo(1);
        assertThat(nonExistingResult).isEmpty();

        verify(departamentoRepository, times(1)).findById(1);
        verify(departamentoRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Should mock save method successfully")
    void testMockSave() {
        // Given
        Departamento newDepartamento = new Departamento();
        newDepartamento.setM2(90);
        newDepartamento.setPrecio(165000.0);

        Departamento savedDepartamento = new Departamento();
        savedDepartamento.setId(3);
        savedDepartamento.setM2(90);
        savedDepartamento.setPrecio(165000.0);

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(savedDepartamento);

        // When
        Departamento result = departamentoRepository.save(newDepartamento);

        // Then
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getM2()).isEqualTo(90);
        assertThat(result.getPrecio()).isEqualTo(165000.0);
        verify(departamentoRepository, times(1)).save(newDepartamento);
    }

    @Test
    @DisplayName("Should mock deleteById method successfully")
    void testMockDeleteById() {
        // Given
        doNothing().when(departamentoRepository).deleteById(1);

        // When
        departamentoRepository.deleteById(1);

        // Then
        verify(departamentoRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Should mock existsById method successfully")
    void testMockExistsById() {
        // Given
        when(departamentoRepository.existsById(1)).thenReturn(true);
        when(departamentoRepository.existsById(999)).thenReturn(false);

        // When
        boolean exists = departamentoRepository.existsById(1);
        boolean notExists = departamentoRepository.existsById(999);

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
        verify(departamentoRepository, times(1)).existsById(1);
        verify(departamentoRepository, times(1)).existsById(999);
    }

    @Test
    @DisplayName("Should mock count method successfully")
    void testMockCount() {
        // Given
        when(departamentoRepository.count()).thenReturn(10L);

        // When
        long count = departamentoRepository.count();

        // Then
        assertThat(count).isEqualTo(10L);
        verify(departamentoRepository, times(1)).count();
    }

    @Test
    @DisplayName("Should mock saveAll method successfully")
    void testMockSaveAll() {
        // Given
        List<Departamento> departamentosToSave = Arrays.asList(testDepartamento1, testDepartamento2);
        when(departamentoRepository.saveAll(anyList())).thenReturn(departamentosToSave);

        // When
        List<Departamento> savedDepartamentos = departamentoRepository.saveAll(departamentosToSave);

        // Then
        assertThat(savedDepartamentos).hasSize(2);
        assertThat(savedDepartamentos).containsExactlyElementsOf(departamentosToSave);
        verify(departamentoRepository, times(1)).saveAll(departamentosToSave);
    }

    @Test
    @DisplayName("Should mock deleteAll method successfully")
    void testMockDeleteAll() {
        // Given
        doNothing().when(departamentoRepository).deleteAll();

        // When
        departamentoRepository.deleteAll();

        // Then
        verify(departamentoRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Should verify method call order")
    void testMethodCallOrder() {
        // Given
        when(departamentoRepository.existsById(1)).thenReturn(true);
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamento1));
        doNothing().when(departamentoRepository).deleteById(1);

        // When
        boolean exists = departamentoRepository.existsById(1);
        Optional<Departamento> found = departamentoRepository.findById(1);
        departamentoRepository.deleteById(1);

        // Then
        assertThat(exists).isTrue();
        assertThat(found).isPresent();

        // Verify the order of method calls
        var inOrder = inOrder(departamentoRepository);
        inOrder.verify(departamentoRepository).existsById(1);
        inOrder.verify(departamentoRepository).findById(1);
        inOrder.verify(departamentoRepository).deleteById(1);
    }

    @Test
    @DisplayName("Should verify method was never called")
    void testMethodNeverCalled() {
        // Given
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamento1));

        // When
        departamentoRepository.findById(1);

        // Then
        verify(departamentoRepository, times(1)).findById(1);
        verify(departamentoRepository, never()).deleteById(anyInt());
        verify(departamentoRepository, never()).save(any(Departamento.class));
    }

    @Test
    @DisplayName("Should test with argument captors")
    void testWithArgumentCaptor() {
        // Given
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(testDepartamento1);

        Departamento departamentoToSave = new Departamento();
        departamentoToSave.setM2(85);
        departamentoToSave.setPrecio(155000.0);

        // When
        departamentoRepository.save(departamentoToSave);

        // Then
        verify(departamentoRepository, times(1)).save(argThat(dept -> 
            dept.getM2().equals(85) && dept.getPrecio().equals(155000.0)
        ));
    }

    @Test
    @DisplayName("Should mock repository with exception throwing")
    void testMockWithException() {
        // Given
        when(departamentoRepository.findById(999))
            .thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        try {
            departamentoRepository.findById(999);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Database connection error");
        }

        verify(departamentoRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Should test multiple invocations with different returns")
    void testMultipleInvocations() {
        // Given
        when(departamentoRepository.count())
            .thenReturn(5L)
            .thenReturn(6L)
            .thenReturn(7L);

        // When
        long count1 = departamentoRepository.count();
        long count2 = departamentoRepository.count();
        long count3 = departamentoRepository.count();

        // Then
        assertThat(count1).isEqualTo(5L);
        assertThat(count2).isEqualTo(6L);
        assertThat(count3).isEqualTo(7L);
        verify(departamentoRepository, times(3)).count();
    }
}
