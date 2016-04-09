Feature: JSF
  I want to start a new game using jsf pages.

  Scenario: Start 2 browsers, player 1 is firefox and player 2 is chrome
    Given two browser
    Given player 1 a url '/home.xhtml'
    Then one page for player 1 should have been loaded
    And player 1 page should contain '<h1>tictactoe</h1>'
    And player 1 page should contain 'Enter nickname'
    When player 1 enters nick name 'bern'
    Then player 1 should be asked by oauth provider with title 'authorization page' to verify my 'Email address' 'bj@bjason.org'
    Then I will check player 1 page contains 'Hi bern, create a game or join a friend'
    And player 1 will enter a game named 'auto' and click 'createGameButton'
    Then I will check player 1 page contains 'Hi bern, match in proress'
        
    Then get player 1 game url
    Given player 2 a url '/home.xhtml'
    When player 2 enters nick name 'jason'
    Then player 2 should be asked by oauth provider with title 'authorization page' to verify my 'Email address' 'bogus@boguss.net'
    Then I will check player 2 page contains 'Hi jason, create a game or join a friend'
    And player 2 will enter url and click 'joinGameButton'
  
    And player 1 will play 'centre' square 'r1c1'
  	Then pause 1 seconds
  	And make sure player 1 'r1c1' is now an 'X'
    And make sure player 2 'r1c1' is now an 'X'
  	
    Then player 2 will play 'top right' square 'r0c2'
    Then pause 1 seconds
    And make sure player 1 'r0c2' is now an 'O'
    And make sure player 2 'r0c2' is now an 'O'
  	  
    Then i will wait 2 seconds and close browsers
