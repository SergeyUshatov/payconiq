package logic;

import org.json.JSONObject;

import java.util.Map;

/**
 * interface provides request's helper methods
 * such as body, headers and url
 *
 * works like a builder
 */
public interface RequestHelper {

    /**
     * method to set a body into a request
     * @param body JSONObject
     * @return
     */
    RequestHelper withBody(JSONObject body);

    /**
     * method to add custom headers
     * @param headers Map
     * @return
     */
    RequestHelper withHeaders(Map<String, Object> headers);

    /**
     * method to add a header
     * @param key String
     * @param value Object
     * @return
     */
    RequestHelper withHeader(String key, Object value);

    /**
     * method to set request's url
     * @param url
     * @return
     */
    RequestHelper withUrl(String url);

    /**
     * method to retrieve request's url
     * @return String
     */
    String getUrl();

    /**
     * method to retrieve body for a request
     * @return
     */
    JSONObject getBody();

    /**
     * method to retrieve headers for a request
     * @return
     */
    Map<String, Object> getHeaders();


}
