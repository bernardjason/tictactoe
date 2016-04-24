# tictactoe
a springfox swagger implementation of oauth using spring(with hibernate/h2), jsf, cucumber and selenium

You can see this running now on heroku on a free Dyno, so it may be a little slow to start and in the unlikely event of many calls 
it has to be idle for 6 hours in every 24 hours. (see https://devcenter.heroku.com/articles/dyno-types)

swagger 
	http://bjason-tictactoe.herokuapp.com/swagger/swagger-ui.html

or tictactoe game at 

	http://bjason-tictactoe.herokuapp.com/swagger/home.xhtml

Quick note, the tests use Selenium and are set to use Firefox and Chrome. See file JsfFeatureSteps.java in package bjason.swagger.jsf. Also note that to test Chrome at least I've had to include copies of chromedrive for Linux.  You'll need copies for other platforms from here https://sites.google.com/a/chromium.org/chromedriver/

The aim of the project was to look at a more recent copy of swagger than I've used before. Then to use JSF rather than the usual JSP. Finally I wanted to see the oauth feature built into swagger working. So the implementation features are built around my tool choice and what I wanted to see rather than best way to do it. Biggest "choice" being heavy use of javascript in JSF pages to call rest resources when I think I'd have done that on the backend in this case.

What does the game do? It just allows 2 players to have a game of tic tac toe. It doesn't check who wins, so it isnt finished. it just shows the use of a simple rest api .

to run up to play tictactoe as well as see the swagger gui

mvnw clean spring-boot:run -Dspring.profiles.active=local

then visit http://127.0.0.1:8080/swagger/swagger-ui.html

or to play the game 

http://127.0.0.1:8080/swagger/home.xhtml

Swagger requires a recent version of the browser, be that Chrome, Firefox or IE. The oauth functionality uses Object.assign which is added in ECMAScript 6th edition.

I've included the swagger-ui here, as I've tested this works, at least for my 2 browsers, Chrome and Firefox.

To run selenium tests

mvnw package -Dspring.profiles.active=local

Note the use of spring profile, the default profile is set for heroku using postgress db

<img src="/screen/Screenshot1-Swagger%20UI%20-%20Google%20Chrome.png" width="40%"/>
<img src="/screen/Screenshot2-Swagger%20UI%20-%20Google%20Chrome.png" width="40%"/>
<img src="/screen/Screenshot3-Swagger%20UI%20-%20Google%20Chrome.png" width="40%"/>
<img src="/screen/Screenshot4-authorization%20page%20-%20Google%20Chrome.png" width="40%"/>
<img src="/screen/Screenshot5-authorization%20page%20-%20Google%20Chrome.png" width="40%"/>
<img src="/screen/Screenshot6-Swagger%20UI%20-%20Google%20Chrome.png" width="40%"/>

## selenium
test class JsfFeatureSteps.java sets up the use of 2 browsers. If you aren't using Chrome or Firefox, or are a 
non Linux 64 bit please visit http://www.seleniumhq.org/download/ to see links to the various third party drivers

you may need to change this entry if using windows
System.setProperty("webdriver.chrome.driver",
				"./chromedriver." + System.getProperty("os.name") + "."
						+ System.getProperty("sun.arch.data.model"));
						
to simple reference the drive explicitly, e.g. System.setProperty("webdriver.chrome.driver","chromedrive.exe");

the test is setup to use 2 browsers, Chrome and Firefox. To change simply set these lines accordingly.
_chromePlayer2 = new ChromeDriver();
_firefoxPlayer1 = new FirefoxDriver();

I chose these browsers simply because they are the 2 I use for testing on my system.

##TicTacToeController.java
this file creates the actual game resources. Using lots of swagger annotations it defines the interface alongside the implementation. 
It is here that the rest api definitions have their scope's defined as well. There's a lot of annotations but the entire api is configured in one place,
then the actual code just implements the api functionality. The authorisation is specified here but actually implemented in CheckBearer.java.

##CheckBearer.java
this class extends HandlerInterceptorAdapter and implements the check to ensure the token is valid. Massive cheat here as I use a 
spring bean rather than call the check externally via a rest call or similar.
CheckBearer is added as an intercepter in HelloworldApplication..java

##OauthController.java
this class implements the rest api's to support oauth operations. It supports the GET that the tictactoe web app makes to get a token, along 
with the POST create of a token that authorization/authorization_server.html uses before it redirects back to the calling tictactoe web app

##authorization/authorization_server.html
this page is simply the simple page that the oauth provider implements, here you just enter an email address that isnt validated and it will then
create a token and allow a redirect back to the app using the specified redirect_uri

##spring,h2/hibernate
the app is using h2 in memory db to store information about the game in progress. 

##HelloworldApplication.java
Sets up JSF servlet, adds a logging filter plus adds the CheckBearer for calls to the tictactoe rest resources.

##WEB-INF/   faces-config.xml and web.xml
This is needed just to keep JSF happy. Otherwise not required.

##cucumber
setup is via src/test/resources/bjason/swagger/jsf/jsf.feature, and classes CucumberJsfTest.java and JsfFeatureSteps. I've described the
scenarios for driving the GUI pages using selenium. Sorry no proper unit tests.
