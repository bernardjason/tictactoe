package bjason.swagger.controllers;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.regex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import bjason.swagger.CommonSwagger;
import bjason.swagger.entity.Game;
import bjason.swagger.entity.GameEntity;
import bjason.swagger.entity.Players;
import bjason.swagger.entity.TicTacToe;
import bjason.swagger.entity.TicTacToeEntity;


@RestController
@Api(value = "/tictactoe", description = "noughts and crosses or tic tac toe game")
@RequestMapping(value = "/tictactoe", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
public class TicTacToeController extends CommonSwagger {
 
	@Autowired
	TicTacToe ticTacToe;

	@Autowired
	Game game;
	
	@Bean
    public Docket ticTacToeApiBean() {
		
		
        Docket d = new Docket(DocumentationType.SWAGGER_2)
                .groupName("tictactoe")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/tictactoe/.*"))
                .build().securitySchemes(newArrayList(oauth()))
                .securityContexts(newArrayList(securityContext()));
        


        return d;
    }
	
	

    @Bean
    SecurityContext securityContext() {
        AuthorizationScope playScope = new AuthorizationScope("play:all", "play tic tac toe");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = playScope;
        SecurityReference securityReference = SecurityReference.builder()
                .reference("tic tac toe authentication")
                .scopes(scopes)
                .build();

        return SecurityContext.builder()
                .securityReferences(newArrayList(securityReference))
                .forPaths(ant("/tactactoe/.*"))
                .build();
    }

    @Bean
    SecurityScheme oauth() {
        return new OAuthBuilder()
                .name(TicTacToeAuth)
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("api_key", "api_key", "header");
    }

    List<AuthorizationScope> scopes() {
        return newArrayList(
                new AuthorizationScope(Scope, ScopeDescription));
    }

    List<GrantType> grantTypes() {
        GrantType grantType = new ImplicitGrantBuilder()
                .loginEndpoint(new LoginEndpoint("http://127.0.0.1:8080/swagger/authorization/authorization_server.html"))
                .build();
        return newArrayList(grantType);
    }

    private static final String Scope = "play:all";
    private static final String ScopeDescription = "play tic tac toe";

    private static final String TicTacToeAuth = "tictactoe_auth";
    private static final String client_id = "tictactoe-id";
    private static final String client_secret = "tictactoe-oxoxox123";
    private static final String realm = "tictactoe";
    private static final String appName ="tictactoe";
    private static final String apticTacToeIdeyValue ="tictactoe";
    private static final String apiKeyName = "auth";
    private static final String scopeSeparator=",";
    
    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(client_id, client_secret, realm, appName, apticTacToeIdeyValue, ApiKeyVehicle.HEADER, apiKeyName,scopeSeparator);
    }
   

    @ApiOperation(value = "createGame", nickname = "createANewGame",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @RequestMapping(method = RequestMethod.POST, path="/", produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "gameName", value = "name of the game to create", required = false, dataType = "string", paramType = "query", defaultValue="Jason")

      })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TicTacToeEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public TicTacToeEntity createGame(@RequestParam(value="gameName", defaultValue="World") String gameName
    		) {
    	
    	checkAuth();
        TicTacToeEntity entity = new TicTacToeEntity();
        entity.setGameName(gameName);
        ArrayList<String> players = entity.getPlayers();
        players.add(null);
        players.add(null);
        log.info("Set players to an empty array, size is "+players.size());
        entity.setPlayers(players);
        TicTacToeEntity saved = ticTacToe.save(entity);
        saved.add(linkTo(methodOn(TicTacToeController.class).get(entity.getTicTacToeId())).withSelfRel());

        return saved;
    }
    
    
    
   

	@ApiOperation(value = "get", nickname = "get",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TicTacToeEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "ForbticTacToeIdden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.GET, path="/{ticTacToeId}", produces = "application/json")    
    public TicTacToeEntity get(@PathVariable(value="ticTacToeId") long ticTacToeId) {
    	checkAuth();

    	TicTacToeEntity entity = ticTacToe.findOne(ticTacToeId);
    	
    	entity.add(linkTo(methodOn(TicTacToeController.class).get(ticTacToeId)).withSelfRel());
       
       return entity;
    }
    
    
    
    @ApiOperation(value = "updatePlayers", nickname = "updatePlayers",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @RequestMapping(method = RequestMethod.PUT, path="/{ticTacToeId}/players", produces = "application/json")
   
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TicTacToeEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 412, message = "*** PRECON CHECK ALREADY UPDATED **"),

            @ApiResponse(code = 500, message = "Failure")})
    public TicTacToeEntity updatePlayers(@PathVariable(value="ticTacToeId") long ticTacToeId,
    		@RequestBody Players players) {
    	
    	checkAuth();

    	
    	// TODO add logic to make sure cannot reset players once set. honour If Match header for 412 error
    	
    	log.info("GOT IN "+players);
    	
    	TicTacToeEntity entity = ticTacToe.findOne(ticTacToeId);
    	
    	ArrayList<String> toAdd = new ArrayList<String>();

    	ArrayList<String> currentPlayers = entity.getPlayers();
    	if ( players.getPlayers().get(0) != null && currentPlayers.get(0) != null ) {
    		throw new RuntimeException("player 1 already set "+currentPlayers.get(0)+"  "+players.getPlayers().get(0));
    	} else {
    		if ( players.getPlayers().get(0) != null ) toAdd.add(players.getPlayers().get(0)); 
    		else toAdd.add(currentPlayers.get(0));
    	}
    	if ( players.getPlayers().get(1) != null && currentPlayers.get(1) != null ) {
    		throw new RuntimeException("player 2 already set "+currentPlayers.get(1)+"  "+players.getPlayers().get(1));
    	} else {
    		if ( players.getPlayers().get(1) != null ) toAdd.add(players.getPlayers().get(1)); 
    		else toAdd.add(currentPlayers.get(1));
    	}

    	    	
		entity.setPlayers(toAdd );
        TicTacToeEntity saved = ticTacToe.save(entity);
        saved.add(linkTo(methodOn(TicTacToeController.class).get(entity.getTicTacToeId())).withSelfRel());

        return saved;
    }
    

    @ApiOperation(value = "createMatch", nickname = "createAMatch",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @RequestMapping(method = RequestMethod.POST, path="/{ticTacToeId}/game", produces = "application/json")
    @ApiImplicitParams({
      })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GameEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public GameEntity createMatch(@PathVariable(value="ticTacToeId") long ticTacToeId) {
    	checkAuth();

        TicTacToeEntity ticTacToeEntity = ticTacToe.findOne(ticTacToeId);

    	GameEntity entity = new GameEntity();
    	entity.setTicTacToeEntity(ticTacToeEntity);
        GameEntity saved = game.save(entity);
        saved.add(linkTo(methodOn(TicTacToeController.class).getMatch(ticTacToeId,entity.getGameId())).withSelfRel());
        
        ticTacToeEntity.setGameEntity(entity);
        ticTacToe.save(ticTacToeEntity);
        return saved;
    }
    
    @ApiOperation(value = "getMatch", nickname = "getMatch",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GameEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "ForbticTacToeIdden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.GET, path="/{ticTacToeId}/game/{gameId}", produces = "application/json")    
    public GameEntity getMatch(
    		@PathVariable( value="ticTacToeId") long ticTacToeId,
    		@PathVariable( value="gameId") long gameId) {
    	checkAuth();

    	GameEntity entity = game.findOne(gameId);
    	
    	entity.add(linkTo(methodOn(TicTacToeController.class).getMatch(ticTacToeId,gameId)).withSelfRel());
       
       return entity;
    }
    
    @ApiOperation(value = "putMatch", nickname = "update Match",
    		authorizations = {
            @Authorization(value = TicTacToeAuth, scopes = {
                    @io.swagger.annotations.AuthorizationScope(scope = Scope, description = ScopeDescription)
            })})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GameEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "ForbticTacToeIdden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.PUT, path="/{ticTacToeId}/game/{gameId}", produces = "application/json")    
    public GameEntity putMatch(
    		@PathVariable( value="ticTacToeId") long ticTacToeId,
    		@PathVariable( value="gameId") long gameId,
    		@RequestBody GameEntity updatedEntity) {
    	
    	checkAuth();
    	
    	log.info("Input "+updatedEntity.toString());

    	
    	GameEntity entity ;
    	updatedEntity.setGameId(gameId);
    	game.save(updatedEntity);
    	entity = game.findOne(gameId);

    	entity.add(linkTo(methodOn(TicTacToeController.class).getMatch(ticTacToeId,gameId)).withSelfRel());
       
    	return entity;
    }
    
    private void checkAuth() {
    }
    /*
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
    	String authorization=request.getHeader("authorization");

    	
    	log.info("check "+authorization);
    	if ( authorization == null)     		{
    		throw new NotAuthenticated(null);
    	}

    	String[] checkAccess_token= authorization.split("Bearer ");
    	if ( checkAccess_token.length != 2 ) {
    		throw new NotAuthenticated(authorization);
    	}
    	
    	try{
    		oc.get(checkAccess_token[1]);
    	} catch (RuntimeException re) {
    		throw new NotAuthenticated(authorization);
    	}
	}
*/

   
}

/*
class NotAuthenticated extends RuntimeException {

	public NotAuthenticated(String auth) {
		super("Not authenticated, authorization was "+auth);
	}
}
@ControllerAdvice
class TicTacToeControllerAdvice {

	@ResponseBody
	@ExceptionHandler(NotAuthenticated.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	VndErrors userNotFoundExceptionHandler(NotAuthenticated e) {
		return new VndErrors("ERROR!", e.getMessage());
	}
}
*/