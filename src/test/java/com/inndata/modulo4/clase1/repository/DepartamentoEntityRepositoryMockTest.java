package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.DepartamentoEntity;
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
class DepartamentoEntityRepositoryMockTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    private DepartamentoEntity testDepartamentoEntity1;
    private DepartamentoEntity testDepartamentoEntity2;

    @BeforeEach
    void setUp() {
        testDepartamentoEntity1 = new DepartamentoEntity();
        testDepartamentoEntity1.setId(1);
        testDepartamentoEntity1.setM2(75);
        testDepartamentoEntity1.setPrecio(135000.0);

        testDepartamentoEntity2 = new DepartamentoEntity();
        testDepartamentoEntity2.setId(2);
        testDepartamentoEntity2.setM2(110);
        testDepartamentoEntity2.setPrecio(200000.0);
    }

    @Test
    @DisplayName("Should mock findAll method successfully")
    void testMockFindAll() {
        // Given
        List<DepartamentoEntity> mockDepartamentoEntities = Arrays.asList(testDepartamentoEntity1, testDepartamentoEntity2);
        when(departamentoRepository.findAll()).thenReturn(mockDepartamentoEntities);

        // When
        List<DepartamentoEntity> result = departamentoRepository.findAll();

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
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamentoEntity1));
        when(departamentoRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<DepartamentoEntity> existingResult = departamentoRepository.findById(1);
        Optional<DepartamentoEntity> nonExistingResult = departamentoRepository.findById(999);

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
        DepartamentoEntity newDepartamentoEntity = new DepartamentoEntity();
        newDepartamentoEntity.setM2(90);
        newDepartamentoEntity.setPrecio(165000.0);

        DepartamentoEntity savedDepartamentoEntity = new DepartamentoEntity();
        savedDepartamentoEntity.setId(3);
        savedDepartamentoEntity.setM2(90);
        savedDepartamentoEntity.setPrecio(165000.0);

        when(departamentoRepository.save(any(DepartamentoEntity.class))).thenReturn(savedDepartamentoEntity);

        // When
        DepartamentoEntity result = departamentoRepository.save(newDepartamentoEntity);

        // Then
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getM2()).isEqualTo(90);
        assertThat(result.getPrecio()).isEqualTo(165000.0);
        verify(departamentoRepository, times(1)).save(newDepartamentoEntity);
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
        List<DepartamentoEntity> departamentosToSave = Arrays.asList(testDepartamentoEntity1, testDepartamentoEntity2);
        when(departamentoRepository.saveAll(anyList())).thenReturn(departamentosToSave);

        // When
        List<DepartamentoEntity> savedDepartamentoEntities = departamentoRepository.saveAll(departamentosToSave);

        // Then
        assertThat(savedDepartamentoEntities).hasSize(2);
        assertThat(savedDepartamentoEntities).containsExactlyElementsOf(departamentosToSave);
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
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamentoEntity1));
        doNothing().when(departamentoRepository).deleteById(1);

        // When
        boolean exists = departamentoRepository.existsById(1);
        Optional<DepartamentoEntity> found = departamentoRepository.findById(1);
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
        when(departamentoRepository.findById(1)).thenReturn(Optional.of(testDepartamentoEntity1));

        // When
        departamentoRepository.findById(1);

        // Then
        verify(departamentoRepository, times(1)).findById(1);
        verify(departamentoRepository, never()).deleteById(anyInt());
        verify(departamentoRepository, never()).save(any(DepartamentoEntity.class));
    }

    @Test
    @DisplayName("Should test with argument captors")
    void testWithArgumentCaptor() {
        // Given
        when(departamentoRepository.save(any(DepartamentoEntity.class))).thenReturn(testDepartamentoEntity1);

        DepartamentoEntity departamentoEntityToSave = new DepartamentoEntity();
        departamentoEntityToSave.setM2(85);
        departamentoEntityToSave.setPrecio(155000.0);

        // When
        departamentoRepository.save(departamentoEntityToSave);

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
