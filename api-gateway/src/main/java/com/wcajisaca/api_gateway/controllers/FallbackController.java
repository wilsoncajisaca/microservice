package com.wcajisaca.api_gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping
    public String fallbackGet() {
        return "Servicio no disponible en este momento. Inténtalo más tarde.";
    }
}