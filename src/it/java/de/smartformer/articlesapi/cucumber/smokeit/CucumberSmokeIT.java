package de.smartformer.articlesapi.cucumber.smokeit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/it/resources/features/smokeit",
        plugin = {"pretty", "html:target/cucumber/smokeit"},
        extraGlue = "de.smartformer.articlesapi.cucumber.configuration")
public class CucumberSmokeIT {
}
