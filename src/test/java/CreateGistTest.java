import impl.RequestHelperImpl;
import logic.RequestHelper;
import logic.ResponseHelper;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.CompareUtils.compareIgnoringUpdatedAt;

public class CreateGistTest extends TestBase {

    private static final String ID = "id";

    @Test
    public void shouldCreateGistWithAllFields() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(3)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        JSONObject createdGist = getGistHelper().createGist(request).asJson();

        String gistId = createdGist.getString(ID);
        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }

    @Test
    public void shouldCreateGistWithRequiredFieldsOnly() {
        JSONObject testGistBody = getGistUtil()
                .withFiles(3)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        JSONObject createdGist = getGistHelper().createGist(request).asJson();

        String gistId = createdGist.getString(ID);
        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }

    @Test
    public void shouldCreateGistWithoutDescription() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(3)
                .withoutDescription()
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        JSONObject createdGist = getGistHelper().createGist(request).asJson();

        String gistId = createdGist.getString(ID);
        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }

    @Test
    public void shouldCreateGistWithoutIsPublicFlag() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(3)
                .withoutDescription()
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        JSONObject createdGist = getGistHelper().createGist(request).asJson();

        String gistId = createdGist.getString(ID);
        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt());

        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        getGistHelper().deleteGist(deleteRequest);
    }

    // negative cases

    @Test
    public void shouldNotCreateGistWithoutFilesSection() {
        JSONObject testGistBody = getGistUtil()
                .withoutFiles()
                .get();

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotCreateGistWithEmptyFilesSection() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(0)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotCreateGistWhenFilesSectionIsNotAnObject() {
        JSONObject testGistBody = getGistUtil().get();
        testGistBody.put("files", true);
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotCreateGistWhenFilesSectionHasEmptyFile() {
        JSONObject emptyFile = new JSONObject()
                .put("some_file", JSONObject.NULL);
        JSONObject testGistBody = getGistUtil()
                .get()
                .put("files", emptyFile);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void shouldNotCreateGistIfUserIsNotAuthorized() {
        JSONObject testGistBody = getGistUtil().dummyGist(1).get();
        RequestHelper request = new RequestHelperImpl()
                .withHeader("Authorization", "token invalid")
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        assertThat(response.getStatus(), equalTo(401));
    }

}
