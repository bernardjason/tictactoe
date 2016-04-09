package bjason.swagger.jsf;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty","html:build/reports/cucumber/jsf"},
glue={"cucumber.api.spring","classpath:bjason.swagger.jsf"}
)
public class CucumberJsfTest {

}
