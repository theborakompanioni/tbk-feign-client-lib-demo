package org.tbk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tbk.feigndemoclient.github.GitHubClient;
import org.tbk.feigndemoclient.github.GitHubClientFactory;
import org.tbk.feigndemoclient.myclient.MyClient;
import org.tbk.feigndemoclient.myclient.MyClientFactory;

@Configuration
public class FeignClientDemoTestConfig {

    @Bean
    public MyClient myClient() {
        return MyClientFactory.create();
    }

    @Bean
    public GitHubClient gitHubClient() {
        return GitHubClientFactory.create();
    }

}
