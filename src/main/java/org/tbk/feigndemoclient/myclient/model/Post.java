package org.tbk.feigndemoclient.myclient.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Post.Builder.class)
public class Post {
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

    }

    private long userId;
    private long id;
    private String title;
    private String body;
}
