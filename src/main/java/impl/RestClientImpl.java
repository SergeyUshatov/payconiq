package impl;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import logic.RequestHelper;
import logic.ResponseHelper;
import logic.RestClient;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import utils.PropertyLoader;

import static utils.Constants.*;
import static io.restassured.RestAssured.given;

public class RestClientImpl implements RestClient {
    final static Logger logger = Logger.getLogger(RestClientImpl.class);
    private static final String token = PropertyLoader.loadProperty("personal.token");

    @Override
    public ResponseHelper get(RequestHelper requestHelper) {
        logger.info("Going to send GET request with headers: "
                + requestHelper.getHeaders()
                + " and url: "
                + requestHelper.getUrl()) ;
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
        logger.info("Going to send POST request with body: "
                + requestHelper.getBody()
                + " and headers: "
                + requestHelper.getHeaders()
                + " and url: "
                + requestHelper.getUrl());

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
        logger.info("Going to send PATCH request with body: "
                + requestHelper.getBody()
                + "  and headers: "
                + requestHelper.getHeaders()
                + " and url: "
                + requestHelper.getUrl());
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

        logger.info("Going to send DELETE request with headers:"
                + requestHelper.getHeaders()
                + " and url: "
                + requestHelper.getUrl());
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
            req.header(CONTENT_TYPE, ContentType.JSON)
                    .header(ACCEPT, ContentType.JSON.getAcceptHeader())
                    .header(AUTHORIZATION, TOKEN + token);
        } else
            req.headers(requestHelper.getHeaders());
    }
}
