package com.teste.votacao.api.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.teste.votacao.api.DTO.PautaDetalheDto;
import com.teste.votacao.api.DTO.PautaDto;
import com.teste.votacao.api.DTO.VotoDetalheDto;
import com.teste.votacao.api.DTO.VotoDto;
import com.teste.votacao.api.exception.PautaServiceException;
import com.teste.votacao.api.exception.RecursoNaoEncontradoException;
import com.teste.votacao.api.mapper.PautaMapper;
import com.teste.votacao.api.mapper.VotoMapper;
import com.teste.votacao.api.model.Associado;
import com.teste.votacao.api.model.Pauta;
import com.teste.votacao.api.model.Voto;
import com.teste.votacao.api.model.VotoId;
import com.teste.votacao.api.repository.AssociadoRepository;
import com.teste.votacao.api.repository.PautaRepository;
import com.teste.votacao.api.repository.VotoRepository;
import com.teste.votacao.api.service.PautaService;
import com.teste.votacao.api.shared.RestWebServices;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaServiceImpl implements PautaService {
    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaMapper pautaMapper;
    
    @Autowired
    private VotoMapper votoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private Binding binding;
 
    @Override
    public PautaDetalheDto novaPauta(PautaDto pautaDto) {
        int tempoSessao = pautaDto.getTempoSessao() == 0 ? 1 : pautaDto.getTempoSessao();
        Date prazoLimite = setPrazoLimite(tempoSessao);
        pautaDto.setPrazoVotacao(prazoLimite);
        pautaDto.setTempoSessao(tempoSessao);
        Pauta novaPauta = pautaRepository.save(pautaMapper.toModel(pautaDto));
        String mensagem = "Pauta: " + novaPauta.getId() + " foi encerrada.";

        //Realiza o envio da mensagem ao rabbitmq ao finalizar o prazo da votação.
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), mensagem, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(tempoSessao * 60000);
                return message;
            }
        });
        
        return pautaMapper.toDetalheDto(novaPauta);
    }

    @Override
    public PautaDetalheDto getPauta(Long pautaId) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if(pauta.isPresent()){
            Long votosSim = getQuantidadeVotos(pauta.get().getVotos(), true);
            Long votosNao = getQuantidadeVotos(pauta.get().getVotos(), false);

            PautaDetalheDto pautaDetalheDto = pautaMapper.toDetalheDto(pauta.get());

            if(isVotacaoEmAberto(pauta.get().getPrazoVotacao())){
                pautaDetalheDto.setResultado("Votação em andamento.");
            }else{
                pautaDetalheDto.setResultado(getResultadoVotos(votosSim, votosNao));
            }
            
            pautaDetalheDto.setVotosSim(votosSim);
            pautaDetalheDto.setVotosNao(votosNao);

            return pautaDetalheDto;
        }else{
            throw new RecursoNaoEncontradoException("Pauta não encontrada!");
        }
    }

    @Override
    public List<PautaDetalheDto> getPautas() {
        List<Pauta> pautas = pautaRepository.findAll();
        return pautaMapper.toDetalhesDto(pautas);
    }

    @Override
    public VotoDetalheDto novoVoto(Long pautaId, VotoDto votoDto) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if(pauta.isPresent()){
            if(isVotacaoEmAberto(pauta.get().getPrazoVotacao())){
                if(RestWebServices.permitidoVotar(votoDto.getCpf())){
                    Optional<Associado> associado = associadoRepository.findAssociadoByCpf(votoDto.getCpf());
                    if(associado.isEmpty()){
                        Associado novoAssociado = adicionarAssociado(votoDto);
                        Voto novoVoto = adicionarVoto(novoAssociado, pautaId, votoDto.getVoto());
                        return votoMapper.toDetalheDto(novoVoto);
                    }else{                    
                        Voto novoVoto = adicionarVoto(associado.get(), pautaId, votoDto.getVoto());
                        return votoMapper.toDetalheDto(novoVoto);
                    }
                }else{
                    throw new PautaServiceException("Não é permitido votar com o CPF informado.");
                }
            }else{
                throw new PautaServiceException("Terminado o prazo de votação.");
            }
        }else{
            throw new RecursoNaoEncontradoException("Pauta não encontrada!");
        }
    }

    @Override
    public List<VotoDetalheDto> getVotos(Long pautaId) {
        List<Voto> votos = votoRepository.findByVotoIdPautaId(pautaId);
        return votoMapper.toDetalhesDto(votos);
    }

    public Associado adicionarAssociado(VotoDto votoDto){
        Associado novoAssociado = new Associado();
        novoAssociado.setCpf(votoDto.getCpf());
        return associadoRepository.save(novoAssociado);
    }

    public Voto adicionarVoto(Associado associado, Long pautaId, Boolean voto){
        VotoId votoId = new VotoId();
        votoId.setAssociadoId(associado.getId());
        votoId.setPautaId(pautaId);

        Voto novoVoto = new Voto();
        novoVoto.setAssociado(associado);
        novoVoto.setVotoId(votoId);
        novoVoto.setVoto(voto);

        return votoRepository.save(novoVoto);
    }

    public Date setPrazoLimite(int tempoSessao){
        Date dataAtual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        calendar.add(Calendar.MINUTE, tempoSessao);
        return calendar.getTime();
    }

    public Long getQuantidadeVotos(List<Voto> votos, Boolean filtro){
        return votos.stream().filter(p -> p.getVoto().equals(filtro)).count();
    }

    public String getResultadoVotos(Long votosSim, Long VotosNao){
        if(votosSim > VotosNao){
            return "Pauta aprovada.";
        }else if(votosSim < VotosNao){
            return "Pauta não aprovada.";
        }else{
            return "Empate no número de votos.";
        }
    }

    public boolean isVotacaoEmAberto(Date prazoVotacao){
        Date agora = new Date();
        return agora.before(prazoVotacao);
    }
}
