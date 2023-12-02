package com.example.demo.controller;

import com.example.demo.domain.ColaboradorDTO;
import com.example.demo.domain.Colaborador;
import com.example.demo.service.ColaboradorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/save")
    public Colaborador save(@RequestBody Colaborador colaborador) {
        return colaboradorService.save(colaborador);
    }

    @GetMapping("/findHierarchy")
    public List<ColaboradorDTO> findHierarchy() {
        return colaboradorService.findHierarchy();
    }

    @GetMapping("/findAll")
    public List<Colaborador> findAll() {
        return colaboradorService.findAll();
    }

}