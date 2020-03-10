import impl.GistUtils;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateGistTest extends TestBase {
    public static final String ID = "id";
    public static final String FILES = "files";
    public static final String DESCRIPTION = "description";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_INVALID = "token invalid";
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
        JSONObject newFileObject = GistUtils.getDummyFiles(1);
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
        String newDescription = GistUtils.dummyDescription();
        JSONObject updateBody = new JSONObject()
                .put(DESCRIPTION, newDescription);

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        assertThat(updatedGist.getString(DESCRIPTION), equalTo(newDescription));
    }

    // negative

    @Test
    public void shouldNotUpdateGistWithEmptyFilesSection() {
        JSONObject testGistBody = GistUtils.getDummyFiles(0);
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFilesSectionIsNotAnObject() {
        JSONObject testGistBody = getGistUtil().get();
        testGistBody.put("files", true);
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFilesSectionHasEmptyFile() {
        JSONObject emptyFile = new JSONObject()
                .put("some_file", JSONObject.NULL);
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", emptyFile);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFileHasNoContent() {
        JSONObject fileWithoutContent = new JSONObject()
                .put("some_file", new JSONObject());
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsNull() {
        JSONObject fileWithoutContent = new JSONObject()
                .put("some_file", new JSONObject().put("content", JSONObject.NULL));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsNotString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put("some_file", new JSONObject().put("content", 123));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWhenFileContentIsEmptyString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put("some_file", new JSONObject().put("content", ""));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", fileWithoutContent);

        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }
    
    
    
    
    
    
    
    
    
    
    
    

    @Test
    public void shouldNotUpdateGistWithoutBody() {
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId);
        
        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistWithEmptyBody() {
        RequestHelper request = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(new JSONObject());

        ResponseHelper response = getGistHelper().updateGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotUpdateGistIfUserIsNotAuthorized() {
        JSONObject updateBody = new JSONObject()
                .put(DESCRIPTION, GistUtils.dummyDescription());

        RequestHelper updateRequest = new RequestHelperImpl()
                .withHeader(AUTHORIZATION, TOKEN_INVALID)
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);

        ResponseHelper response = getGistHelper().updateGist(updateRequest);

        assertThat(response.getStatus(), equalTo(401));
    }
}
