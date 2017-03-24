package org.tbk.feigndemoclient.github;

import com.netflix.hystrix.HystrixCommand;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.tbk.feigndemoclient.github.model.Contributor;

import java.util.List;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.USER_AGENT;

@Headers({
        ACCEPT + ": " + "application/json;charset=UTF-8",
        USER_AGENT + ": Mozilla/5.0"
})
public interface GitHubClient {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    HystrixCommand<List<Contributor>> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
