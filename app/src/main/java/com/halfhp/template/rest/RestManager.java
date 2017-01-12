package com.halfhp.template.rest;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * Manager for rest communication
 */
@EBean(scope = EBean.Scope.Singleton)
public class RestManager {

    private static final String TAG = RestManager.class.getName();

    @RestService
    RestClient restClient;

    @AfterInject
    protected void afterInject() {
        restClient.getRestTemplate().setRequestFactory(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // TODO: global HTTP header setup goes here

        Log.d(TAG,"RestManager initialized.");
    }

    public RestClient getClient() {
        return restClient;
    }

    public void setHeader(String name, String value) {
        getClient().setHeader(name, value);
    }
}
