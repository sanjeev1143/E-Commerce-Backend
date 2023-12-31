package com.shoppingapplication.orderservice.service;

import com.shoppingapplication.orderservice.dto.InventoryResponse;
import com.shoppingapplication.orderservice.dto.OrderLineDto;
import com.shoppingapplication.orderservice.dto.OrderRequest;
import com.shoppingapplication.orderservice.model.Order;
import com.shoppingapplication.orderservice.model.OrderLineItems;
import com.shoppingapplication.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems= orderRequest.getOrderLineDtoList().stream().map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();


        //Call Inventory Service, and place an order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get().uri("http://localhost:9002/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class).block();

        assert inventoryResponses != null;
        boolean allProductsInStock =  Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        }else{
            throw  new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineDto orderLineDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineDto.getQuantity());
        orderLineItems.setPrice(orderLineDto.getPrice());
        orderLineItems.setSkuCode(orderLineDto.getSkuCode());

        return orderLineItems;
    }
}
