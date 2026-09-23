package com.mypersonalportifolio.food_delivery_api.infrastructure.reports;

public class FailOnBuildReportException extends RuntimeException {
    private static final String  message = "Ocorreu uma Falha inesperada ao gerar relatório";

    public FailOnBuildReportException(Throwable cause) {
        super(message, cause);
    }


}
