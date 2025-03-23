package br.com.dealership.janczakcars.cucumber.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/order.feature",
        glue = {"br.com.dealership.janczakcars.cucumber.steps.order", "br.com.dealership.janczakcars.cucumber.config"},
        plugin = {"pretty", "json:target/cucumber-report-order.json"},
        monochrome = true
)
public class OrderControllerTest {
}
