package com.alextim.config;

import com.alextim.domain.Order;
import com.alextim.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
public class IntegrationConfig {

    @Bean
    public IntegrationFlow cafeFlow(OrderService orderService) {
        return IntegrationFlows
                .from("itemsChannel")
                .<String, Order>transform(orderService::specify)
                .<Order, Boolean>route(
                        Order::isIced,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf
                                        .handle("kitchenService", "cookHot")
                                )
                                .subFlowMapping(false, sf -> sf
                                        .handle("kitchenService", "cookCold")
                                )
                )
                .channel("foodChannel")
                .handle("cashboxService", "getPleaseCheck")
                .channel("checkChannel")
                .get();
    }


    @Bean
    public DirectChannel itemsChannel(){
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel foodChannel(){
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public DirectChannel checkChannel(){
        return MessageChannels.direct().get();
    }
}
