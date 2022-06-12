package com.microservices.estoque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constantes.RabbitMQConstantes;
import com.microservices.estoque.service.RabbitMQService;

import dto.PrecoDTO;

@RestController
@RequestMapping(value="preco")
public class PrecoController {

	@Autowired
	private RabbitMQService rabbitMQService;
	
	@PutMapping
	private ResponseEntity alterarPreco(@RequestBody PrecoDTO precoDTO) {
		System.out.println(precoDTO);	
		rabbitMQService.enviarMensagem(RabbitMQConstantes.FILA_PRECO, precoDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
