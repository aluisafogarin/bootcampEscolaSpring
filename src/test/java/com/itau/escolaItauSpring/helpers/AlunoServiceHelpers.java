package com.itau.escolaItauSpring.helpers;

import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.model.Aluno;

import java.util.Collections;
import java.util.List;

public class AlunoServiceHelpers {
    public static Aluno criaAluno() {
        AlunoRequest alunoRequest = new AlunoRequest();
        alunoRequest.setNome("Pichau");
        alunoRequest.setCpf(123L);
        alunoRequest.setIdade(15);
        return new Aluno(alunoRequest);
    }

    public static List<AlunoResponse> listaDeAlunoResponse(Aluno aluno) {
        AlunoResponse alunoResponse = new AlunoResponse();
        alunoResponse.setId(aluno.getId());
        alunoResponse.setIdade(aluno.getIdade());
        alunoResponse.setCpf(aluno.getCpf());
        alunoResponse.setNome(aluno.getNome());

        return Collections.singletonList(alunoResponse);
    }
}
