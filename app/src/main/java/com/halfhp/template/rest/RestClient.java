package com.halfhp.template.rest;

import com.halfhp.template.model.DomainModel;
import com.halfhp.template.model.DomainSample;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Rest(converters = {MappingJackson2HttpMessageConverter.class}, interceptors = {RestLogger.class})
public interface RestClient extends RestClientHeaders {

    RestTemplate getRestTemplate();
    void setRestTemplate(RestTemplate restTemplate);

    @Get("https://httpbin.org/get")
    ResponseEntity<DomainSample> sampleGet();
}
