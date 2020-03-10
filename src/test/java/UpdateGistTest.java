import impl.GistUtils;
import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateGistTest extends TestBase {
    private static JSONObject gist;

    @BeforeMethod
    public void setup(){
        gist = getGistHelper().createGist().asJson();
    }

    @AfterMethod
    public void tearDown() {
        getGistHelper().deleteGist(gist.getString("id"));
    }

    @Test
    public void shouldUpdateGistWithAddingFile() {
        String gistId = gist.getString("id");

        JSONObject newFile = GistUtils.getDummyFiles(1);
        JSONObject updateBody = new JSONObject().put("files", newFile);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        Set<String> updatedFiles = updatedGist.getJSONObject("files").toMap().keySet();
        assertThat(updatedFiles, contains(newFile));
    }

    @Test
    public void shouldUpdateGistWithRemovingFile() {
        String gistId = gist.getString("id");
        Set<Map.Entry<String, Object>> gistFiles = gist.getJSONObject("files").toMap().entrySet();
        String fileToBeRemoved = gistFiles.stream().findFirst().get().getKey();

        JSONObject body = new JSONObject().put(fileToBeRemoved, JSONObject.NULL);
        JSONObject updateBody = new JSONObject().put("files", body);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();
        Set<String> updatedFiles = updatedGist.getJSONObject("files").toMap().keySet();

        assertThat(updatedFiles, not(contains(fileToBeRemoved)));
        assertThat(updatedFiles.size(), equalTo(gistFiles.size()));
    }

    @Test
    public void shouldUpdateGistDescription() {
        String gistId = gist.getString("id");
        String newDescription = GistUtils.dummyDescription();
        JSONObject updateBody = new JSONObject().put("description", newDescription);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        assertThat(updatedGist.getString("description"), equalTo(newDescription));
    }

    // negative

    @Test
    public void shouldNotUpdateGistIfUserIsNotAuthorized() {
        String gistId = gist.getString("id");
        JSONObject updateBody = new JSONObject().put("description", GistUtils.dummyDescription());

        RequestHelper updateRequest = new RequestHelperImpl()
                .withHeader("Authorization", "token invalid")
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);

        ResponseHelper response = getGistHelper().updateGist(updateRequest);

        assertThat(response.getStatus(), equalTo(401));
    }
}
