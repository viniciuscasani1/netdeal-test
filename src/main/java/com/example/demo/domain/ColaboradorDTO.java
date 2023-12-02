package com.example.demo.domain;

import java.util.List;

public record ColaboradorDTO(String name, String password, Integer passwordScore, List<ColaboradorDTO> subordinados) {
}
