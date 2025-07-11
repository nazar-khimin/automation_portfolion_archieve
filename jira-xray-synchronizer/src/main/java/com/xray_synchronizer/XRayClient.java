package com.xray_synchronizer;

import static io.restassured.RestAssured.given;

import com.xray_synchronizer.dto.TestRunDto;
import com.xray_synchronizer.dto.TestRunStatus;
import com.xray_synchronizer.service.XRayService;
import io.cucumber.java.Status;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class XRayClient {
  private final XRayService xRayService = new XRayService();
  private static final Logger log = LogManager.getLogger(XRayClient.class);

  public void batchTestRunStatusUpdate(String testSetKey) {
    Map<String, List<Status>> scenarioMap = TestResultHolder.getScenarioMap();

    log.info(scenarioMap);
    List<TestRunDto> runsOfTestExecution = xRayService.getRunsOfTestExecution(testSetKey);
    scenarioMap.forEach((issueKey, scenarioStatusList) -> {
      String status = TestRunStatus.getAggregatedStatusOfScenarioListExecution(scenarioStatusList);
      updateTestRunStatus(runsOfTestExecution, issueKey, status);
    });
  }

  private void updateTestRunStatus(List<TestRunDto> runsOfTestExecution, String issueKey,
      String status) {
    Optional<TestRunDto> testRunDto = filterTestRunByKey(runsOfTestExecution, issueKey);
    if (testRunDto.isPresent()) {
      xRayService.updateTestRunStatus(testRunDto.get().getId(), status);
    } else {
      String message = String.format("Test run by key %s not found", issueKey);
      log.error(message);
    }
  }

  Optional<TestRunDto> filterTestRunByKey(List<TestRunDto> testRunDtoList,
      String issueKey) {
    return testRunDtoList.stream()
        .filter(testRunDto -> testRunDto.getKey().equals(issueKey)).findAny();
  }
}
