package com.melowetty.hsepermhelper.usersservice.mongo

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory

@Configuration
class MongoConfiguration: AbstractReactiveMongoConfiguration() {
    @Value("\${spring.data.mongodb.database}")
    lateinit var mongoDatabaseName: String

    @Value("\${spring.data.mongodb.username}")
    lateinit var mongoDatabaseUser: String

    @Value("\${spring.data.mongodb.password}")
    lateinit var mongoDatabasePassword: String

    @Value("\${spring.data.mongodb.host}")
    lateinit var mongoDatabaseHost: String

    @Value("\${spring.data.mongodb.port}")
    lateinit var mongoDatabasePort: String

    override fun getDatabaseName(): String {
        return mongoDatabaseName
    }

    @Bean
    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create("mongodb://${mongoDatabaseUser}:${mongoDatabasePassword}@${mongoDatabaseHost}:${mongoDatabasePort}")
    }

    @Bean
    override fun reactiveMongoDbFactory(): ReactiveMongoDatabaseFactory {
        return SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), databaseName)
    }
}