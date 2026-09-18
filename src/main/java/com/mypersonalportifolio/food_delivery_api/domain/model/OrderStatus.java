package com.mypersonalportifolio.food_delivery_api.domain.model;


import lombok.Getter;

@Getter
public enum OrderStatus {

    CRIADO,
    CONFIRMADO,
    ENTREGUE,
    CANCELADO  ;
}
