import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteGistTest extends TestBase {

    @Test
    public void shouldDeleteGist() {
        JSONObject gist = getGistHelper().createGist().asJson();

        String gistId = gist.getString("id");
        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        ResponseHelper deleteResponse = getGistHelper().deleteGist(deleteRequest);

        assertThat(deleteResponse.getStatus(), equalTo(204));
        ResponseHelper makeSureResponse = getGistHelper().deleteGist(deleteRequest);
        assertThat(makeSureResponse.getStatus(), equalTo(404));
    }

    // negative

    @Test
    public void shouldNotDeleteNotExistingGist() {
        String randomId = RandomStringUtils.randomAlphanumeric(32);

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + randomId);
        ResponseHelper deleteResponse = getGistHelper().deleteGist(deleteRequest);

        assertThat(deleteResponse.getStatus(), equalTo(404));
    }

    @Test
    public void shouldNotDeleteGistIfUserIsNotAuthorized() {
        JSONObject gist = getGistHelper().createGist().asJson();

        String gistId = gist.getString("id");
        RequestHelper unauthorizedDeleteRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withHeader("Authorization",  "token invalid");

        ResponseHelper deleteResponse = getGistHelper().deleteGist(unauthorizedDeleteRequest);

        assertThat(deleteResponse.getStatus(), equalTo(401));

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }
}
