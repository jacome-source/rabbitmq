package com.microservices.consumidorestoque.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constantes.RabbitMQConstantes;
import dto.PrecoDTO;

@Component
public class PrecoConsumer {

	// Marca método para escutar a fila do RabbitMQ
	@RabbitListener(queues=RabbitMQConstantes.FILA_PRECO)
	public void consumir(PrecoDTO preco) {
		System.out.println(preco.codigoProduto);
		System.out.println(preco.preco);
		System.out.println("-------------------------------");
	}
	
}
