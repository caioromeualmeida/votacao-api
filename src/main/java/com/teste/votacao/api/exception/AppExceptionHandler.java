package com.teste.votacao.api.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.teste.votacao.api.model.response.ErrorListResponseModel;
import com.teste.votacao.api.model.response.ErrorResponseModel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    //handler padrão para formatar o corpo das exceptions mais comuns
    @ExceptionHandler(value = {Exception.class, NullPointerException.class, TransactionSystemException.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
        String descricaoErro = ex.getLocalizedMessage();

        if(descricaoErro == null){
            descricaoErro = ex.toString();
        }

        ErrorResponseModel error = new ErrorResponseModel(new Date(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handler que ajusta o corpo da mensagem, caso haja erro no corpo do json informado
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, org.springframework.http.HttpStatus status, WebRequest request) {
        String descricaoErro = "Corpo da mensagem não informado.";

        ErrorResponseModel error = new ErrorResponseModel(new Date(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handler que ajusta o corpo da mensagem, formatando os campos com erros de notação @valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, org.springframework.http.HttpStatus status, WebRequest request) {
        final List<String> mensagensErro = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            mensagensErro.add("Campo " + error.getField() + ": " + error.getDefaultMessage());
        }

        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            mensagensErro.add("Campo " + error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorListResponseModel errors = new ErrorListResponseModel(new Date(), mensagensErro);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handler específico, gerado por conta do status de erro
    @ExceptionHandler(value = {RecursoNaoEncontradoException.class})
    public ResponseEntity<Object> handleRecursoNaoEncontradoException(Exception ex, WebRequest request) {
        String descricaoErro = ex.getLocalizedMessage();

        if(descricaoErro == null){
            descricaoErro = ex.toString();
        }

        ErrorResponseModel error = new ErrorResponseModel(new Date(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    //handler específico, gerado por conta do consumo do webservice
    @ExceptionHandler(value = {PautaServiceException.class})
    public ResponseEntity<Object> handlePautaServiceException(Exception ex, WebRequest request) {
        String descricaoErro = ex.getLocalizedMessage();

        if(descricaoErro == null){
            descricaoErro = ex.toString();
        }

        ErrorResponseModel error = new ErrorResponseModel(new Date(), descricaoErro);
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
