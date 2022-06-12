package com.microservices.estoque.connections;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import constantes.RabbitMQConstantes;


@Component
public class RabbitMQConnections {

	private static final String NOME_EXCHANGE = "amq.direct";
	
	// Objeto de administração do RabbitMQ
	// Injetado pelo spring com as configurações definidas no application.properties
	private AmqpAdmin amqAdmin;
	
	
	public RabbitMQConnections(AmqpAdmin amqAdmin) {
		this.amqAdmin = amqAdmin;
	}


	private Queue getFila(String nomeFila) {
		// durable: segundo parâmetro
		// define se mensagens serão persistidas
		return new Queue(nomeFila, true, false, false);
	}
	
	 
	private DirectExchange getDirectExchange() {
		return new DirectExchange(NOME_EXCHANGE);
	}
	
	
	// Cria relação entre fila e exchange
	private Binding getRelacaoFilaExchange(Queue fila, DirectExchange exchange){
		// routingKey: quarto parâmetro - nome da fila	
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, exchange.getName(), fila.getName(), null);
	}
	
	
	// Configura as filas e exchanges no RabbitMQ
	// PostConstruct: executa assim que subir aplicação
	@PostConstruct
	private void configurar() {
		Queue filaEstoque 	= getFila(RabbitMQConstantes.FILA_ESTOQUE);
		Queue filaPreco		= getFila(RabbitMQConstantes.FILA_PRECO);
		
		DirectExchange exchange = getDirectExchange();
				
		Binding ligacaoFilaExchangeEstoque 	 = getRelacaoFilaExchange(filaEstoque, exchange);
		Binding ligacaoFilaExchangePreco	 = getRelacaoFilaExchange(filaPreco, exchange);

		// Criação dos objetos no RabbitMQ
		
		// Criação das filas
		amqAdmin.declareQueue(filaEstoque);
		amqAdmin.declareQueue(filaPreco);
		
		// Criação da exchange
		// Já existe a exchange, nada será criado
		amqAdmin.declareExchange(exchange);

		// Criação do relacionamento fila/exchange
		amqAdmin.declareBinding(ligacaoFilaExchangeEstoque);
		amqAdmin.declareBinding(ligacaoFilaExchangePreco);		
	}
	
	
}
