package org.tbk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tbk.feigndemoclient.github.GitHubClient;
import org.tbk.feigndemoclient.github.model.Contributor;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubClientIT {

    @Autowired
    private GitHubClient gitHubClient;

    @Test
    public void itShouldLoadContributors() {
        List<Contributor> contributors = gitHubClient.contributors("theborakompanioni", "tbk-feign-client-lib-demo")
                .execute();

        assertThat(contributors, is(notNullValue()));
        assertThat(contributors, hasSize(greaterThan(0)));


        final Contributor anyContributor = contributors.stream().findAny()
                .orElseThrow(IllegalStateException::new);

        assertThat(anyContributor, is(notNullValue()));
        assertThat(anyContributor.getId(), is(greaterThan(0L)));
    }
}
