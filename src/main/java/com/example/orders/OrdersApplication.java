package com.example.orders;

//import com.example.orders.audit.OrderAuditLogger;

import com.example.orders.service.OrderService;
//import com.example.orders.service.OrderTransactionDemo;
//import com.example.orders.service.OrderTransactionWorker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
@ConfigurationPropertiesScan
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);

        long value0 = System.nanoTime();
        System.out.println(" value0 :" + value0);

        // var ex = Executors.newFixedThreadPool(200)  // 50 s
        try (var ex = Executors.newVirtualThreadPerTaskExecutor()) { // environ 1 seconde comme durée.
            IntStream.range(0, 10_000).forEach(i -> ex.submit(
                    () -> {
                        Thread.sleep(Duration.ofSeconds(1));
                        return i;
                    }
            ));

        }
        long value1 = System.nanoTime();
        System.out.println(" value1 : " + value1);
        System.out.println(" delta: " + (value1 - value0));
    }

    @Bean
    CommandLineRunner testOrderService(OrderService orderService) {
		/*OrderTransactionWorker orderTransactionWorker = new OrderTransactionWorker();
		OrderTransactionDemo orderTransactionDemo = new OrderTransactionDemo(orderTransactionWorker);
		OrderAuditLogger orderAuditLogger = new OrderAuditLogger();
		orderAuditLogger.log("testOrderService");
		orderTransactionDemo.outer();
		  orderService.process();*/
        return args ->
                orderService.displayOrderApiConfiguration();

    }

}
