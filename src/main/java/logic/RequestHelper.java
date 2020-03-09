package logic;

import org.json.JSONObject;

import java.util.Map;

public interface RequestHelper {

    RequestHelper withBody(JSONObject body);
    RequestHelper withHeaders(Map<String, Object> headers);
    RequestHelper withHeader(String key, Object value);

    String getUrl();
    JSONObject getBody();
    Map<String, Object> getHeaders();

    RequestHelper withUrl(String url);
}
