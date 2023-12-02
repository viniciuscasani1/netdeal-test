package com.example.demo.repository;

import com.example.demo.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findAllByLiderIsNull();

    List<Colaborador> findByLider(Colaborador lider);
}