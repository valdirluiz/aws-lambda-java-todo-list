package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.Map;
import java.util.List;

import com.serverless.dal.Todo;

public class ListTodosHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
         List<Todo> todos = new Todo().list();

        // send the response back
        return ApiGatewayResponse.builder()
    				.setStatusCode(200)
    				.setObjectBody(todos)
    				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
    				.build();
    } catch (Exception ex) {
 
        // send the error response back
  			Response responseBody = new Response("Falha ao realizar busca: ", input);
  			return ApiGatewayResponse.builder()
  					.setStatusCode(500)
  					.setObjectBody(responseBody)
  					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
  					.build();
    }
	}
}
