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

public class UpdateExistingComment {

    @Test
    public void getTheBugNumber() throws IOException {
        String requestBodyForLogin = generateString("jiralogin.json");

        RestAssured.baseURI = "http://localhost:8080";

        Response res = given().contentType(ContentType.JSON)
                .body(requestBodyForLogin).

                        when().post("/rest/auth/1/session").

                        then().assertThat().statusCode(200)
                .extract().response();

        String response = res.asString();
        JsonPath jsonResponse = new JsonPath(response);
        String sessionId = jsonResponse.getString("session.value");

        System.out.println(sessionId);


        String updateCommentRequestBody=generateString("updateComment.json");

// Update the existing comment
        Response updateCommentResponse=given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").
                body(updateCommentRequestBody).

                when().put("/rest/api/2/issue/RAT-6/comment/10001").

                then().assertThat().statusCode(200)

                .extract().response();

         JsonPath JsonupdateCommentResponse=new JsonPath(updateCommentResponse.asString());

         System.out.println("After Updating the comment of the existing jira is --"+ JsonupdateCommentResponse.prettyPrint());


         // delete the comment.


               given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").

                when().delete("/rest/api/2/issue/RAT-6/comment/10001").

                then().assertThat().statusCode(204)

                .extract().response();


    }

    public static String generateString(String filename) throws IOException {
        String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
        return new String (Files.readAllBytes(Paths.get(filePath)));
    }
}
