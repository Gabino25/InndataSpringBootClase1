package com.inndata.modulo4.clase1.repository;

import com.inndata.modulo4.clase1.entity.PersonadEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("PersonaD Repository Integration Tests with MockBean")
class PersonadRepositoryMockTest {

    @Mock
    PersonadRepository personadRepository;

    private PersonadEntity persona1;
    private PersonadEntity persona2;
    private PersonadEntity persona3;

    @BeforeEach
    void setUp() {
        persona1 = new PersonadEntity();
        persona1.setNombre("Juan Perez");
        persona1.setDireccion("Calle Falsa 123");
        persona1.setEdad(30);
        persona1.setIdDepartamento(1);

        persona2 = new PersonadEntity();
        persona2.setNombre("Maria Lopez");
        persona2.setDireccion("Avenida Siempre Viva 456");
        persona2.setEdad(25);
        persona2.setIdDepartamento(2);

        persona3 = new PersonadEntity();
        persona3.setNombre("Carlos Sanchez");
        persona3.setDireccion("Boulevard de los Sueños Rotos 789");
        persona3.setEdad(40);
        persona3.setIdDepartamento(3);
    }

    @Test
    @DisplayName("Should save and retrieve a PersonaD")
    void testSave() {
        // Given
        PersonadEntity savedPersona = persona1;
        savedPersona.setId(1);

        // Mocking the save method
        when(personadRepository.save(any(PersonadEntity.class))).thenReturn(savedPersona);

        // When
        PersonadEntity result = personadRepository.save(persona1);
        Optional<PersonadEntity> savedEntity = Optional.of(result);

        // Then
        assertThat(savedEntity).isPresent();
        assertThat(savedEntity.get().getNombre()).isEqualTo("Juan Perez");
        assertThat(savedEntity.get().getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(savedEntity.get().getEdad()).isEqualTo(30);
        assertThat(savedEntity.get().getIdDepartamento()).isEqualTo(1);

        verify(personadRepository,times(1)).save(persona1);
    }

    @Test
    @DisplayName("Should find a PersonaD by ID")
    void testFindById() {
        // Given
        when(personadRepository.findById(1)).thenReturn(Optional.of(persona1));
        when(personadRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<PersonadEntity> foundPersona = personadRepository.findById(1);
        Optional<PersonadEntity> notFoundPersona = personadRepository.findById(999);

        // Then
        assertThat(foundPersona).isPresent();
        assertThat(foundPersona.get().getNombre()).isEqualTo("Juan Perez");
        assertThat(notFoundPersona).isEmpty();

        verify(personadRepository, times(1)).findById(1);
        verify(personadRepository, times(1)).findById(999);

    }


    @Test
    @DisplayName("test findAll")
    void testFindAll() {
        // Given
        when(personadRepository.findAll()).thenReturn(List.of(persona1, persona2));

        // When
        List<PersonadEntity> result = personadRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombre()).isEqualTo("Juan Perez");
        assertThat(result.get(1).getNombre()).isEqualTo("Maria Lopez");

        verify(personadRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Should return empty list when no PersonaD exist")
    void testFindAllEmpty() {
        // Given
        when(personadRepository.findAll()).thenReturn(List.of());

        // When
        List<PersonadEntity> result = personadRepository.findAll();

        // Then
        assertThat(result).isEmpty();
        verify(personadRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test update PersonaD")
    void testUpdatePersonaD() {
        // Given
        persona1.setNombre("Juan Perez Updated");
        persona1.setEdad(35);
        when(personadRepository.save(any(PersonadEntity.class))).thenReturn(persona1);

        // When
        PersonadEntity updatedPersona = personadRepository.save(persona1);

        // Then
        assertThat(updatedPersona.getNombre()).isEqualTo("Juan Perez Updated");
        assertThat(updatedPersona.getEdad()).isEqualTo(35);
        verify(personadRepository, times(1)).save(persona1);
    }

    @Test
    @DisplayName("Should delete a PersonaD by ID")
    void testDeleteById() {
        // Given
        doNothing().when(personadRepository).deleteById(1);
        when(personadRepository.findById(1)).thenReturn(Optional.empty());

        // When
        personadRepository.deleteById(1);
        Optional<PersonadEntity> found = personadRepository.findById(1);

        // Then
        assertThat(found).isEmpty();
        verify(personadRepository, times(1)).deleteById(1);
    }

}
