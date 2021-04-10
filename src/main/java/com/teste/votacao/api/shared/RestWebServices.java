package com.teste.votacao.api.shared;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.votacao.api.exception.AssociadoServiceException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestWebServices {
    //Realiza o consumo do webservice que valida o cpf do usuário
    public static Boolean permitidoVotar(String cpf){
        Map<String, String> map = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://user-info.herokuapp.com/users/" + cpf;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if(response.getStatusCode().value() == 404){
            throw new AssociadoServiceException("CPF inválido.");
        }

        try {
            map = mapper.readValue(response.getBody(), new TypeReference<HashMap<String, String>>() {});
            return map.get("status").equals("ABLE_TO_VOTE") ? true : false;
        } catch (Exception e) {
            throw new AssociadoServiceException(e.getLocalizedMessage());
        }
    }
}