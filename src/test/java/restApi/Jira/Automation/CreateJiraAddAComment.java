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

public class CreateJiraAddAComment {

    @Test
    public void verifyResponse() throws IOException {

        //payload in string format for POST request
        String requestBodyForLogin=generateString("jiralogin.json");


        RestAssured.baseURI = "http://localhost:8080";
        Response res =given().contentType(ContentType.JSON).
                body(requestBodyForLogin).

                when().
                post("/rest/auth/1/session").
                then().assertThat().statusCode(200).

                extract().response();

        String response=res.asString();

        JsonPath Jsonresponse=new JsonPath(response);
        String sessionId=Jsonresponse.getString("session.value");

        String requestBodyForBugCreation=generateString("createBug.json");

        Response idCreationRes=given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").
                body(requestBodyForBugCreation).

                when().
                post("/rest/api/2/issue").
                then().assertThat().statusCode(201).extract().response();

        JsonPath jsonidCreationResponse= new JsonPath(idCreationRes.asString());
        String issueId=jsonidCreationResponse.getString("id");

        String addCommentRequestBody=generateString("addAComment.json");

        Response addCommentRes=given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").
                body(addCommentRequestBody).

                when().post("/rest/api/2/issue/"+ issueId+ "/comment").

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
