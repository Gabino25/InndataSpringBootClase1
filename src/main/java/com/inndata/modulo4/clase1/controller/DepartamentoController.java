package  com.inndata.modulo4.clase1.controller;

import com.inndata.modulo4.clase1.entity.Departamento;
import com.inndata.modulo4.clase1.service.impl.DepartamentoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class DepartamentoController {
    @Autowired
    DepartamentoService departamentoService;
    //GetMapping son para los metodos de lectura
    @GetMapping("/departamentos")
    public List<Departamento> readAll(){
        return departamentoService.readAll();
    }
    @GetMapping("/departamentos/{id}")
    public Optional<Departamento> readById(@PathVariable Integer id){
        return departamentoService.readById(id);
    }
    //La diferencia entre create y update es que uno es PutMapping(update)
    // y el otro es PostMapping(create)
    @PostMapping("/departamentos")
    public Departamento create(@RequestBody Departamento departamento){
        return departamentoService.create(departamento);
    }

    @PutMapping("/departamentos")
    public Departamento update(@RequestBody Departamento departamento){
        return departamentoService.update(departamento);
    }

    @DeleteMapping("/departamentos/{id}")
    public String delete(@PathVariable Integer id){
        return departamentoService.deletebyId(id);
    }

    // Este método busca departamentos con m2 mayor que un valor dado
    @GetMapping("/departamentos/m2")
    public List<Departamento> findByM2GreaterThan(@PathParam("m2") Integer m2) {
        return departamentoService.findByM2GreaterThan(m2);
    }

    // Este método busca departamentos con m2 menor que un valor dado y precio mayor que un valor dado
    @GetMapping("/departamentos/m2andprecio")
    public List<Departamento>  m2AndPrecio(@PathParam("m2") Integer m2
                                          ,@PathParam("precio") Double precio) {
        return departamentoService.findByM2LessThanAndPrecioGreaterThan(m2, precio);
    }

    // Este método busca departamentos con m2 menor que un valor dado y precio mayor que un valor dado
    @GetMapping("/departamentos/m2MenorAndPrecioMayor")
    public List<Departamento> m2MenorAndPrecioMayor(@PathParam("m2") Integer m2
                                                   ,@PathParam("precio") Double precio) {
        return departamentoService.m2MenorAndPrecioMayor(m2, precio);
    }



}