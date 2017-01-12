package com.halfhp.template.rest;
import com.halfhp.template.model.DomainSample;
import com.halfhp.template.test.IntegrationTest;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class RestClientTest extends IntegrationTest {

    RestClient client;

    @Before
    public void beforeEach() {
        client = RestManager_.getInstance_(getContext()).getClient();
    }

    @Test
    public void domainSample_returnsResult() throws Exception {
        DomainSample response = client.sampleGet().getBody();
        assertNotNull(response.getOrigin());
        assertNotNull(response.getUrl());
    }
}
