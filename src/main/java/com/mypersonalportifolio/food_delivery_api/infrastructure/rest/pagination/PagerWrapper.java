package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.pagination;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagerWrapper<T> extends PageImpl<T> {

    private final Pageable pageable;

    public PagerWrapper(List<T> content, Pageable pageable) {
        super(content, pageable, content.size());
        this.pageable = pageable;
    }

    @Override
    public Pageable getPageable() {
        return this.getPageable();
    }
}
