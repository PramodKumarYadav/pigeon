package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/pages",
        glue = {"cucumber/stepDefinitions"},
        plugin = {"pretty"}
)
public class TestRunnerHomePage {
}
