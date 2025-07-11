package com.xray_synchronizer;

import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestResultHolder {

  private static final Map<String, List<Status>> scenarioMap = new HashMap<>();
  private static TestResultHolder instance;

  public static TestResultHolder getInstance() {
    if (instance == null) {
      instance = new TestResultHolder();
    }
    return instance;
  }

  public void addToScenarioMap(String issueKey, Status status) {
    if (scenarioMap.containsKey(issueKey)) {
      scenarioMap.get(issueKey).add(status);
    } else {
      scenarioMap.put(issueKey, new ArrayList<>(Collections.singleton(status)));
    }
  }

  public String getIssueKeyFromScenario(Scenario scenario) {
    String issueTag = scenario.getSourceTagNames()
        .stream()
        .filter(s -> s.startsWith("@issue"))
        .findFirst().orElseThrow(() -> new RuntimeException(
            "Issue with name " + scenario.getName() + "not found"));
    return StringUtils.substringAfter(issueTag, ":");
  }

  public static Map<String, List<Status>> getScenarioMap() {
    return scenarioMap;
  }
}
