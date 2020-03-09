package impl;

import logic.GistHelper;
import logic.RequestHelper;
import logic.ResponseHelper;
import logic.RestClient;
import org.json.JSONObject;
import utils.PropertyLoader;

import java.util.List;
import java.util.Map;

public class GistHelperImpl implements GistHelper {
    private static String BASE_URL = PropertyLoader.loadProperty("baseUrl");
    private static final String GISTS_URL = BASE_URL + "gists";

    private RestClient restClient;

    public GistHelperImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ResponseHelper createGist(RequestHelper request) {
        request.withUrl(GISTS_URL);
        return restClient.post(request);
    }

    @Override
    public ResponseHelper getGist(RequestHelper requestHelper) {
        return restClient.get(requestHelper);
    }

    @Override
    public ResponseHelper deleteGist(RequestHelper requestHelper) {
        return restClient.delete(requestHelper);
    }

    @Override
    public ResponseHelper updateGist(RequestHelper requestHelper) {
        return restClient.patch(requestHelper);
    }

    @Override
    public ResponseHelper createGist() {
        JSONObject dummyBody = GistUtils.dummyGist(3);
        RequestHelper createRequest = new RequestHelperImpl().withBody(dummyBody);
        return createGist(createRequest);
    }
}
