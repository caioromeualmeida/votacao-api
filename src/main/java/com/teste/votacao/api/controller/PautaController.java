package com.teste.votacao.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.teste.votacao.api.DTO.PautaDetalheDto;
import com.teste.votacao.api.DTO.PautaDto;
import com.teste.votacao.api.DTO.VotoDetalheDto;
import com.teste.votacao.api.DTO.VotoDto;
import com.teste.votacao.api.service.PautaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/pautas")
public class PautaController {
    
    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaDetalheDto> novaPauta(@Valid @RequestBody PautaDto pautaDto){
        PautaDetalheDto resposta = pautaService.novaPauta(pautaDto);
        return new ResponseEntity<PautaDetalheDto>(resposta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PautaDetalheDto>> getPautas(){
        List<PautaDetalheDto> resposta = pautaService.getPautas();
        return new ResponseEntity<List<PautaDetalheDto>>(resposta, HttpStatus.OK);
    }

    @GetMapping(path = "/{pautaId}")
    public ResponseEntity<PautaDetalheDto> getPauta(@PathVariable Long pautaId){
        PautaDetalheDto resposta = pautaService.getPauta(pautaId);
        return new ResponseEntity<PautaDetalheDto>(resposta, HttpStatus.OK);
    }

    @PostMapping(path = "/{pautaId}/voto")
    public ResponseEntity<VotoDetalheDto> novoVoto(@PathVariable Long pautaId, @RequestBody VotoDto votoDto){
        VotoDetalheDto resposta = pautaService.novoVoto(pautaId, votoDto);
        return new ResponseEntity<VotoDetalheDto>(resposta, HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{pautaId}/voto")
    public ResponseEntity<List<VotoDetalheDto>> getVotos(@PathVariable Long pautaId){
        List<VotoDetalheDto> resposta = pautaService.getVotos(pautaId);
        return new ResponseEntity<List<VotoDetalheDto>>(resposta, HttpStatus.OK);
    }
}