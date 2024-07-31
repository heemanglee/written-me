package com.match.team.migration_kotlin.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    @Value("\${aws.credentials.access-key}")
    private val accessKey: String,

    @Value("\${aws.credentials.secret-key}")
    private val secretKey: String,

    @Value("\${aws.region.static}")
    private val region: String
) {

    @Bean
    fun amazonSQSAsync(): AmazonS3? {
        return AmazonS3ClientBuilder.standard().withRegion(region)
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        accessKey,
                        secretKey
                    )
                )
            )
            .build()
    }
}