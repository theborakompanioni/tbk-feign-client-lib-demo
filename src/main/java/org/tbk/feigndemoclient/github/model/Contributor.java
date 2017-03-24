package org.tbk.feigndemoclient.github.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Contributor.Builder.class)
public class Contributor {
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

    }

    private long id;
    private String login;
    private long contributions;
}
