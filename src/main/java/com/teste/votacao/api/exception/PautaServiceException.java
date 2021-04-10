package com.teste.votacao.api.exception;

public class PautaServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public PautaServiceException(String message){
        super(message);
    }
}
