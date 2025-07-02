package com.inndata.modulo4.clase1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViajesResponse {

    private Integer id;
    private String destino;
    private Double precio;
    private String fechaSalida;

}
