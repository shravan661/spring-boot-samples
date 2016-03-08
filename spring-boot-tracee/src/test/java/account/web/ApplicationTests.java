package account.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import account.Application;
import io.tracee.Tracee;
import io.tracee.TraceeBackend;
import io.tracee.Utilities;

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
  
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationTests.class);

	@Value("${local.server.port}")
	private int port;
	
	
	@Test
	public void testHome() throws Exception {
		LOG.info("Start testHome()");
    // first call to accounts
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate()
				.getForEntity("http://localhost:" + port + "/accounts?number=1111", Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		// second call to accounts
		entity = new TestRestTemplate()
        .getForEntity("http://localhost:" + port + "/accounts?number=1111", Map.class);
    assertEquals(HttpStatus.OK, entity.getStatusCode());
    
		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertEquals("1111", body.get("number"));
        assertNotNull(body.get("id"));
        assertNotNull(body.get("creditCardNumber"));
    LOG.info("End testHome()");
	}
	
	@Test
  public void testHome2() throws Exception {
	  LOG.info("Start testHome2()");
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity = new TestRestTemplate()
        .getForEntity("http://localhost:" + port + "/accounts2?number=1111", Map.class);
    assertEquals(HttpStatus.OK, entity.getStatusCode());

    @SuppressWarnings("unchecked")
    Map<String, Object> body = entity.getBody();
    assertEquals("1111", body.get("number"));
        assertNotNull(body.get("id"));
        assertNotNull(body.get("creditCardNumber"));
    LOG.info("End testHome2()");
  }

}
