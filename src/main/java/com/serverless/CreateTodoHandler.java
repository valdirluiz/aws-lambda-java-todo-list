package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.Map;

import com.serverless.dal.Todo;

public class CreateTodoHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try {
			JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

			Todo todo = new Todo();
			todo.setName(body.get("name").asText());
			todo.setStatus(body.get("status").asText());
			todo.save();

			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(todo)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();

		} catch (Exception ex) {
			logger.error("Error in saving todo: " + ex);

			Response responseBody = new Response("Falha ao salvar: ", input);
			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(responseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
		}
	}
}
