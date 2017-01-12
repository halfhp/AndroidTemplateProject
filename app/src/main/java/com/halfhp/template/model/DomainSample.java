package com.halfhp.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.halfhp.template.rest.ReadUnwrapped;
import com.halfhp.template.rest.WriteUnwrapped;

/**
 * Created by halfhp on 1/11/17.
 */
public class DomainSample extends DomainModel implements ReadUnwrapped, WriteUnwrapped {

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("url")
    private String url;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
