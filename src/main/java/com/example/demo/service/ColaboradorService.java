package com.example.demo.service;


import com.example.demo.domain.ColaboradorDTO;
import com.example.demo.domain.Colaborador;
import com.example.demo.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final PasswordService passwordService;

    public ColaboradorService(ColaboradorRepository colaboradorRepository, PasswordService passwordService) {
        this.colaboradorRepository = colaboradorRepository;
        this.passwordService = passwordService;
    }

    public Colaborador save(Colaborador colaborador) {
        colaborador.setPasswordScore(passwordService.calculatePasswordStrength(colaborador.getPassword()));
        return colaboradorRepository.save(colaborador);
    }

    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    public List<ColaboradorDTO> findHierarchy() {
        List<ColaboradorDTO> colaboradorDTOS = new ArrayList<>();

        List<Colaborador> allByLiderIsNull = colaboradorRepository.findAllByLiderIsNull();

        allByLiderIsNull.forEach(lider -> colaboradorDTOS.addAll(getSubordinados(lider)));

        return colaboradorDTOS;
    }

    private List<ColaboradorDTO> getSubordinados(Colaborador lider) {
        List<ColaboradorDTO> colaboradorDTOS = new ArrayList<>();

        List<Colaborador> byLider = colaboradorRepository.findByLider(lider);

        colaboradorDTOS.add(new ColaboradorDTO(lider.getName(), lider.getPassword(), lider.getPasswordScore(),
                byLider.stream().flatMap(sub -> getSubordinados(sub).stream()).toList()));

        return colaboradorDTOS;
    }


}