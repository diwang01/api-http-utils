package me.heng.model;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import javax.annotation.concurrent.NotThreadSafe;
import java.net.URI;

/**
 * AUTHOR: wangdi
 * DATE: 25/06/2018
 * TIME: 11:01 AM
 */
@NotThreadSafe
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }
    public HttpDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }
    public HttpDeleteWithBody() { super(); }
}

