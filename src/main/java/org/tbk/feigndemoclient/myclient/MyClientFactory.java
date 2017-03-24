package org.tbk.feigndemoclient.myclient;

import org.tbk.feigndemoclient.ClientConfig;
import org.tbk.feigndemoclient.ClientFactory;

public class MyClientFactory {

    public static MyClient create() {
        return ClientFactory.create(MyClient.class, config());
    }

    public static MyClient create(ClientConfig clientConfig) {
        return ClientFactory.create(MyClient.class, clientConfig);
    }

    public static ClientConfig config() {
        return ClientConfig.DefaultFactory.buildWithBaseUrl("https://jsonplaceholder.typicode.com");
    }
}
