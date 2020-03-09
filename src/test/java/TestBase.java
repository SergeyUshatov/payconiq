import impl.GistUtilImpl;
import impl.GistHelperImpl;
import impl.RestClientImpl;
import logic.GistUtil;
import logic.GistHelper;
import logic.RestClient;
import utils.PropertyLoader;

public abstract class TestBase {
    static String BASE_URL = PropertyLoader.loadProperty("baseUrl");
    static final String GISTS_URL = BASE_URL + "gists";

    private RestClient  restClient;
    private GistHelper gistHelper;
    private GistUtil gistUtilHelper;
    
    synchronized RestClient getRestClient() {
        if (restClient == null) {
            restClient = new RestClientImpl();
        }
        return restClient;
    }
    
    synchronized GistHelper getGistHelper() {
        if (gistHelper == null) {
            gistHelper = new GistHelperImpl(getRestClient());
        }
        return gistHelper;
    }

    synchronized GistUtil getGistUtil() {
        if (gistUtilHelper == null) {
            gistUtilHelper = new GistUtilImpl();
        }

        return gistUtilHelper;
    }
}
