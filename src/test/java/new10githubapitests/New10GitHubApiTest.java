package new10githubapitests;

import com.fasterxml.jackson.core.JsonProcessingException;
import createnewrepodata.RequestBodyForNewRepo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class New10GitHubApiTest {


    //Step 1 from Assignment

    @Test
    public void makeSureRightAccountIsAccessed () {

        JsonPath response = given()
                .spec(getAuthorizedRequestSpec())
                .when()
                .get("https://api.github.com/users/DanaTestForNew10")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(response.getString("login"), is(equalTo("DanaTestForNew10")));
    }

    //step 2 + 3 from Assignment - thereIFixedIt, Readme is already created


        @Test
        public void makeSureNewRepoIsCreated() throws JsonProcessingException {

        RequestBodyForNewRepo requestBody = new RequestBodyForNewRepo();
        requestBody.setName("NeatoAPIRepo");
        requestBody.setAuto_init(true);

        JsonPath response = given()
                .auth().preemptive().basic("DanaTestForNew10", <"token">)
                .spec(getAuthorizedRequestSpec())
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("https://api.github.com/user/repos")
                .then()
                .assertThat().statusCode(201)
                .extract()
                .response()
                .jsonPath();

        System.out.println(requestBody.toString());

        assertThat(response.getString("name"), is(equalTo(requestBody.getName())));
    }


//    Not so pretty version of step 2
//    @Test
//    public void test() {
//
//        String response = given()
//                .spec(getAuthorizedRequestSpec())
//                .header("Authorization", "Token <token>")
//                .body("{\"name\": \"testDana\"," +
//                        "\"auto_init\": true}")
//                .when()
//                .post("https://api.github.com/user/repos")
//                .then()
//                .extract()
//                .asString();
//    }


    private RequestSpecification getAuthorizedRequestSpec() {
        return new RequestSpecBuilder()
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }
}