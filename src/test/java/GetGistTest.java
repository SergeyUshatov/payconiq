import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static utils.CompareUtils.compareIgnoringUpdatedAt;

public class GetGistTest extends TestBase {

    @Test
    public void shouldReturnGist() {
        JSONObject gist = getGistHelper().createGist().asJson();
        String gistId = gist.getString("id");

        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(gist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }

    @Test
    public void shouldNotReturnNotExistingGist() {
        String randomId = RandomStringUtils.randomAlphanumeric(32);

        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + randomId);
        ResponseHelper retrievedGist = getGistHelper().getGist(getRequest);

        assertThat(retrievedGist.getStatus(), equalTo(404));
    }

    @Test
    public void shouldNotReturnGist() {
        JSONObject gist = getGistHelper().createGist().asJson();
        String gistId = gist.getString("id");

        RequestHelper unauthorizedGetRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withHeader("Authorization",  "token invalid");

        ResponseHelper response = getGistHelper().getGist(unauthorizedGetRequest);
        assertThat(response.getStatus(), equalTo(401));

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }
}
