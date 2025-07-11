# Xray Synchronizer
JIRA XRay client that allows update test run status of Test Execution issue.

In application.properties find below properties, replace them and run tests in **XRayTest** class in path **xray_synchronizer\src\test\java\com\xray_synchronizer**

```
jira_test_execution_key=EXEC_KEY
jira_issue_key=ISSUE_KEY
jira_server=https://{jira_server}/jira/rest/raven/1.0/api/
jira_username=username
jira_password=password
```

Manual API calls that do this cliet:

1. Retrieve information of test runs that are in test execution

`curl -H "Content-Type: application/json" -X GET -u <jira_user_username>:<password> https://<JIRA_PORTAL>/jira/rest/raven/1.0/api/testexec/<TEST_EXECUTION_ISSUE-KEY>/test`

Sample Response
```
[
         {
            "id": 10444291,
            "status": "TODO",
            "key": "<TEST_RUN_ISSUE_KEY>",
            "rank": 1
          }
]
```

2. Do update of Test Run using above Test Run Id

`curl -H "Content-Type: application/json" -X PUT -u <jira_user_username>:<password> https://<JIRA_PORTAL>/jira/rest/raven/1.0/api/testrun/10368224/status?status=PASS`

Expected Response 200

XRay API reference:
https://docs.getxray.app/display/XRAY/REST+API
