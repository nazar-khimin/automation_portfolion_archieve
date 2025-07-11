package com.xray_synchronizer.service;

import static com.xray_synchronizer.utils.AppProps.getProperty;
import static io.restassured.RestAssured.given;

import com.xray_synchronizer.dto.TestRunDto;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.List;

public class XRayService {

  public static final String USER_NAME = getProperty("jira_username");
  public static final String USER_PASS = getProperty("jira_password");

  public List<TestRunDto> getRunsOfTestExecution(String testSetKey) {
    TestRunDto[] testRunDtoList = auth().when()
        .pathParam("testExecKey", testSetKey)
        .get(JiraEndPoints.TEST_SET)
        .getBody().as(TestRunDto[].class);

    return Arrays.asList(testRunDtoList);
  }


  public void updateTestRunStatus(Integer testRunId, String status) {
    auth().when()
        .pathParam("runId", testRunId)
        .pathParam("status", status)
        .put(JiraEndPoints.TEST_RUN);
  }

  private RequestSpecification auth() {
    return given()
        .baseUri(JiraEndPoints.JIRA_SERVER)
        .auth()
        .preemptive().basic(USER_NAME, USER_PASS);
  }
}
