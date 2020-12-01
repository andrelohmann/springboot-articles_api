package de.smartformer.articlesapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // override application.yml with application-test.yml
class ArticlesApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
