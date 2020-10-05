package com.example.sqs;

import com.amazonaws.services.sqs.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

@RestController
public class SqsController {

    // Name of the queue. Developers are free to choose their queue name.
    private static final String QUEUE = "spring-boot-amazon-sqs";

    // QueueMessagingTemplate initializes the messaging template by configuring the destination resolver as well as the message converter.
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;


    // @SqsListener listens to the message from the specified queue.
    // Here in this example we are printing the message on the console and the message will be deleted from the queue once it is successfully delivered.
    @SqsListener(value = QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String message,
                               @Header("SenderId") String senderId) {
        System.out.println("MESSAGE : "+message);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "message", required = false) String message) {
        System.out.println("INPUT MESSAGE : "+message);
        if(message == null){
            message = "sample message";
        }
        queueMessagingTemplate.convertAndSend(QUEUE, message);
        return "message posted :"+message;
    }
}
