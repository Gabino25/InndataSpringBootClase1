package com.inndata.modulo4.clase1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonadResponse {

    private Integer id;
    private String nombre;
    private Integer edad;
    private Integer idDepartamento;
    private Double precio;

}
