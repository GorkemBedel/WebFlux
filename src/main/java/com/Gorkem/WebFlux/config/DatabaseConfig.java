package com.Gorkem.WebFlux.config;

import com.Gorkem.WebFlux.model.converter.CourseMetadatatoJsonConverter;
import com.Gorkem.WebFlux.model.converter.JsonToCourseMetaDataConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.annotation.Nonnull;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.util.annotation.NonNullApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
public class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${webflux.database.host}")
    private String host;

    @Value("${webflux.database.port}")
    private int port;

    @Value("${webflux.database.name}")
    private String database;

    @Value("${webflux.database.schema}")
    private String schema;

    @Value("${webflux.database.username}")
    private String username;

    @Value("${webflux.database.password}")
    private String password;

    @Value("${webflux.database.pool.size.initial}")
    private int initialPoolSize;

    @Value("${webflux.database.pool.size.max}")
    private int maxPoolSize;




    @Override
    @Bean
    public ConnectionFactory connectionFactory() {

        final PostgresqlConnectionFactory factory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration
                        .builder()
                        .host(host)
                        .port(port)
                        .schema(schema)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build()
        );

        final ConnectionPoolConfiguration  poolConfiguration = ConnectionPoolConfiguration
                .builder(factory)
                .initialSize(initialPoolSize)
                .maxSize(maxPoolSize)
                .build();


        return new ConnectionPool(poolConfiguration);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory){

        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new ObjectMapper();
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {

        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new CourseMetadatatoJsonConverter(objectMapper()));
        converters.add(new JsonToCourseMetaDataConverter(objectMapper()));
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }


}

