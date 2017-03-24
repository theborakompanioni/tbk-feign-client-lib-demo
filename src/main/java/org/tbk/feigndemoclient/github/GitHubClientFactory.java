package org.tbk.feigndemoclient.github;

import org.tbk.feigndemoclient.ClientConfig;
import org.tbk.feigndemoclient.ClientFactory;

public class GitHubClientFactory {

    public static GitHubClient create() {
        return ClientFactory.create(GitHubClient.class, config());
    }

    public static GitHubClient create(ClientConfig clientConfig) {
        return ClientFactory.create(GitHubClient.class, clientConfig);
    }

    public static ClientConfig config() {
        return ClientConfig.DefaultFactory.buildWithBaseUrl("https://api.github.com");
    }
}
