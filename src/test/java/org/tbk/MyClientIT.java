package org.tbk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tbk.feigndemoclient.myclient.MyClient;
import org.tbk.feigndemoclient.myclient.model.Post;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyClientIT {

    @Autowired
    private MyClient myClient;

    @Test
    public void itShouldLoadAllPosts() {
        final List<Post> posts = myClient.posts().execute();

        assertThat(posts, is(notNullValue()));
        assertThat(posts, hasSize(greaterThan(0)));
    }
}
