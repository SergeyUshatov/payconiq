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
import static utils.Constants.*;

public class CreateGistTest extends TestBase {

    @Test
    public void shouldCreateGistWithAllFields() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(3)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper gist = getGistHelper().createGist(request);
        JSONObject createdGist = gist.asJson();

        String gistId = createdGist.getString(ID);
        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT); // check that response has  body as in request
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt()); // check that retrieved gist is the same as in POST response

        getGistHelper().deleteGist(gistId);
    }

    @Test
    public void shouldCreateGistWithRequiredFieldsOnly() {
        JSONObject testGistBody = getGistUtil()
                .withFiles(3)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        JSONObject createdGist = getGistHelper().createGist(request).asJson();

        String gistId = createdGist.getString(ID);
        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT); // check that response has  body as in request
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt()); // check that retrieved gist is the same as in POST response

        getGistHelper().deleteGist(gistId);
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
        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT); // check that response has  body as in request
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt()); // check that retrieved gist is the same as in POST response

        getGistHelper().deleteGist(gistId);
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
        JSONObject retrievedGist = getGistHelper().getGist(gistId).asJson();

        JSONAssert.assertEquals(testGistBody.toString(), createdGist.toString(), JSONCompareMode.LENIENT); // check that response has  body as in request
        JSONAssert.assertEquals(createdGist.toString(), retrievedGist.toString(), compareIgnoringUpdatedAt()); // check that retrieved gist is the same as in POST response

        getGistHelper().deleteGist(gistId);
    }

    // negative cases

    @Test
    public void shouldNotCreateGistWithoutFilesSection() {
        JSONObject testGistBody = getGistUtil()
                .withoutFiles()
                .get();

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWithEmptyFilesSection() {
        JSONObject testGistBody = getGistUtil()
                .dummyGist(0)
                .get();
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFilesSectionIsNotAnObject() {
        JSONObject testGistBody = getGistUtil().get();
        testGistBody.put(FILES, true);
        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFilesSectionHasEmptyFile() {
        JSONObject emptyFile = new JSONObject()
                .put(SOME_FILE, JSONObject.NULL);
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, emptyFile);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFileHasNoContent() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject());
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFileContentIsNull() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, JSONObject.NULL));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFileContentIsNotString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, 123));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWhenFileContentIsEmptyString() {
        JSONObject fileWithoutContent = new JSONObject()
                .put(SOME_FILE, new JSONObject().put(CONTENT, ""));
        JSONObject testGistBody = getGistUtil()
                .get()
                .put(FILES, fileWithoutContent);

        RequestHelper request = new RequestHelperImpl().withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWithoutBody() {
        ResponseHelper response = getGistHelper().createGist(new RequestHelperImpl());
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistWithEmptyBody() {
        RequestHelper request = new RequestHelperImpl()
                .withBody(new JSONObject());

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldNotCreateGistIfUserIsNotAuthorized() {
        JSONObject testGistBody = getGistUtil().dummyGist(1).get();
        RequestHelper request = new RequestHelperImpl()
                .withHeader(AUTHORIZATION, TOKEN_INVALID)
                .withBody(testGistBody);

        ResponseHelper response = getGistHelper().createGist(request);
        //checking only stats because I have no access to a DB and have no gist id to check with GET request that a gist was not created
        assertThat(response.getStatus(), equalTo(UNAUTHORIZED));
    }

}
