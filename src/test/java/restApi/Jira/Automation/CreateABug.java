package restApi.Jira.Automation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import org.testng.annotations.Test;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class CreateABug {

    @Test
    public void verifyResponse() throws IOException{

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

        given().contentType(ContentType.JSON).
                header("cookie", "JSESSIONID=" + sessionId+"").
                body(requestBodyForBugCreation).

                when().
                post("/rest/api/2/issue").
                then().assertThat().statusCode(201).log().all();


        System.out.println(sessionId);


    }
    public static String generateString(String filename) throws IOException {
        String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
        return new String (Files.readAllBytes(Paths.get(filePath)));
    }
}
