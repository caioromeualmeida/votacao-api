package com.teste.votacao.api.exception;

public class RecursoNaoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public RecursoNaoEncontradoException(String message){
        super(message);
    }
}
