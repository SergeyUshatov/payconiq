package logic;

import org.json.JSONObject;

public interface ResponseHelper {

    /**
     * method to get original response for further processing
     * @return
     */
    Object getOriginal();

    /**
     * method to get a status code from a response
     * @return
     */
    int getStatus();

    /**
     * method to return response as json object
     * @return
     */
    JSONObject asJson();
}
