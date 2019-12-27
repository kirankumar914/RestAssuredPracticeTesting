package restApi.Jira.Automation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetTheBugNumber {

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

        Response projectRes=given().header("cookie", "JSESSIONID=" + sessionId+"").

//                when().get("/rest/api/2/project").
        when().get("/rest/api/2/issue/RAT-5").

                then().assertThat().statusCode(200).extract().response();


        String projectResponse=projectRes.asString();

        JsonPath jsonProjectResponse=new JsonPath(projectResponse);

        System.out.println("list of the jiras from the project are--"+ jsonProjectResponse.prettyPrint());

    }



    public static String generateString(String filename) throws IOException {
        String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
        return new String (Files.readAllBytes(Paths.get(filePath)));
    }
}
