package impl;

import io.restassured.response.Response;
import logic.ResponseHelper;
import org.json.JSONObject;

import java.util.Map;

public class ResponseHelperImpl implements ResponseHelper {
    private Response response;

    public ResponseHelperImpl(Response response) {
        this.response = response;
    }

    @Override
    public Object getOriginal() {
        return response;
    }

    @Override
    public int getStatus() {
        return response.statusCode();
    }

    @Override
    public JSONObject asJson() {
        Map map = response.jsonPath().get();
        return new JSONObject(map);
    }

}
