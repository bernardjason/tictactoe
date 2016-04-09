package bjason.swagger.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static springfox.documentation.builders.PathSelectors.regex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import bjason.swagger.CommonSwagger;
import bjason.swagger.entity.OauthToken;
import bjason.swagger.entity.TicTacToeEntity;


/*
 * 
 * 
 * SEE https://tools.ietf.org/html/rfc6749#section-4.2.1
 * 
 * 
 * 
 */

@RestController
@Api(value = "/oauth", description = "oauth operations")
@RequestMapping(value = "/oauth", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class OauthController extends CommonSwagger {
 
	private static final long TOKEN_LENGTH = 120000;

	Log log = LogFactory.getLog(this.getClass());

	ConcurrentHashMap<String,Long> access_tokens = new ConcurrentHashMap<String,Long>();
	
	
	@Bean
    public Docket oauthApiBean() {
		
		
        Docket d = new Docket(DocumentationType.SWAGGER_2)
                .groupName("oauth")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/oauth/.*"))
                .build();
        


        return d;
    }
	
	
	

    @ApiOperation(value = "postAddName", nickname = "postAddName")
    @RequestMapping(method = RequestMethod.POST, path="/grantgame", produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "User's name", required = false, dataType = "string", paramType = "query", defaultValue="Jason")
      })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OauthToken.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbpkden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public OauthToken postAddName(@RequestParam(value="name", defaultValue="gameForBernard") String name) {
       
    	OauthToken oauthToken = new OauthToken();
    	
    	long now=System.currentTimeMillis();
    	String accessToken = "duff"+now+name;
    	long expires=TOKEN_LENGTH+now;
    	access_tokens.put(accessToken,expires);
    	oauthToken.setAccess_token(accessToken);
    	oauthToken.setToken_type("Bearer");
    	oauthToken.setExpires_in(""+expires);
    	    	
    	
        return oauthToken;
    }
    
    @ApiOperation(value = "get", nickname = "get")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TicTacToeEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "ForbticTacToeIdden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.GET, path="/{access_token}", produces = "application/json")    
    public void get(@PathVariable(value="access_token") String checkAccess_token) {

    	log.info("tokens "+access_tokens);
    	long now=System.currentTimeMillis();

    	for(String k : access_tokens.keySet()) {
    		if ( access_tokens.get(k) < now ) {
    			access_tokens.remove(k);
    			log.info("Expired "+k);
    		}
    	}
    	if ( access_tokens.containsKey(checkAccess_token) == false ) {
    		throw new RuntimeException("token not valid");
    	}
    		
    	return;
    }

    @ApiOperation(value = "delete", nickname = "delete token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TicTacToeEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "ForbticTacToeIdden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.DELETE, path="/{access_token}", produces = "application/json")    
    public void delete(@PathVariable(value="access_token") String checkAccess_token) {
    	
    	access_tokens.remove(checkAccess_token) ;
    	
    		
    	return;
    }
}
