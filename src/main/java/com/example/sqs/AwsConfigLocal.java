package com.example.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class AwsConfigLocal {

    @Value("${cloud.aws.region.static}")
    String awsRegion;

    @Value("${cloud.aws.credentials.access-key}")
    String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    String awsSecretKey;

    @Value("${localstack.sqs.url}")
    String localStackSqsUrl;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSqs() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackSqsUrl, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }

}
