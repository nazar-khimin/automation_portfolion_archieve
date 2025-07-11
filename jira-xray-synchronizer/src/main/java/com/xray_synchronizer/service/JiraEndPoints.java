package com.xray_synchronizer.service;

import static com.xray_synchronizer.utils.AppProps.getProperty;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JiraEndPoints {
  public static final String JIRA_SERVER = getProperty("jira_server");
  public static final String TEST_SET = "/testexec/{testExecKey}/test";
  public static final String TEST_RUN = "/testrun/{runId}/status?status={status}";
}
