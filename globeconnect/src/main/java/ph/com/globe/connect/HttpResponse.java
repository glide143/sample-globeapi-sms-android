/*
 * The MIT License
 *
 * Copyright 2016 charleszamora.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ph.com.globe.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONObject;

/**
 * HttpResponse helper.
 *
 * @author Charles Zamora czamora@openovate.com
 */
public class HttpResponse {
    /* Set raw response object */
    protected CloseableHttpResponse rawResponse = null;

    /* Response string */
    protected String body = null;

    /**
     * Create HttpResponse without parameter.
     */
    public HttpResponse() {}

    /**
     * Create HttpResponse with response parameter.
     *
     * @param body response string
     */
    public HttpResponse(String body) {
        // set response string
        this.body = body;
    }

    /**
     * Create HttpResponse with raw response
     * object parameter.
     *
     * @param rawResponse raw http response
     */
    public HttpResponse(
            CloseableHttpResponse rawResponse) {

        // set response
        this.rawResponse = rawResponse;
    }

    /**
     * Create HttpResponse with raw response
     * and string response parameter.
     *
     * @param  rawResponse raw http response
     * @param  body response body
     */
    public HttpResponse(
            CloseableHttpResponse rawResponse,
            String body) {

        // set raw response
        this.rawResponse = rawResponse;
        // set response body
        this.body = body;
    }

    /**
     * Set raw response.
     *
     * @param  rawResponse raw http response
     * @return this
     */
    public HttpResponse setRawResponse(
            CloseableHttpResponse rawResponse) {

        // set response
        this.rawResponse = rawResponse;

        return this;
    }

    /**
     * Set response string.
     *
     * @param  body response string
     * @return this
     */
    public HttpResponse setBody(String body) {
        // set response string
        this.body = body;

        return this;
    }

    /**
     * Returns the raw response.
     *
     * @return CloseableHttpResponse
     */
    public CloseableHttpResponse getRawResponse() {
        return this.rawResponse;
    }

    /**
     * Returns the response string.
     *
     * @return String
     * @throws HttpResponseException http response exception
     */
    public String getResponse() throws HttpResponseException {
        // try parsing response
        try {
            CloseableHttpResponse rawResponse = this.rawResponse;

            // initialize reader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(rawResponse.getEntity().getContent()));

            // contains the results
            StringBuilder result = new StringBuilder();
            // contains each line of results
            String line = "";

            // while we still have results
            while((line = reader.readLine()) != null) {
                // append that result
                result.append(line);
            }

            return result.toString();
        } catch(IOException e) {
            throw new HttpResponseException(e.getMessage());
        }
    }

    /**
     * Returns a JSONObject from the response string.
     *
     * @return JSONObject
     * @throws HttpResponseException http response exception
     */
    public JSONObject getJsonResponse() throws HttpResponseException {
        // try parsing response string
        try {
            // get raw response
            String body = this.getResponse();

            // parse response string as json
            JSONObject json = new JSONObject(body);

            return json;
        } catch(Exception e) {
            // throw exception
            throw new HttpResponseException(e.getMessage());
        }
    }
}
