import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static utils.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateGistTest extends TestBase {
    private static JSONObject gist;
    private static String gistId;

    @BeforeMethod
    public void setup(){
        gist = getGistHelper().createGist().asJson();
        gistId = gist.getString(ID);
    }

    @AfterMethod
    public void tearDown() {
        getGistHelper().deleteGist(gist.getString(ID));
    }

    @Test
    public void shouldUpdateGistWithAddingFile() {
        JSONObject newFileObject = getGistUtil().getDummyFiles(1);
        String newFile = newFileObject.keySet().stream().findFirst().get();
        JSONObject updateBody = new JSONObject().put(FILES, newFileObject);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        List<String> updatedFiles = new ArrayList<>(updatedGist.getJSONObject(FILES).toMap().keySet());
        assertThat(updatedFiles, hasItem(newFile));
    }

    @Test
    public void shouldUpdateGistWithRemovingFile() {
        Set<Map.Entry<String, Object>> gistFiles = gist.getJSONObject(FILES).toMap().entrySet();
        String fileToBeRemoved = gistFiles.stream().findFirst().get().getKey();

        JSONObject body = new JSONObject().put(fileToBeRemoved, JSONObject.NULL);
        JSONObject updateBody = new JSONObject().put(FILES, body);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();
        Set<String> updatedFiles = updatedGist.getJSONObject(FILES).toMap().keySet();

        assertThat(updatedFiles, not(hasItem(fileToBeRemoved)));
        assertThat(updatedFiles.size(), equalTo(gistFiles.size() - 1));
    }

    @Test
    public void shouldUpdateGistDescription() {
        String newDescription = getGistUtil().dummyDescription();
        JSONObject updateBody = new JSONObject()
                .put(DESCRIPTION, newDescription);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        assertThat(updatedGist.getString(DESCRIPTION), equalTo(newDescription));
    }

    // negative cases

    @Test
    public void shouldNotUpdateGistWithEmptyFilesSection() {
        JSONObject testGistBody = getGistUtil().getDummyFiles(0);
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFilesSectionIsNotAnObject() {
        JSONObject testGistBody = getGistUtil().get();
        testGistBody.put(FILES, true);
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFilesSectionHasEmptyFile() {
        JSONObject emptyFile = new JSONObject()
                .put(SOME_FILE, JSONObject.NULL);
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, emptyFile);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFileHasNoContent() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject());
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsNull() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, JSONObject.NULL));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsNotString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, 123));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsEmptyString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, EMPTY_STRING));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWithoutBody() {
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId);
        
        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistWithEmptyBody() {
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(new JSONObject());

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotUpdateGistIfUserIsNotAuthorized() {
        JSONObject updateBody = new JSONObject()
                .put(DESCRIPTION, getGistUtil().dummyDescription());

        RequestHelper updateRequest = new RequestHelperImpl()
                .withHeader(AUTHORIZATION, TOKEN_INVALID)
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);

        ResponseHelper response = getGistHelper().updateGist(updateRequest);

        assertThat(response.getStatus(), equalTo(UNAUTHORIZED));
    }
}
