package br.com.dealership.janczakcars.cucumber.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/product.feature",
        glue = {"br.com.dealership.janczakcars.cucumber.steps.product", "br.com.dealership.janczakcars.cucumber.config"},
        plugin = {"pretty", "json:target/cucumber-report-product.json"},
        monochrome = true
)

public class ProductControllerTest {
}