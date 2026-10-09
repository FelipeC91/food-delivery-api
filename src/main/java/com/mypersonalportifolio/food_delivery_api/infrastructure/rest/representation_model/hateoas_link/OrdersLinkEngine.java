package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.representation_model.hateoas_link;

import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderStatusController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class OrdersLinkEngine {

    static final TemplateVariables STD_SORT_TEMPLATE_VARIABLES =  new TemplateVariables( new TemplateVariable("size", VariableType.REQUEST_PARAM),
                                                                                            new TemplateVariable("sort", VariableType.REQUEST_PARAM),
                                                                                            new TemplateVariable("page", VariableType.REQUEST_PARAM)

                                                                        );

   public static Link slashOrders() {
        var link = WebMvcLinkBuilder.linkTo(OrderController.class).withRel("/orders");
        var uriTemplate = UriTemplate.of(link.getHref()).with(new TemplateVariables(
                new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
                new TemplateVariable("customerId", VariableType.REQUEST_PARAM),
                new TemplateVariable("createdSince", VariableType.REQUEST_PARAM),
                new TemplateVariable("createdUntil", VariableType.REQUEST_PARAM))
        ).with(STD_SORT_TEMPLATE_VARIABLES);

        return Link.of(uriTemplate, link.getRel());
    }

    public static Link slashOrdersSlashOrderId(UUID orderId) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).getResource(orderId)).withSelfRel();
    }

    public static Link slashStatus(UUID orderId) {
       return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderStatusController.class).updateResourceProperty(orderId,null)).withRel("/status");
    }
}
