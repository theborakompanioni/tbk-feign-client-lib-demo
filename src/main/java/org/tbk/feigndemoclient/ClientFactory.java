package org.tbk.feigndemoclient;

import com.google.common.collect.ImmutableList;
import feign.RequestInterceptor;
import feign.hystrix.HystrixFeign;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class ClientFactory {

    public static <T> T create(Class<T> clazz, ClientConfig clientConfig) {
        requireNonNull(clientConfig, "`clientConfig` must not be null");

        final ImmutableList<RequestInterceptor> requestInterceptors =
                ImmutableList.<RequestInterceptor>builder()
                        .addAll(clientConfig.requestInterceptors())
                        .addAll(Stream.of(clientConfig.basicAuthRequestInterceptor())
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(toList()))
                        .build();

        return HystrixFeign.builder()
                .client(clientConfig.client())
                .contract(clientConfig.contract())
                .decoder(clientConfig.decoder())
                .encoder(clientConfig.encoder())
                .errorDecoder(clientConfig.errorDecoder())
                .logger(clientConfig.logger())
                .logLevel(clientConfig.logLevel())
                .options(clientConfig.options())
                .requestInterceptors(requestInterceptors)
                .retryer(clientConfig.retryer())
                .setterFactory(clientConfig.setterFactory())
                .target(clazz, clientConfig.baseUrl());
    }
}
