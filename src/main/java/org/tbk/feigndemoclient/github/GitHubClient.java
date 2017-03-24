package org.tbk.feigndemoclient.github;

import com.netflix.hystrix.HystrixCommand;
import feign.Param;
import feign.RequestLine;
import org.tbk.feigndemoclient.github.model.Contributor;

import java.util.List;

public interface GitHubClient {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    HystrixCommand<List<Contributor>> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
