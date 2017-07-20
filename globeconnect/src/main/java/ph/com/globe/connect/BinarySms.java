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

import java.util.Map;
import java.util.HashMap;

/**
 * BinarySms Class.
 *
 * @author charleszamora
 */
public class BinarySms extends Sms {
    /* Sms-Binary API url */
    private final String SMS_BIN_URL = "https://devapi.globelabs.com.ph/binarymessaging/v1/outbound/%s/requests";

    /* Message UDH */
    protected String userDataHeader = null;

    /* Data coding of the message */
    protected int dataCodingScheme = 0;

    /* Binary message */
    protected String binaryMessage = null;

    /**
     * Create BinarySms class without parameters.
     */
    public BinarySms() {
        super();
    }

    /**
     * Create BinarySms class with sendAddress parameter.
     *
     * @param senderAddress app short code
     */
    public BinarySms(String senderAddress) {
        super(senderAddress);
    }

    /**
     * Create BinarySms class with senderAddress and
     * accessToken parameter.
     *
     * @param senderAddress app short code
     * @param accessToken access token
     */
    public BinarySms(String senderAddress, String accessToken) {
        super(senderAddress, accessToken);
    }

    /**
     * Sets sender address (Short Code)
     *
     * @param  senderAddress app short code
     * @return this
     */
    @Override
    public BinarySms setSenderAddress(String senderAddress) {
        // call base method
        super.setSenderAddress(senderAddress);

        return this;
    }

    /**
     * Sets access token.
     *
     * @param  accessToken access token
     * @return this
     */
    @Override
    public BinarySms setAccessToken(String accessToken) {
        // call base method
        super.setAccessToken(accessToken);

        return this;
    }

    /**
     * Sets receiver address.
     *
     * @param  receiverAddress receiver address
     * @return this
     */
    @Override
    public BinarySms setReceiverAddress(String receiverAddress) {
        // call base method
        super.setReceiverAddress(receiverAddress);

        return this;
    }

    /**
     * Sets user data header.
     *
     * @param  userDataHeader user data header
     * @return this
     */
    public BinarySms setUserDataHeader(String userDataHeader) {
        // set user data header
        this.userDataHeader = userDataHeader;

        return this;
    }

    /**
     * Sets data coding scheme.
     *
     * @param  dataCodingScheme data coding scheme
     * @return this
     */
    public BinarySms setDataCodingScheme(int dataCodingScheme) {
        // set data coding scheme
        this.dataCodingScheme = dataCodingScheme;

        return this;
    }

    /**
     * Sets binary message.
     *
     * @param  binaryMessage binary message
     * @return this
     */
    public BinarySms setBinaryMessage(String binaryMessage) {
        // set binary message
        this.binaryMessage = binaryMessage;

        return this;
    }

    /**
     * Sends binary message request.
     *
     * @param  userDataHeader user data header
     * @param  dataCodingScheme data coding scheme
     * @param  receiverAddress receiver address
     * @param  binaryMessage binary message
     * @param  asyncHandler async handler instance
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendBinaryMessage(
        String userDataHeader,
        int dataCodingScheme,
        String receiverAddress,
        String binaryMessage,
        AsyncHandler asyncHandler)
        throws ApiException, HttpRequestException, HttpResponseException {

        // get the url
        String url = this.buildUrl(this.SMS_BIN_URL);

        // set base data
        Map<String, Object> data = new HashMap<>();

        // set outbound binary messsage request data
        Map<String, Object> obmr = new HashMap<>();

        // set message
        Map<String, String> msg = new HashMap<>();

        // set sender address
        obmr.put("senderAddress", this.senderAddress);

        // set binary message
        msg.put("message", binaryMessage);

        // set user data header
        obmr.put("userDataHeader", userDataHeader);

        // set data coding scheme
        obmr.put("dataCodingScheme", Integer.toString(dataCodingScheme));

        // set address
        obmr.put("address", receiverAddress);

        // set outbound binary message
        obmr.put("outboundBinaryMessage", new org.json.JSONObject(msg));

        // set to base data
        data.put("outboundBinaryMessageRequest", new org.json.JSONObject(obmr));

        // send request
        new HttpRequest()
        // set url
        .setUrl(url)
        // set data
        .setData(data)
        // set async handler
        .setAsyncHandler(asyncHandler)
        // execute async post
        .execute("post");
    }

    /**
     * Sends binary message request.
     *
     * @param  asyncHandler async handler instance
     * @throws ApiException api exception
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response exception
     */
    public void sendBinaryMessage(AsyncHandler asyncHandler)
        throws ApiException, HttpRequestException, HttpResponseException {

        // call send binary message
        this.sendBinaryMessage(
            this.userDataHeader,
            this.dataCodingScheme,
            this.receiverAddress,
            this.binaryMessage,
            asyncHandler);
    }
}
