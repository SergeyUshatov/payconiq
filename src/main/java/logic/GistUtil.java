package logic;

import org.json.JSONObject;

/**
 * Interface provides basic methods to get gist's body for POST or PATCH requests
 */
public interface GistUtil {
    /**
     * method to get a default gist's body with N files
     * @param filesCount - amount of files you want to be added to body
     * @return
     */
    GistUtil dummyGist(int filesCount);

    /**
     * method to set a gist description
     * @param description - String
     * @return
     */
    GistUtil withDescription(String description);

    /**
     * method to add files section to a gist's body in a request
     * @param filesCount
     * @return
     */
    GistUtil withFiles(int filesCount);

    /**
     * method to remove description from gist's body request
     * @return
     */
    GistUtil withoutDescription();

    /**
     * method to remove files section from a gist's body request
     * @return
     */
    GistUtil withoutFiles();

    /**
     * method to get a gist's description
     * @return
     */
    String dummyDescription();

    /**
     * method to get a files section with N files
     * @param filesCount
     * @return
     */
    JSONObject getDummyFiles(int filesCount);

    /**
     * method to return desired gists body configured with another methods
     * @return
     */
    JSONObject get();
}
