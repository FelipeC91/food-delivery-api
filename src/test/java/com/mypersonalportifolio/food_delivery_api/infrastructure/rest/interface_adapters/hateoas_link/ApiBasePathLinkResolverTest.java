package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiBasePathLinkResolverTest {

    @Test
    void addsApiBasePathToUnversionedLink() {
        var link = ApiBasePathLinkResolver.withApiBasePath(Link.of("http://127.0.0.1:8080/cities", "cities"));

        assertEquals("http://127.0.0.1:8080/api/v1/cities", link.getHref());
        assertEquals("cities", link.getRel().value());
    }

    @Test
    void doesNotDuplicateApiBasePath() {
        var link = ApiBasePathLinkResolver.withApiBasePath(
                Link.of("http://127.0.0.1:8080/api/v1/cities", "cities"));

        assertEquals("http://127.0.0.1:8080/api/v1/cities", link.getHref());
    }
}
