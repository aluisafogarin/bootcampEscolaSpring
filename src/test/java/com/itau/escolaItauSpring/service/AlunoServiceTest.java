package com.itau.escolaItauSpring.service;

import com.itau.escolaItauSpring.config.mapper.AlunoMapper;
import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.exception.ItemNaoExistenteException;
import com.itau.escolaItauSpring.helpers.AlunoServiceHelpers;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    AlunoService service;

    @Mock
    AlunoMapper alunoMapper;

    @Mock
    AlunoRepository alunoRepository;

    @Test
    @DisplayName("Testa adicao do aluno na base")
    public void adicionar() {
        var aluno = new Aluno();

        Mockito.when(alunoMapper.toModel(Mockito.any(AlunoRequest.class))).thenReturn(aluno);
        Mockito.when(alunoMapper.toResponse(ArgumentMatchers.any(Aluno.class))).thenReturn(new AlunoResponse());
        Mockito.when(alunoRepository.save(ArgumentMatchers.any(Aluno.class))).thenReturn(aluno);


        service.adicionar(new AlunoRequest());


        Mockito.verify(alunoRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(alunoMapper, Mockito.times(1)).toResponse(Mockito.any());
    }

    @Test
    @DisplayName("Quando aluno for existente faz a ativacao do mesmo")
    public void ativar() throws ItemNaoExistenteException {

        var aluno = new Aluno();
        UUID id = aluno.getId();

        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(aluno));


        service.ativar(id);


        assertTrue(aluno.getAtivado());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    @DisplayName("Quando aluno nao existe lanca item nao encontrado exception")
    public void ativar_QuandoAlunoNaoExiste() {
        Mockito.when(alunoRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());


        assertThrows(ItemNaoExistenteException.class, () -> service.ativar(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Desativa aluno")
    public void desativar() {

        var aluno = new Aluno();


        service.desativar(aluno);


        verify(alunoRepository, times(1)).save(aluno);
        assertFalse(aluno.getAtivado());
    }

    @Test
    @DisplayName("Listar alunos")
    public void listarTest() {
        Aluno aluno = AlunoServiceHelpers.criaAluno();
        List<AlunoResponse> alunosResponse = AlunoServiceHelpers.listaDeAlunoResponse(aluno);
        when(alunoRepository.findAll()).thenReturn(Collections.singletonList(aluno));
        when(alunoMapper.mapAluno(ArgumentMatchers.any())).thenReturn(alunosResponse);


        List<AlunoResponse> resultadoListar = service.listar();


        verify(alunoRepository, times(1)).findAll();
        assertEquals(alunosResponse, resultadoListar);
        assertEquals(alunosResponse.get(0), resultadoListar.get(0));

    }

    @Test
    @DisplayName("Localizar quando o aluno ItemNaoExistenteException")
    public void localizar_QuandoAlunoNaoExistir() {
        when(alunoRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(ItemNaoExistenteException.class, () -> service.localizar(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Localizar aluno existente")
    public void localizar_AlunoExistente() throws ItemNaoExistenteException {
        var aluno = new Aluno();
        var alunoResponse =  new AlunoResponse();
        alunoResponse.setId(aluno.getId());

        when(alunoRepository.findById(any())).thenReturn(Optional.of(aluno));
        when(alunoMapper.toResponse(aluno)).thenReturn(alunoResponse);


        var result = service.localizar(aluno.getId());


        assertEquals(result.getId(), alunoResponse.getId());
        assertEquals(result, alunoResponse);
        assertEquals(aluno.getId(), alunoResponse.getId());
    }

    @Test
    @DisplayName("Verifica quantidade de alunos ativos")
    public void quantidadeAlunosAtivo() {
        final long QUANTIDADE_ALUNOS_ATIVOS_ESPERADA = 2L;
        when(alunoRepository.countAlunoByAtivado(true)).thenReturn(QUANTIDADE_ALUNOS_ATIVOS_ESPERADA);


        var quantidadeAlunosAtivo = service.quantidadeAlunosAtivo();


        assertEquals(QUANTIDADE_ALUNOS_ATIVOS_ESPERADA, quantidadeAlunosAtivo);
    }

    @Test
    @DisplayName("Verifica remocao do cpf")
    public void removerPorCpf() {
        final long CPF = 123456;

        // when(alunoRepository.deleteByCpf(CPF)).thenReturn();

        service.removerPorCpf(CPF);


        verify(alunoRepository, times(1)).deleteByCpf(CPF);
    }

    @Test
    @DisplayName("Verifica busca por nome")
    public void buscaPorNome(){
        final String nome = "Ana";

        when(alunoRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(Collections.singletonList(new Aluno()));


        var result =  service.buscarPorNome(nome);


        verify(alunoRepository, times(1)).findByNomeContainingIgnoreCase(nome);
        assertEquals(1, result.size());
    }

}

