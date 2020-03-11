package logic;

public interface RestClient {

    /**
     * method to send a GET request
     * @param requestHelper
     * @return
     */
    ResponseHelper get(RequestHelper requestHelper);

    /**
     * method to send a POST request
     * @param requestHelper
     * @return
     */
    ResponseHelper post(RequestHelper requestHelper);

    /**
     * method to send a PATCH request
     * @param requestHelper
     * @return
     */
    ResponseHelper patch(RequestHelper requestHelper);

    /**
     * method to send a DELETE request
     * @param requestHelper
     * @return
     */
    ResponseHelper delete(RequestHelper requestHelper);
}
