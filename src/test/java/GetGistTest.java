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
import static utils.Constants.*;

public class GetGistTest extends TestBase {

    @Test
    public void shouldReturnGist() {
        JSONObject gist = getGistHelper().createGist().asJson();
        String gistId = gist.getString(ID);

        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(gist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        getGistHelper().deleteGist(gistId);
    }

    @Test
    public void shouldNotReturnNotExistingGist() {
        String randomId = RandomStringUtils.randomAlphanumeric(32);

        ResponseHelper retrievedGist = getGistHelper().getGist(randomId);

        assertThat(retrievedGist.getStatus(), equalTo(NOT_FOUND));
    }

    @Test
    public void shouldNotReturnGist() {
        JSONObject gist = getGistHelper().createGist().asJson();
        String gistId = gist.getString(ID);

        RequestHelper unauthorizedGetRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withHeader(AUTHORIZATION,  TOKEN_INVALID);

        ResponseHelper response = getGistHelper().getGist(unauthorizedGetRequest);
        assertThat(response.getStatus(), equalTo(UNAUTHORIZED));

        getGistHelper().deleteGist(gistId);
    }
}
