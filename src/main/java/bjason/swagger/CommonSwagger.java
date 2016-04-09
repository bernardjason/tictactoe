package bjason.swagger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.hateoas.ResourceSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

public class CommonSwagger  {
	
	public Log log = LogFactory.getLog(this.getClass());
	
	protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with Swagger")
                .description("Spring REST Sample with Swagger")
                .termsOfServiceUrl("http://springfox.io")
                .contact("Bernard Jason")
                .license("Apache License Version 2.0")
                .version("2.0")
                .build();
    }
}
