package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.exception_handler.ApiProblemDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper =  new ObjectMapper();


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {
        var status = HttpStatus.FORBIDDEN;
        var problemDetails = ApiProblemDetails.builder()
                .title("Acesso restrito")
                .detail("Usuário não possui permissão para essa operação")
                .httpStatusCode(status.value())
                .timestamp(OffsetDateTime.now())
                .build();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getOutputStream(), problemDetails);
    }
}
