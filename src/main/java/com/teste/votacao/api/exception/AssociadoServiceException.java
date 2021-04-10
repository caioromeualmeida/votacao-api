package com.teste.votacao.api.exception;

public class AssociadoServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public AssociadoServiceException(String message){
        super(message);
    }
}
