package com.inndata.modulo4.clase1.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "viajes", url = "https://68635ba488359a373e949c1f.mockapi.io/api/v1/viajes")
public interface ViajesCliente {
  //Seria con la interfaz service

}
