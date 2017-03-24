package org.tbk.feigndemoclient.myclient;

import com.netflix.hystrix.HystrixCommand;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.tbk.feigndemoclient.myclient.model.Post;

import java.util.List;

import static com.google.common.net.HttpHeaders.*;

@Headers({
        ACCEPT + ": " + "application/json;charset=UTF-8",
        USER_AGENT + ": Mozilla/5.0"
})
public interface MyClient {

    @RequestLine("GET /posts")
    HystrixCommand<List<Post>> posts();

    @RequestLine("GET /posts/{postId}")
    HystrixCommand<Post> postById(@Param("postId") long post);

    @Headers({
            CONTENT_TYPE + ": " + "application/json;charset=UTF-8"
    })
    @RequestLine("PUT /posts/{postId}")
    HystrixCommand<Post> putPost(@Param("postId") long post);
}
