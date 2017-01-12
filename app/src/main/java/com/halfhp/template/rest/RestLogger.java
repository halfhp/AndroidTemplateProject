package com.halfhp.template.rest;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Logs Rest requests / responses
 */
public class RestLogger implements ClientHttpRequestInterceptor {

    private static final String TAG = RestLogger.class.getName();
    private static final String REQUEST_LOG_FORMAT = "===> %s (%s) [%d] \nHEADERS: %s\nBODY:%s";
    private static final String RESPONSE_LOG_FORMAT = "<===  %s (%s) [%d]\nRESPONSE CODE: %d\nHEADERS: %s\nBODY:%s";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data,
            ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, data);
        ClientHttpResponse response = execution.execute(request, data);
        logResponse(request, response);
        return response;
    }

    /**
     * TODO: remove all invocations of this method from production builds with proguard.
     * @param request
     * @param data
     */
    protected void logRequest(HttpRequest request, byte[] data) {
        try {

            String body = " <EMPTY>";

            if (data != null && data.length > 0) {
                body = "\n" + new JSONObject(new String(data)).toString(2);
            }

            Log.d(TAG, String.format(REQUEST_LOG_FORMAT,
                    request.getURI().toString(),
                    request.getMethod().name(),
                    request.hashCode(),
                    getHeadersAsString(request.getHeaders()),
                    body));

            Log.d(TAG, "BODY: " + body);
        } catch (JSONException e) {
            Log.w(TAG, "Failed to convert request to JSON (likely invalid format)");
        }
    }

    /**
     * TODO: remove all invocations of this method from production builds with proguard.
     * @param request
     * @param response
     */
    protected void logResponse(HttpRequest request, ClientHttpResponse response) {

        String body = null;
        try {
            // bizarre but for whatever reason simply reading this value seems to fix a bug
            // with the response body sometimes coming back empty, even though the server is responding with a body.
            String statusText = response.getStatusText();
            body = readFully(response.getBody());
        } catch (IOException e) {
            Log.w(TAG, e);
        }
        try {
            Log.d(TAG, String.format(RESPONSE_LOG_FORMAT,
                    request.getURI().toString(),
                    request.getMethod().name(),
                    request.hashCode(),
                    response.getRawStatusCode(),
                    getHeadersAsString(response.getHeaders()),
                    body != null ? "\n" + new JSONObject(body).toString(2) : "<EMPTY>"));
        } catch (IOException | JSONException e) {
            Log.w(TAG, "Failed to convert response to JSON (likely invalid format)");
        }
    }

    private String getHeadersAsString(HttpHeaders headers) {
        StringBuffer headerStr = new StringBuffer("<NONE>");
        if (headers.size() > 0) {
            headerStr = new StringBuffer("\n");
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                for (String value : header.getValue()) {
                    headerStr.append(header.getKey()).append(" = ").append(value).append("\n");
                }
            }
        }
        return headerStr.toString();
    }

    private String readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.size() > 0 ? baos.toString() : null;
    }
}