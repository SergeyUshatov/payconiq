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

        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(gist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        getGistHelper().deleteGist(gistId);
    }

    @Test
    public void shouldNotReturnNotExistingGist() {
        String randomId = RandomStringUtils.randomAlphanumeric(32);

        ResponseHelper retrievedGist = getGistHelper().getGist(randomId);

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

        getGistHelper().deleteGist(gistId);
    }
}
