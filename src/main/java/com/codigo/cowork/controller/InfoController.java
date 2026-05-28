package com.codigo.cowork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "aplicacion", "CoWork API - Sistema de Reservas de Sala",
                "version",    "1.0.0",
                "autor",      "Alberto Prado"
        );
    }
}
