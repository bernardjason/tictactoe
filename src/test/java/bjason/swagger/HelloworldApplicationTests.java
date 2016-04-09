package bjason.swagger;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HelloworldApplication.class})
@WebAppConfiguration
public class HelloworldApplicationTests {


	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
		
	@Before
	public void setup() throws MalformedURLException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	
	}
		
		@Test
		public void getSwaggerHomePage() throws Exception {
			MockHttpServletRequestBuilder createMessage = get("/swagger-ui.html");
			MvcResult result = mockMvc.perform(createMessage).andDo(print()).andExpect(status().isOk())
					.andExpect(content().string(new BaseMatcher<String>() {
						@Override
						public boolean matches(Object item) {
							return ((String)item).contains("<title>Swagger UI</title>");
						}
						@Override
						public void describeTo(Description description) {
							System.out.println("Failed"+description);	
						}
					})).andReturn();
			//					 .andExpect(model().attribute("message", "<title>Swagger UI</title>"))

		}
		
}
