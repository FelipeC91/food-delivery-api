package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter;

import com.mypersonalportifolio.food_delivery_api.domain.model.Order;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller.order.OrderController;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.dto.OrderRepresentationModel;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.hateoas_link.*;
import com.mypersonalportifolio.food_delivery_api.infrastructure.rest.interface_adapters.presenter.mapstruct.OrderMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderRepresentationModel> {

    private final OrderMapper orderMapper;

    public OrderRepresentationModelAssembler(OrderMapper orderMapper) {
        super(OrderController.class, OrderRepresentationModel.class);
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderRepresentationModel toModel(Order entity) {
        var representationModel = orderMapper.toRepresentationModel(entity);

        representationModel.add(OrdersLinkEngine.slashOrders())
                .add(OrdersLinkEngine.slashOrdersSlashOrderId(entity.getId()))
                .add(OrdersLinkEngine.slashStatus(entity.getId()));

        representationModel.getRestaurant().add(RestaurantLinkEngine.slashRestaurants())
                .add(RestaurantLinkEngine.slashRestaurantsSlashRestaurantId(entity.getRestaurant().getId()));

        representationModel.getPaymentMethod().add(PaymentMethodsLinkEngine.slashPaymentMethods())
                        .add(PaymentMethodsLinkEngine.slashPaymentMethodsSlashPaymentMethodId(entity.getPaymentMethod().getId()));

        representationModel.getCustomer().add(UsersLinkEngine.slashUsers())
                .add(UsersLinkEngine.slashUsersUserId(entity.getCustomer().getId()));

        representationModel.getShippingAddress().add(CitiesLinkEngine.slashCities())
                .add(CitiesLinkEngine.slashCitiesSlashCityId(entity.getShippingAddress().getCity().getId()));

        representationModel.getItems().forEach(item -> {
            item.add( RestaurantLinkEngine.slashProductsSlashProductId(entity.getRestaurant().getId(), item.getProductId()) );
        });

        return representationModel;
    }


}
