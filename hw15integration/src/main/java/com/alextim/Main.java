package com.alextim;


import com.alextim.domain.Check;
import com.alextim.domain.Food;
import com.alextim.domain.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.support.GenericMessage;

@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@ComponentScan
@EnableIntegration
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);

        DirectChannel itemsChannel = ctx.getBean("itemsChannel", DirectChannel.class);

        PublishSubscribeChannel foodChannel = ctx.getBean("foodChannel", PublishSubscribeChannel.class);
        foodChannel.subscribe(handler-> System.out.println("Ready orderItem: " + ((Food)handler.getPayload()).getTitle()));

        DirectChannel checkChannel = ctx.getBean("checkChannel", DirectChannel.class);
        checkChannel.subscribe(handler-> System.out.println("Check: " + ((Check)handler.getPayload()).getCash()));


        itemsChannel.send(new GenericMessage<>("something satisfying"));

        ctx.close();
    }
}