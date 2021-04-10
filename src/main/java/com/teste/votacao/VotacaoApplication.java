package com.teste.votacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotacaoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(VotacaoApplication.class);

	//consome a fila de mensagens e exibe no console apenas para fins de validação
    @RabbitListener(queues = "mq")
    public void receive(final String mensagem) {
		log.info("Mensagem recebida: " + mensagem);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(VotacaoApplication.class, args);
	}
}
