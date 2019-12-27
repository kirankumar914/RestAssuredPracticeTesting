package restApi.Jira.Automation;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddACommentToTheExistigJira {

    @Test
    public void getTheBugNumber() throws IOException {
        String requestBodyForLogin=generateString("jiralogin.json");

        RestAssured.baseURI = "http://localhost:8080";

        Response res =given().contentType(ContentType.JSON)
                .body(requestBodyForLogin).

                        when().post("/rest/auth/1/session").

                        then().assertThat().statusCode(200)
                .extract().response();

        String response=res.asString();
        JsonPath jsonResponse=new JsonPath(response);
        String sessionId=jsonResponse.getString("session.value");

        System.out.println(sessionId);

        // add a comment to the existing jira.

        String addCommentRequestBody=generateString("addAComment.json");

        Response addCommentRes=given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").
                body(addCommentRequestBody).

        when().post("/rest/api/2/issue/RAT-5/comment").

                        then().assertThat().statusCode(201).extract().response();


        String addCommentResponse=addCommentRes.asString();

        JsonPath jsonAddCommentResponse=new JsonPath(addCommentResponse);

        System.out.println("Adding a comment to the exsting jira"+ jsonAddCommentResponse.prettyPrint());

    }



    public static String generateString(String filename) throws IOException {
        String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
        return new String (Files.readAllBytes(Paths.get(filePath)));
    }
}
