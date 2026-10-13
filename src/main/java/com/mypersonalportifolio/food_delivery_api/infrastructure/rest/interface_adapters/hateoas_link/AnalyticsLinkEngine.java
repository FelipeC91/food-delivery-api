package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.AnalyticsController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class AnalyticsLinkEngine {
    public static Link slashAnalytics() {
        var link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AnalyticsController.class).getDailySales(null)).withRel("/daily-sales");

        var uriTemplate = UriTemplate.of(link.getHref()).with(new TemplateVariables(
                new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("offsetTimeZone", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("createdSince", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("createdUntil", TemplateVariable.VariableType.REQUEST_PARAM))
        );

        return Link.of(uriTemplate, link.getRel());
    }
}
