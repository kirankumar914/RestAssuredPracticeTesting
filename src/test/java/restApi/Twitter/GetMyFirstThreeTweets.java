package restApi.Twitter;

import static io.restassured.RestAssured.given;

import java.util.SimpleTimeZone;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetMyFirstThreeTweets {

    private String consumerKey="rvIq5fM6ZiylbyM4cvyUQ6bOT";
    private String ConsumerSecretKey="UU3eFmWrL4WQdh4X4gUIxAkfzT5Mw2SLKPgq2IJwpLTEFNSLb0";
    private String accessToken="3188635759-BpbxHkCriFXOkDTMTonZYdzmvVddWIP3Vetohad";
    private String acessSecretToken="p99QAptcSA2Jm8aUmBvsLuK2NdBO3ZjyTZivBhht0C77k";


    @Test
    public void getMyTweets()
    {
        RestAssured.baseURI = "https://api.twitter.com";

        Response res=given().auth()
                .oauth(consumerKey,ConsumerSecretKey,accessToken,acessSecretToken).
                param("conut",3)

                .when().get("/1.1/statuses/user_timeline.json")
                .then().assertThat().statusCode(200).log().all().
                        extract().response();
    }

}
