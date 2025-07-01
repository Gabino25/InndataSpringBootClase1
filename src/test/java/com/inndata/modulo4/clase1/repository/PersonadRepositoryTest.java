package com.inndata.modulo4.clase1.repository;


import com.inndata.modulo4.clase1.entity.PersonadEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("PersonaD Repository Tests")
class PersonadRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
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
    void testSaveAndFindById() {
        // Given
        PersonadEntity savedPersona = entityManager.persistAndFlush(persona1);

        // When
        Optional<PersonadEntity> found = personadRepository.findById(savedPersona.getId());

        // Then
        assertThat(found.isPresent());
        assertThat(found.get().getNombre()).isEqualTo("Juan Perez");
        assertThat(found.get().getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(found.get().getEdad()).isEqualTo(30);
        assertThat(found.get().getIdDepartamento()).isEqualTo(1);
    }


    @Test
    @DisplayName("Should return empty Optional when PersonaD not found")
    void testFindByIdNotFound() {
        // When
        Optional<PersonadEntity> found = personadRepository.findById(999);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should save a PersonaD and generate ID")
    void testSavePersonaD() {
        // When
        PersonadEntity saved = personadRepository.save(persona1);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNombre()).isEqualTo("Juan Perez");
        assertThat(saved.getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(saved.getEdad()).isEqualTo(30);
        assertThat(saved.getIdDepartamento()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should find all PersonaD")
    void testFindAll() {
        // Given
        entityManager.persistAndFlush(persona1);
        entityManager.persistAndFlush(persona2);
        entityManager.persistAndFlush(persona3);

        // When
        List<PersonadEntity> personas = personadRepository.findAll();

        // Then
        assertThat(personas).hasSize(3);
        assertThat(personas.get(0).getNombre()).isEqualTo("Juan Perez");
        assertThat(personas.get(1).getNombre()).isEqualTo("Maria Lopez");
        assertThat(personas.get(2).getNombre()).isEqualTo("Carlos Sanchez");
    }

    @Test
    @DisplayName("find all empty list when no PersonaD exists")
    void testFindAllEmpty() {
        // When
        List<PersonadEntity> personas = personadRepository.findAll();

        // Then
        assertThat(personas).isEmpty();
    }

    @Test
    @DisplayName("Test Update a PersonaD")
    void testUpdatePersonaD() {
        // Given
        PersonadEntity savedPersona = entityManager.persistAndFlush(persona1);
        savedPersona.setNombre("Juan Perez Updated");
        savedPersona.setDireccion("Calle Actualizada 123");

        // When
        PersonadEntity updatedPersona = personadRepository.save(savedPersona);

        // Then
        assertThat(updatedPersona.getId()).isEqualTo(savedPersona.getId());
        assertThat(updatedPersona.getNombre()).isEqualTo("Juan Perez Updated");
        assertThat(updatedPersona.getDireccion()).isEqualTo("Calle Actualizada 123");
    }

    @Test
    @DisplayName("Should delete a PersonaD by ID")
    void testDeleteById() {
        // Given
        PersonadEntity savedPersona = entityManager.persistAndFlush(persona1);

        // When
        personadRepository.deleteById(savedPersona.getId());
        entityManager.flush();

        // Then
         Optional<PersonadEntity> found = personadRepository.findById(savedPersona.getId());
         assertThat(found).isEmpty();
    }

}
