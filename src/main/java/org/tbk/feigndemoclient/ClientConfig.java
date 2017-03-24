package org.tbk.feigndemoclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.SetterFactory;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

public interface ClientConfig {
    interface BasicAuth {
        String password();

        String username();
    }

    String baseUrl();

    default Optional<BasicAuth> basicAuth() {
        return Optional.empty();
    }

    default Optional<BasicAuthRequestInterceptor> basicAuthRequestInterceptor() {
        return basicAuth().map(basicAuth -> new BasicAuthRequestInterceptor(
                basicAuth.username(),
                basicAuth.password(),
                Charsets.UTF_8
        ));
    }

    SetterFactory setterFactory();

    Logger logger();

    Retryer retryer();

    Contract contract();

    ErrorDecoder errorDecoder();

    Client client();

    Logger.Level logLevel();

    Decoder decoder();

    Encoder encoder();

    Request.Options options();

    default Collection<RequestInterceptor> requestInterceptors() {
        return Collections.emptyList();
    }

    abstract class AbstractClientConfig implements org.tbk.feigndemoclient.ClientConfig {

        private ObjectMapper objectMapper() {
            return new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(SerializationFeature.INDENT_OUTPUT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        public Optional<BasicAuth> basicAuth() {
            return Optional.empty();
        }

        public Optional<BasicAuthRequestInterceptor> basicAuthRequestInterceptor() {
            return basicAuth().map(basicAuth -> new BasicAuthRequestInterceptor(
                    basicAuth.username(),
                    basicAuth.password(),
                    Charsets.UTF_8
            ));
        }

        public SetterFactory setterFactory() {
            return new DefaultSetterFactory();
        }

        public Logger logger() {
            return new Slf4jLogger();
        }

        public Retryer retryer() {
            return new Retryer.Default();
        }

        public Contract contract() {
            return new Contract.Default();
        }

        public ErrorDecoder errorDecoder() {
            return new ErrorDecoder.Default();
        }

        public Client client() {
            return new OkHttpClient();
        }

        public Logger.Level logLevel() {
            return Logger.Level.FULL;
        }

        public Decoder decoder() {
            return new JacksonDecoder(objectMapper());
        }

        public Encoder encoder() {
            return new JacksonEncoder(objectMapper());
        }

        public Request.Options options() {
            return new Request.Options();
        }

        public Collection<RequestInterceptor> requestInterceptors() {
            return Collections.emptyList();
        }

        private static final class DefaultSetterFactory implements SetterFactory {
            private static int DEFAULT_TIMEOUT_IN_MS = (int) SECONDS.toMillis(10_000);

            @Override
            public HystrixCommand.Setter create(Target<?> target, Method method) {
                String groupKey = target.name();
                String commandKey = Feign.configKey(target.type(), method);

                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withExecutionTimeoutInMilliseconds(DEFAULT_TIMEOUT_IN_MS)
                                        .withExecutionIsolationStrategy(SEMAPHORE)
                                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(1)
                        );
            }
        }
    }

    class DefaultFactory {
        public static ClientConfig buildWithBaseUrl(String baseUrl) {
            return new HardCodedTarget(baseUrl);
        }

        private static class HardCodedTarget extends AbstractClientConfig {

            private final String baseUrl;

            private HardCodedTarget(String baseUrl) {
                this.baseUrl = requireNonNull(baseUrl, "`baseUrl` must not be null");
            }

            @Override
            public String baseUrl() {
                return this.baseUrl;
            }
        }
    }
}
