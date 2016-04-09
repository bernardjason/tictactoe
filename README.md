# tictactoe
a springfox swagger implementation of oauth using spring(with hibernate/h2), jsf, cucumber and selenium

Quick note, the tests use Selenium and are set to use Firefox and Chrome. See file JsfFeatureSteps.java in package bjason.swagger.jsf. Also note that to test Chrome at least I've had to include copies of chromedrive for Linux.  You'll need copies for other platforms from here https://sites.google.com/a/chromium.org/chromedriver/

The aim of the project was to look at a more recent copy of swagger than I've used before. Then it was to use JSF rather than my usual JSP. Finally I wanted to see the oauth feature built into swagger working. So the implementation features are built around my tool choice and what I wanted to see rather than best way to do it. Biggest "choice" being heavy use of javascript in JSF pages to call rest resources when I would have put these in the backend probably. 

What does the game do? It just allows 2 players to have a game of tic tac toe. It doesn't check who wins, so it isnt finished. it just shows the use of a simple rest api .

to run up to play tictactoe as well as see the swagger gui

mvnw clean spring-boot:run

then visit http://127.0.0.1:8080/swagger/swagger-ui.html

or to play the game 

http://127.0.0.1:8080/swagger/home.xhtml

More notes to follow.
