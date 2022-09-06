package com.itau.escolaItauSpring.config.mapper;

import com.itau.escolaItauSpring.dto.request.AlunoRequest;
import com.itau.escolaItauSpring.dto.request.CursoRequest;
import com.itau.escolaItauSpring.dto.request.EnderecoRequest;
import com.itau.escolaItauSpring.dto.request.NotaRequest;
import com.itau.escolaItauSpring.dto.request.ProfessorRequest;
import com.itau.escolaItauSpring.dto.response.AlunoResponse;
import com.itau.escolaItauSpring.dto.response.CursoResponse;
import com.itau.escolaItauSpring.model.Aluno;
import com.itau.escolaItauSpring.model.Curso;
import com.itau.escolaItauSpring.model.Endereco;
import com.itau.escolaItauSpring.model.Nota;
import com.itau.escolaItauSpring.model.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-02T11:08:07-0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 11.0.16 (Oracle Corporation)"
)
@Component
public class AlunoMapperImpl implements AlunoMapper {

    @Override
    public AlunoResponse toResponse(Aluno aluno) {
        if ( aluno == null ) {
            return null;
        }

        AlunoResponse alunoResponse = new AlunoResponse();

        alunoResponse.setId( aluno.getId() );
        alunoResponse.setNome( aluno.getNome() );
        alunoResponse.setIdade( aluno.getIdade() );
        alunoResponse.setCpf( aluno.getCpf() );

        return alunoResponse;
    }

    @Override
    public Aluno toModel(AlunoRequest alunoRequest) {
        if ( alunoRequest == null ) {
            return null;
        }

        Aluno aluno = new Aluno();

        aluno.setNome( alunoRequest.getNome() );
        aluno.setIdade( alunoRequest.getIdade() );
        aluno.setCpf( alunoRequest.getCpf() );
        aluno.setEndereco( enderecoRequestToEndereco( alunoRequest.getEndereco() ) );
        aluno.setNotas( notaRequestListToNotaList( alunoRequest.getNotas() ) );
        aluno.setCursos( cursoRequestListToCursoList( alunoRequest.getCursos() ) );

        return aluno;
    }

    @Override
    public List<AlunoResponse> mapAluno(List<Aluno> alunos) {
        if ( alunos == null ) {
            return null;
        }

        List<AlunoResponse> list = new ArrayList<AlunoResponse>( alunos.size() );
        for ( Aluno aluno : alunos ) {
            list.add( toResponse( aluno ) );
        }

        return list;
    }

    @Override
    public CursoResponse campoToResponse(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoResponse cursoResponse = new CursoResponse();

        cursoResponse.setNome( curso.getNome() );

        return cursoResponse;
    }

    @Override
    public Curso requestToModel(CursoRequest cursoRequest) {
        if ( cursoRequest == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setNome( cursoRequest.getNome() );
        curso.setProfessor( professorRequestToProfessor( cursoRequest.getProfessor() ) );

        return curso;
    }

    protected Endereco enderecoRequestToEndereco(EnderecoRequest enderecoRequest) {
        if ( enderecoRequest == null ) {
            return null;
        }

        Endereco endereco = new Endereco();

        endereco.setLogradouro( enderecoRequest.getLogradouro() );
        endereco.setNumero( enderecoRequest.getNumero() );
        endereco.setCep( enderecoRequest.getCep() );
        endereco.setComplemento( enderecoRequest.getComplemento() );
        endereco.setCidade( enderecoRequest.getCidade() );
        endereco.setEstado( enderecoRequest.getEstado() );

        return endereco;
    }

    protected Nota notaRequestToNota(NotaRequest notaRequest) {
        if ( notaRequest == null ) {
            return null;
        }

        Nota nota = new Nota();

        nota.setConteudo( notaRequest.getConteudo() );
        nota.setValor( notaRequest.getValor() );

        return nota;
    }

    protected List<Nota> notaRequestListToNotaList(List<NotaRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Nota> list1 = new ArrayList<Nota>( list.size() );
        for ( NotaRequest notaRequest : list ) {
            list1.add( notaRequestToNota( notaRequest ) );
        }

        return list1;
    }

    protected List<Curso> cursoRequestListToCursoList(List<CursoRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Curso> list1 = new ArrayList<Curso>( list.size() );
        for ( CursoRequest cursoRequest : list ) {
            list1.add( requestToModel( cursoRequest ) );
        }

        return list1;
    }

    protected Professor professorRequestToProfessor(ProfessorRequest professorRequest) {
        if ( professorRequest == null ) {
            return null;
        }

        Professor professor = new Professor();

        professor.setNome( professorRequest.getNome() );
        professor.setCpf( professorRequest.getCpf() );

        return professor;
    }
}
