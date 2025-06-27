package com.inndata.modulo4.clase1.controller;

import com.inndata.modulo4.clase1.entity.PersonaD;
import com.inndata.modulo4.clase1.service.impl.PersonadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PersonadController {

    @Autowired
    PersonadService personadService;

    // Aquí puedes agregar métodos para manejar las solicitudes HTTP relacionadas con PersonaD
    // Por ejemplo, métodos para leer, crear, actualizar y eliminar personas

    // Ejemplo de método para leer todas las personas

     @GetMapping("/personas")
     public List<PersonaD> readAll() {
         return personadService.readAll();
     }

    // Puedes agregar más métodos según sea necesario, como create, update, delete, etc.
    // Asegúrate de que los métodos en PersonadService estén implementados correctamente

    // Ejemplo de método para leer una persona por ID
     @GetMapping("/personas/{id}")
     public Optional<PersonaD> readById(@PathVariable Integer id) {
         return personadService.readById(id);
     }

     //Ejemplo de método para crear una nueva persona
     @PostMapping("/personas")
     public PersonaD create(@RequestBody PersonaD personaD) {
         return personadService.create(personaD);
     }

     // Ejemplo de método para actualizar una persona existente
     @PutMapping("/personas")
     public PersonaD update(@RequestBody PersonaD personaD) {
         return personadService.update(personaD);
     }

     // Ejemplo de método para eliminar una persona por ID
     @DeleteMapping("/personas/{id}")
     public String delete(@PathVariable Integer id) {
         return personadService.deletebyId(id);
     }

    // Ejemplo de método para buscar personas por nombre
    @GetMapping("/personas/nombre")
    public List<PersonaD> findByNombreEquals(@RequestParam("nombre") String nombre) {
        return personadService.findByNombreEquals(nombre);
    }


}
