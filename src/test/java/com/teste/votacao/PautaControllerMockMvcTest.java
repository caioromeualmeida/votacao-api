package com.teste.votacao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.votacao.api.DTO.PautaDetalheDto;
import com.teste.votacao.api.DTO.PautaDto;
import com.teste.votacao.api.DTO.VotoDto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class PautaControllerMockMvcTest {
    
    @Autowired 
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;

    private static PautaDto pautaDto;

    private static VotoDto votoDto;

    private ResultActions resultadoAcao;

    private MvcResult resultadoMvc;

    @BeforeAll
    public static void setup() throws ParseException {
        pautaDto = new PautaDto();
        pautaDto.setDescricao("Pauta 1");

        votoDto = new VotoDto();
        votoDto.setCpf("04324619816");
        votoDto.setVoto(true);
    }

    @Test
    public void novaPauta() throws Exception {
        String pautaDtoJson = mapper.writeValueAsString(pautaDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pautas")
        .contentType(MediaType.APPLICATION_JSON)
        .content(pautaDtoJson))
        .andExpect(status().isCreated());
    }

    @Test
    public void getPautas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pautas")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }    

    @Test
    public void getPauta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pautas/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    } 

    @Test
    public void novoVoto() throws Exception {        
        //cria a pauta
        String pautaDtoJson = mapper.writeValueAsString(pautaDto);
        resultadoAcao = mockMvc.perform(MockMvcRequestBuilders.post("/v1/pautas")
        .contentType(MediaType.APPLICATION_JSON)
        .content(pautaDtoJson))
        .andExpect(status().isCreated());

        //converte o resultado
        resultadoMvc = resultadoAcao.andReturn();
        String pautaDetalheDtoString = resultadoMvc.getResponse().getContentAsString();
        PautaDetalheDto pautaDetalheDto = mapper.readValue(pautaDetalheDtoString, PautaDetalheDto.class);
        
        //realiza o voto
        String votoDtoJson = mapper.writeValueAsString(votoDto);
        resultadoAcao = mockMvc.perform(MockMvcRequestBuilders.post("/v1/pautas/" + pautaDetalheDto.getId() + "/voto")
        .contentType(MediaType.APPLICATION_JSON)
        .content(votoDtoJson));
 
        assertTrue(
            resultadoMvc.getResponse().getStatus() == 200 || resultadoMvc.getResponse().getStatus() == 201
        );
    }    

    @Test
    public void getVotos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pautas/1/voto")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }    
}
