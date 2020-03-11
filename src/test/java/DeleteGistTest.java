import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.Constants.*;

public class DeleteGistTest extends TestBase {

    @Test
    public void shouldDeleteGist() {
        JSONObject gist = getGistHelper().createGist().asJson();
        String gistId = gist.getString(ID);

        ResponseHelper deleteResponse = getGistHelper().deleteGist(gistId);

        assertThat(deleteResponse.getStatus(), equalTo(NO_CONTENT_204));
        // checking that the gist is removed
        ResponseHelper makeSureResponse = getGistHelper().deleteGist(gistId);
        assertThat(makeSureResponse.getStatus(), equalTo(NOT_FOUND));
    }

    // negative

    @Test
    public void shouldNotDeleteNotExistingGist() {
        String randomId = RandomStringUtils.randomAlphanumeric(32);

        ResponseHelper deleteResponse = getGistHelper().deleteGist(randomId);

        assertThat(deleteResponse.getStatus(), equalTo(NOT_FOUND));
    }

    @Test
    public void shouldNotDeleteGistIfUserIsNotAuthorized() {
        JSONObject gist = getGistHelper().createGist().asJson();

        String gistId = gist.getString(ID);
        RequestHelper unauthorizedDeleteRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withHeader(AUTHORIZATION,  TOKEN_INVALID);

        ResponseHelper deleteResponse = getGistHelper().deleteGist(unauthorizedDeleteRequest);

        assertThat(deleteResponse.getStatus(), equalTo(UNAUTHORIZED));

        getGistHelper().deleteGist(gistId);
    }
}
