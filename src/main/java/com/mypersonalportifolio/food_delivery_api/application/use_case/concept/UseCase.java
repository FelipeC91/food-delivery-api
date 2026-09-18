package com.mypersonalportifolio.food_delivery_api.application.use_case.concept;


public interface UseCase<Input, Output> {

    Output execute(Input input);
}
