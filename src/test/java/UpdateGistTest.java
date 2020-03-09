import impl.GistUtils;
import impl.RequestHelperImpl;
import logic.RequestHelper;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateGistTest extends TestBase {
    private static JSONObject gist;

    @BeforeMethod
    public void setup(){
        gist = getGistHelper().createGist().asJson();
    }

    @AfterMethod
    public void tearDown() {
        RequestHelper deleteRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gist.getString("id"));
        getGistHelper().deleteGist(deleteRequest);
    }

    @Test
    public void shouldUpdateGistWithAddingFile() {
        String gistId = gist.getString("id");

        JSONObject updateBody = new JSONObject().put("files", GistUtils.getDummyFilesBody(2));

        CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
                updatedAtCustomization(),
                new Customization("files", (actual, expected) -> {
                    JSONObject a = (JSONObject)actual;
                    JSONObject e = (JSONObject)expected;
                    return a.length() == e.length()
                            && a.toMap().keySet().containsAll(updateBody.getJSONObject("files").toMap().keySet());
                })
        );

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(updateBody.toString(), updatedGist.toString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(updatedGist.toString(), retrievedGist.toString(), comparator);


    }

    private Customization updatedAtCustomization() {
        return new Customization("updated_at", (actual, expected) ->{
            DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;
            return LocalDateTime.parse(actual.toString(), df).isAfter(LocalDateTime.parse(gist.getString("updated_at"), df));
        }
        );
    }

    @Test
    public void shouldUpdateGistWithRemovingFile() {
        String gistId = gist.getString("id");
        String fileToBeRemoved = gist.getJSONObject("files").toMap().entrySet().stream().findFirst().get().getKey();

        JSONObject body = new JSONObject().put(fileToBeRemoved, JSONObject.NULL);


        JSONObject updateBody = new JSONObject().put("files", body);

        CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT,
                updatedAtCustomization(),
                new Customization("files", (actual, expected) -> {
                    JSONObject a = (JSONObject)actual;
                    JSONObject e = (JSONObject)expected;
                    return a.length() == e.length()
                            && !a.toMap().keySet().contains(fileToBeRemoved);
                })
        );

        RequestHelper updateRequest = new RequestHelperImpl()
                .withUrl(GISTS_URL + "/" + gistId)
                .withBody(updateBody);
        JSONObject updatedGist = getGistHelper().updateGist(updateRequest).asJson();

        RequestHelper getRequest = new RequestHelperImpl().withUrl(GISTS_URL + "/" + gistId);
        JSONObject retrievedGist = getGistHelper().getGist(getRequest).asJson();

        JSONAssert.assertEquals(updatedGist.toString(), retrievedGist.toString(), comparator);
    }
}
