package com.microservices.consumidorestoque.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constantes.RabbitMQConstantes;
import dto.EstoqueDTO;

@Component
public class EstoqueConsumer {

	// Marca método para escutar a fila do RabbitMQ
	@RabbitListener(queues=RabbitMQConstantes.FILA_ESTOQUE)
	public void consumir(EstoqueDTO estoque) {
		System.out.println(estoque.codigoProduto);
		System.out.println(estoque.quantidade);
		System.out.println("-------------------------------");
	}
	
}
