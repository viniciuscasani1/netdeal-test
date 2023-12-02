package com.example.demo.service;

import com.example.demo.domain.ColaboradorDTO;
import com.example.demo.domain.Colaborador;
import com.example.demo.repository.ColaboradorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {

    @InjectMocks
    private ColaboradorService colaboradorService;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private PasswordService passwordService;

    @Test
    public void should_save_colaborador() {
        Colaborador colaborador = Mockito.mock(Colaborador.class);
        colaborador.setPassword("123@#asbdsa");
        Mockito.when(passwordService.calculatePasswordStrength(colaborador.getPassword())).thenReturn(100);
        colaboradorService.save(colaborador);

        verify(colaboradorRepository).save(colaborador);
    }

    @Test
    public void should_find_all() {
        Colaborador colaborador = Mockito.mock(Colaborador.class);
        when(colaboradorRepository.findAll()).thenReturn(Collections.singletonList(colaborador));
        colaboradorService.findAll();

        verify(colaboradorRepository).findAll();
    }

    @Test
    public void should_get_all_with_hierarchy() {

        Colaborador lider = new Colaborador(1L, "Lider", "pass", 100, null);
        Colaborador sub1 = new Colaborador(2L, "Sub1", "pass", 100, lider);

        when(colaboradorRepository.findAllByLiderIsNull()).thenReturn(Collections.singletonList(lider));
        when(colaboradorRepository.findByLider(lider)).thenReturn(Collections.singletonList(sub1));

        List<ColaboradorDTO> result = colaboradorService.findHierarchy();

        assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        ColaboradorDTO colaboradorDTO = result.get(0);
        assertNotNull(colaboradorDTO);
        assertNotNull(colaboradorDTO.subordinados());
        assertEquals(1, colaboradorDTO.subordinados().size());
        assertEquals("Lider", colaboradorDTO.name());
    }
}