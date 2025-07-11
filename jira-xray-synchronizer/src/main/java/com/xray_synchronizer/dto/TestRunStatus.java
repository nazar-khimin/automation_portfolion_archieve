package com.xray_synchronizer.dto;

import static io.cucumber.java.Status.PASSED;

import io.cucumber.java.Status;

import java.util.List;

public enum TestRunStatus {
  PASS, FAIL;

  public static String getAggregatedStatusOfScenarioListExecution(List<Status> scenarioStatusList) {
    long countOfFailedScenarios = scenarioStatusList.stream()
        .filter(scenarioStatus -> !scenarioStatus.equals(PASSED))
        .count();

    String resultStatus;
    if (countOfFailedScenarios > 0) {
      resultStatus =  FAIL.name();
    } else {
      resultStatus = PASS.name();
    }
    return resultStatus;
  }
}
