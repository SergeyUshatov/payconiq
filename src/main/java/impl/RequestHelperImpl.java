package impl;

import io.restassured.http.ContentType;
import logic.RequestHelper;
import org.json.JSONObject;
import utils.PropertyLoader;

import java.util.HashMap;
import java.util.Map;

public final class RequestHelperImpl implements RequestHelper {
    private String url;
    private JSONObject body;
    private Map<String, Object> headers;

    public RequestHelperImpl() {
        body = new JSONObject();
        headers = new HashMap<>();
        headers.put("Content-Type", ContentType.JSON);
        headers.put("Accept", ContentType.JSON.getAcceptHeader());
        headers.put("Authorization", "token " + PropertyLoader.loadProperty("personal.token"));
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public JSONObject getBody() {
        return body;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public RequestHelper withUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestHelper withBody(JSONObject body) {
        this.body = body;
        return this;
    }

    @Override
    public RequestHelper withHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public RequestHelper withHeader(String key, Object value) {
        headers.put(key, value);
        return this;
    }
}
