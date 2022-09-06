package com.itau.escolaItauSpring.service;


import com.itau.escolaItauSpring.config.mapper.AlunoMapper;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.exception.ItemNaoExistenteException;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes={AlunoService.class, AlunoMapper.class, AlunoRepository.class})
@SpringBootTest
public class AlunoServiceIntegrationTest {

    private final AlunoService service;

    private final AlunoRepository repository;

    @Autowired
    public AlunoServiceIntegrationTest(AlunoService service, AlunoRepository repository) {
        this.service = service;
        this.repository = repository;

    }

    @Test
    public void adicionar() {
        service.adicionar(new AlunoRequest());

        Assertions.assertEquals(1, service.listar().size());
    }

    @Test
    public void ativar() throws ItemNaoExistenteException {
        service.adicionar(new AlunoRequest());

        AlunoResponse alunoResponse = service.listar().get(0);

        UUID id = alunoResponse.getId();

        Aluno aluno = repository.findById(id).get();
        aluno.setAtivado(Boolean.FALSE);
        // Optional<Aluno> aluno1 = repository.findById(service.listar().get(0).getId());

        Assertions.assertFalse(aluno.getAtivado());

        service.ativar(aluno.getId());
        Assertions.assertTrue(aluno.getAtivado());
    }
}
