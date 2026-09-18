package com.mypersonalportifolio.food_delivery_api.infrastructure.web.exception;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityAlreadyExistsException;
import com.mypersonalportifolio.food_delivery_api.domain.exception.EntityNotFoundException;

import com.mypersonalportifolio.food_delivery_api.domain.exception.FailOnValidateEntityPropertiesException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.jspecify.annotations.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource errorMessageSource;

    @ExceptionHandler(BusinessConstraintsViolationException.class)
    public ResponseEntity<?> handleBusinessConstraintsViolationException(BusinessConstraintsViolationException exception, WebRequest request) {
        var defaultHttpStatusCode = HttpStatus.BAD_REQUEST;

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultHttpStatusCode.getReasonPhrase())
                .detail(exception.getMessage())
                .httpStatusCode(defaultHttpStatusCode.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return super.handleExceptionInternal(exception, problemDetails, HttpHeaders.EMPTY, defaultHttpStatusCode, request);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        var defaultMessage = "Recurso não encontrado";
        var defaultHttpStatusCode = HttpStatus.NOT_FOUND;

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail(exception.getMessage())
                .httpStatusCode(defaultHttpStatusCode.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return this.handleExceptionInternal(exception, problemDetails, HttpHeaders.EMPTY, defaultHttpStatusCode, request);

    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleCandidateEntityInvalidException(EntityAlreadyExistsException exception, WebRequest request) {
        var defaultMessage = "Recurso submetido possui propriedades inconsistentes";
        var defaultHttpStatusCode = HttpStatus.BAD_REQUEST;

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail(exception.getMessage())
                .httpStatusCode(defaultHttpStatusCode.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return this.handleExceptionInternal(exception, problemDetails, HttpHeaders.EMPTY, defaultHttpStatusCode, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode httpStatusCode, WebRequest request) {
        var rootCause = ExceptionUtils.getRootCause(ex);

        System.out.println("----------------------------------");
        System.out.println(rootCause.getCause());
        System.out.println("----------------------------------");

        if (rootCause instanceof InvalidFormatException)
            return handleInvalidFormatException((InvalidFormatException) rootCause, HttpHeaders.EMPTY, httpStatusCode, request);

        else if (rootCause instanceof PropertyBindingException)
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, httpStatusCode, request);

        var defaultMessage = "Recurso submetido possui propriedades inconsistentes";

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail("revisar propriedades e sintaxe")
                .httpStatusCode(httpStatusCode.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return this.handleExceptionInternal(ex, problemDetails, HttpHeaders.EMPTY, httpStatusCode, request);
    }

        private @Nullable ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode httpStatusCode, WebRequest request) {
        var defaultMessage = "Recurso submetido possui propriedades inconsistentes";


            String detail = String.format("A propriedade '%s' não existe. "
                    + "Corrija ou remova essa propriedade e tente novamente.", ex.getPropertyName());

        var problemDetails = ApiProblemDetails.builder()
                                                                .title(defaultMessage)
                                                                .httpStatusCode(httpStatusCode.value())
                                                                .detail(detail)
                                                                .build();

        return this.handleExceptionInternal(ex, problemDetails, headers, httpStatusCode, request);

    }



    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        var defaultMessage = "Recurso submetido possui propriedades inconsistentes";

        var propertyPath = concatPath(ex.getPath());

        System.out.println("----------------------------------");
        System.out.println(ex.getPath());
        System.out.println("----------------------------------");

        var invalidProperty = concatPath(ex.getPath());
        var details = String.format("A propriedade %s recebeu valor %s, que é de tipo inválido. Informe um valor compatível com %s",
                                                propertyPath, ex.getValue(), ex.getTargetType().getSimpleName());

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail(details)
                .httpStatusCode(statusCode.value())
                .timestamp(OffsetDateTime.now())
                .build();

        return handleExceptionInternal(ex, problemDetails, headers, statusCode, request);
    }

    private String concatPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @ExceptionHandler(FailOnValidateEntityPropertiesException.class)
    public ResponseEntity<Object> handleFailOnValidateEntityPropertiesException(FailOnValidateEntityPropertiesException ex, WebRequest request) {
        return this.handleValidationErrorInternal(ex, ex.getBindingResult(), HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return this.handleValidationErrorInternal(ex, ex.getBindingResult(), status, request);
    }

    private ResponseEntity<Object> handleValidationErrorInternal(Exception ex, BindingResult bindingResult,  HttpStatusCode status, WebRequest request) {
        var defaultMessage = "Recurso submetido possui propriedades inconsistentes";

        var detail = "revise as propriedades e tente novamente";

        var invalidResourceFields = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    var errorMessage = errorMessageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    var formattedErrorMessage = String.format(errorMessage, fieldError.getField());
                    return new ApiProblemDetails.InvalidResourceField(fieldError.getField(), formattedErrorMessage);
                })
                .collect(Collectors.toList());

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail(detail)
                .timestamp(OffsetDateTime.now())
                .httpStatusCode(status.value())
                .invalidResourceFields(invalidResourceFields)
                .build();

        return handleExceptionInternal(ex, problemDetails, HttpHeaders.EMPTY, status,request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        var defaultMessage = "Ocorreu um erro interno inesperado";
        var detail = "Erro internoente novamente e se o problema persistir, entre em contato com o administrador do sistema.";
        var defaultHttpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        var problemDetails = ApiProblemDetails.builder()
                .title(defaultMessage)
                .detail(detail)
                .timestamp(OffsetDateTime.now())
                .httpStatusCode(defaultHttpStatusCode.value())
                .build();

        ex.printStackTrace();

        System.out.println("---------------------------------------------------");
        System.out.println(ExceptionUtils.getRootCause(ex));
        System.out.println("---------------------------------------------------");

        return handleExceptionInternal(ex, problemDetails, HttpHeaders.EMPTY, defaultHttpStatusCode,request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                                       HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        var IntegerHttpStatusCode = statusCode.value();

        if ( Objects.isNull(body) ) {
            body = ApiProblemDetails.builder()
                    .title(HttpStatus.resolve(IntegerHttpStatusCode).getReasonPhrase())
                    .httpStatusCode(IntegerHttpStatusCode)
                    .timestamp(OffsetDateTime.now())
                    .build();

        } else if (body instanceof String ) {
            body = ApiProblemDetails.builder()
                    .title( (String) body)
                    .httpStatusCode(statusCode.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, HttpHeaders.EMPTY, statusCode, request);
    }
}
