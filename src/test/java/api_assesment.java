

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import javafx.beans.binding.When;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import static com.jayway.restassured.RestAssured.*;
public class api_assesment {
    @Test
    public void verify_jobs_endpoint_is_accessible() {
        Response response = when()
                .get("http://api.dataatwork.org/v1/jobs");
        System.out.println(response.getBody());
        Assert.assertEquals(200, response.getStatusCode());

    }

    @Test
    public void verify_first_grade_job() {
        /*First Grade job Id is 26bc4486dfd0f60b3bb0d8d64e001800
          check ralated skills in first grade job where level is 4.25
          and skill type is ability
        * */
        Response response = given()
                .pathParam("id","26bc4486dfd0f60b3bb0d8d64e001800")
                .when()
                .get("http://api.dataatwork.org/v1/jobs/{id}/related_skills");

        String actualDescription = response.jsonPath().getString("skills.find {it.skill_type=='ability' && it.level==4.25} .description");

        Assert.assertEquals(actualDescription,
                "the ability to communicate information and ideas in speaking so others will understand.");
    }
}
