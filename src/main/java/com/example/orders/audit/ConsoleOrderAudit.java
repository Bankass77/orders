package com.example.orders.audit;

//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class ConsoleOrderAudit implements OrderAudit {

    @Override
    public void record(String message) {
      System.out.println( "[ ORDER AUDIT ] " + message);
    }
}
