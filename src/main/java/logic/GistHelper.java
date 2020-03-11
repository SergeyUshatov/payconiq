package logic;

/**
 * This interface provides basic methods to operate with Git gists.
 */
public interface GistHelper {
    /**
     * method to create a gist with default params
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper createGist();

    /**
     * method to create a gist with custom request
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper createGist(RequestHelper requestHelper);

    /**
     * method to get a gist by id
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper getGist(String id);

    /**
     * method to get a gist with custom request
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper getGist(RequestHelper requestHelper);

    /**
     * method to delete a gist with custom request
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper deleteGist(RequestHelper requestHelper);

    /**
     * method to delete a gist by id
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper deleteGist(String id);

    /**
     * method to update a gist with custom request
     * @return Response helper that contains original response and additional methods to work with it
     */
    ResponseHelper updateGist(RequestHelper updateRequest);
}
