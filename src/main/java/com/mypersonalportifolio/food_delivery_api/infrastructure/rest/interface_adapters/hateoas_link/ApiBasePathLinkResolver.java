package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.security.resource_server.WebConfig;
import org.springframework.hateoas.Link;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

public class ApiBasePathLinkResolver {
    public static Link withApiBasePath(Link link) {
        var linkUri = UriComponentsBuilder.fromUriString(link.getHref());
        var apiBasePath = WebConfig.API_BASE_PATH.replace("{version}", WebConfig.DEFAULT_API_VERSION);
        var path = Objects.requireNonNull(linkUri.build().getPath(), "Link href must contain a path");
        if (path.equals(apiBasePath) || path.startsWith(apiBasePath + "/")) {
            return link;
        }

        return Link.of(linkUri.replacePath(apiBasePath + path).build().toUriString(), link.getRel());
    }
}
