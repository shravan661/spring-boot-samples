package account.web;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import account.Application;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Basic integration tests for demo application.
 *
 * @author DISID
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
// {"server.port=0" plus "management.port=0"} is the same than randomPort = true
@WebIntegrationTest({"PATH_REPO = /tmp/", "server.port=0", "management.port=0"})
@DirtiesContext
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void testHome() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate()
				.getForEntity("http://localhost:" + port + "/accounts?number=1111", Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertEquals("1111", body.get("number"));
        assertNotNull(body.get("id"));
        assertNotNull(body.get("creditCardNumber"));
	}

}
