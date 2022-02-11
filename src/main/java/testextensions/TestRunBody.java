package testextensions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(setterPrefix = "set")
public class TestRunBody {
    private static final String TEST_RUN = String.format("RUN-%s", System.currentTimeMillis());

    private String project;
    private String testRun;
    private String testClass;
    private String testName;
    private String status;
    private String reason;
    private String executionTime;
    private String triggeredBy;

    // A valid default body to publish to elastic
    public TestRunBody setDefault() {
        return TestRunBody.builder()
                .setProject("thor")
                .setTestRun(TEST_RUN)
                .setTestClass(TestExecutionLifecycle.getTestClassName())
                .setTestName(TestExecutionLifecycle.getTestName())
                .setStatus(TestExecutionLifecycle.getTestResult())
                .setReason(TestExecutionLifecycle.getReason())
                .setExecutionTime(LocalDateTime.now().toString())
                .setTriggeredBy(System.getProperty("user.name")) //platform independent
                .build();
    }
}
