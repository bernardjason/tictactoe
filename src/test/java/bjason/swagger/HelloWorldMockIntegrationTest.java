package bjason.swagger;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
 import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
 import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = HelloworldApplication.class)
@ContextConfiguration(classes = {HelloworldApplication.class}, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class HelloWorldMockIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	 @Value("${local.server.port}")
	 private int port;
	 
		
	@Before
	public void setup() throws MalformedURLException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	
	}



	@Test
	public void getSwaggerHomePage() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		
	ResponseEntity<String> response = new TestRestTemplate().exchange("http://localhost:" + this.port+"/swagger/swagger-ui.html",
			HttpMethod.GET, entity, String.class);
			 
	assertEquals(response.getStatusCode(),HttpStatus.OK);
	assertThat(response.getBody(),containsString("<title>Swagger UI</title>"));
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody());
	}
	
	@Test
	public void getTicTacToeHomeJSF() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		
	ResponseEntity<String> response = new TestRestTemplate().exchange("http://localhost:" + this.port+"/swagger/home.xhtml",
			HttpMethod.GET, entity, String.class);
			 
	assertEquals(response.getStatusCode(),HttpStatus.OK);
	assertThat(response.getBody(),containsString("<h1>tictactoe</h1>"));
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody());
	}

}