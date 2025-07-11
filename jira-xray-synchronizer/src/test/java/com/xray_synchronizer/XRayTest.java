package com.xray_synchronizer;

import static com.xray_synchronizer.utils.AppProps.getProperty;

import com.xray_synchronizer.dto.TestRunDto;
import com.xray_synchronizer.dto.TestRunStatus;
import com.xray_synchronizer.service.XRayService;
import io.cucumber.java.Status;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class XRayTest {

  XRayClient xRayClient = new XRayClient();
  TestResultHolder testResultHolder = TestResultHolder.getInstance();
  private final XRayService xRayService = new XRayService();
  private static final String ISSUE_KEY = getProperty("jira_issue_key");
  private static final String TEST_EXEC_KEY = getProperty("jira_test_execution_key");

  @Before
  public void setUp(){
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }


  @Test
  public void testFailTestRunStatusUpdate() {
    testResultHolder.addToScenarioMap(ISSUE_KEY, Status.FAILED);
    testResultHolder.addToScenarioMap(ISSUE_KEY, Status.PASSED);

    TestRunDto testRunDto = updateStatusAndGetTestRun();

    TestRunStatus expectedStatus = TestRunStatus.FAIL;
    Assert.assertEquals("Status of issue should be FAIL", expectedStatus.name(), testRunDto.getStatus());
  }

  @Test
  public void testPassTestRunStatusUpdate() {
    testResultHolder.addToScenarioMap(ISSUE_KEY, Status.PASSED);
    TestRunDto testRunDto = updateStatusAndGetTestRun();

    TestRunStatus expectedStatus = TestRunStatus.PASS;
    Assert.assertEquals("Status of issue should be PASS", expectedStatus.name(), testRunDto.getStatus());
  }

  private TestRunDto updateStatusAndGetTestRun() {
    xRayClient.batchTestRunStatusUpdate(TEST_EXEC_KEY);
    List<TestRunDto> runsOfTestExecution = xRayService.getRunsOfTestExecution(TEST_EXEC_KEY);
    return xRayClient.filterTestRunByKey(runsOfTestExecution, XRayTest.ISSUE_KEY).get();
  }

  @Test
  public void testStatusUpdateIncorrectIssueKey() {
    testResultHolder.addToScenarioMap("INCORRECT_ISSUE", Status.PASSED);

    xRayClient.batchTestRunStatusUpdate(TEST_EXEC_KEY);
    // expect to have ERROR com.xray_synchronizer.XRayClient - Test run by key INCORRECT_ISSUE not found log
  }
}
