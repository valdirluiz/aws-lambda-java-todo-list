package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import java.util.Collections;
import java.util.Map;

import com.serverless.dal.Todo;

public class GetTodoHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

    try {
         Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
        String todoId = pathParameters.get("id");

         Todo todo = new Todo().get(todoId);

         if (todo != null) {
          return ApiGatewayResponse.builder()
      				.setStatusCode(200)
      				.setObjectBody(todo)
      				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
      				.build();
        } else {
          return ApiGatewayResponse.builder()
      				.setStatusCode(404)
              .setObjectBody("id: '" + todoId + "' não econtrado.")
      				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
      				.build();
        }
    } catch (Exception ex) {
 
        // send the error response back
  			Response responseBody = new Response("Falha ao encontrar: ", input);
  			return ApiGatewayResponse.builder()
  					.setStatusCode(500)
  					.setObjectBody(responseBody)
  					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
  					.build();
    }
	}
}
