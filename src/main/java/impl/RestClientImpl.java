package impl;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logic.RequestHelper;
import logic.ResponseHelper;
import logic.RestClient;

import io.restassured.http.ContentType;
import utils.PropertyLoader;

import static io.restassured.RestAssured.given;

public class RestClientImpl implements RestClient {
    private static final String token = PropertyLoader.loadProperty("personal.token");

    @Override
    public ResponseHelper get(RequestHelper requestHelper) {
        RequestSpecification requestSpecification = given().urlEncodingEnabled(true);
        setHeaders(requestHelper, requestSpecification);

        Response response = requestSpecification
                .when()
                .get(requestHelper.getUrl())
                .then()
                .extract()
                .response();
        return new ResponseHelperImpl(response);
    }

    @Override
    public ResponseHelper post(RequestHelper requestHelper) {
        RequestSpecification requestSpecification = given().urlEncodingEnabled(true);
        setHeaders(requestHelper, requestSpecification);

        Response response = requestSpecification
                .body(requestHelper.getBody().toString())
                .when()
                .post(requestHelper.getUrl())
                .then()
                .extract()
                .response();
        return new ResponseHelperImpl(response);
    }

    @Override
    public ResponseHelper patch(RequestHelper requestHelper) {
        RequestSpecification requestSpecification = given().urlEncodingEnabled(true);
        setHeaders(requestHelper, requestSpecification);

        Response response = requestSpecification
                .body(requestHelper.getBody().toString())
                .when()
                .patch(requestHelper.getUrl())
                .then()
                .extract()
                .response();
        return new ResponseHelperImpl(response);
    }

    @Override
    public ResponseHelper delete(RequestHelper requestHelper) {
        RequestSpecification requestSpecification = given().urlEncodingEnabled(true);
        setHeaders(requestHelper, requestSpecification);

        Response response = requestSpecification
                .when()
                .delete(requestHelper.getUrl())
                .then()
                .extract()
                .response();
        return new ResponseHelperImpl(response);
    }

    private void setHeaders(RequestHelper requestHelper, RequestSpecification req) {
        if (requestHelper == null || requestHelper.getHeaders() == null) {
            req.header("Content-Type", ContentType.JSON)
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .header("Authorization", "token " + token);
        } else
            req.headers(requestHelper.getHeaders());
    }
}
