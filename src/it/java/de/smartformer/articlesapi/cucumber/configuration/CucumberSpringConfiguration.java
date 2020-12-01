package de.smartformer.articlesapi.cucumber.configuration;

import de.smartformer.articlesapi.ArticlesApiApplication;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class to use spring application context while running cucumber
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ArticlesApiApplication.class, loader = SpringBootContextLoader.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberSpringConfiguration.class);

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before
    public void setUp() {
        LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
