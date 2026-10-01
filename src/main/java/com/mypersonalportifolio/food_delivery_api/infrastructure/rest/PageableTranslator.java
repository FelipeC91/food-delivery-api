package com.mypersonalportifolio.food_delivery_api.infrastructure.rest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PageableTranslator {

    public  static Pageable translate(Pageable pageableSource, Map<String, String> translationReference) {
            var orderList = pageableSource.getSort().stream()
                                .filter(order -> translationReference.containsKey(order.getProperty()))
                                .map( order -> new Sort.Order(order.getDirection(),
                                        translationReference.get( order.getProperty()) ) )
                                .toList();
        System.out.println(orderList);

        return PageRequest.of(pageableSource.getPageNumber(),  pageableSource.getPageSize(), Sort.by(orderList));
    }
}
