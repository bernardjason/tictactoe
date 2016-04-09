package bjason.swagger.jsf;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import bjason.swagger.HelloworldApplication;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = { HelloworldApplication.class }, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest("server.port:0")
public class JsfFeatureSteps {

	@Value("${local.server.port}")
	private int port;

	public WebDriver _firefoxPlayer1;
	public WebDriver _chromePlayer2;

	public WebDriver[] players = new WebDriver[3];
	String gameUrl;

	public static String root = "/swagger";

	@Given("^two browser$")
	public void startTwoBrowsers() {

		// https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
		System.setProperty("webdriver.chrome.driver",
				"./chromedriver." + System.getProperty("os.name") + "."
						+ System.getProperty("sun.arch.data.model"));
		_chromePlayer2 = new ChromeDriver();
		_firefoxPlayer1 = new FirefoxDriver();

		_firefoxPlayer1.manage().window().setSize(new Dimension(1024, 700));
		_firefoxPlayer1.manage().window().setPosition(new Point(0, 0));

		_chromePlayer2.manage().window().setSize(new Dimension(1024, 700));
		_chromePlayer2.manage().window().setPosition(new Point(600, 400));

		players[1] = _firefoxPlayer1;
		players[2] = _chromePlayer2;
		
		pause();

	}

	@Given("^player (\\d+) a url '(.+)'$")
	public void player_a_url_home_xhtml(int player, String url)
			throws Throwable {
		players[player].get("http://localhost:" + this.port + root + url);

		Assert.assertTrue(players[player].getPageSource().contains("tictactoe"));
		pause();
	}

	@Then("one page for player (\\d+) should have been loaded")
	public void onePageLoaded(int player) {

		System.out.println("one page loaded");
		Assert.assertEquals("tictactoe", players[player].getTitle());

	}

	@And("player (\\d+) page should contain '(.+)'")
	public void pageShouldContain(int player, String text) {
		System.out.println("Page contains " + text);
		Assert.assertTrue(players[player].getPageSource().contains(text));
		pause();

	}

	@When("player (\\d+) enters nick name '(.+)'")
	public void enterMyNickName(int player, String nickName) {
		System.out.println("Nick name is " + nickName);

		WebElement element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//input[contains(@id,'nickName')]"));

		element.click();
		element.sendKeys(nickName);

		element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//input[@type='submit']"));

		pause();
		element.click();

	}

	@Then("^player (\\d+) should be asked by oauth provider with title '(.+)' to verify my '(.+)' '(.+)'$")
	public void enterMyEmailAddress(int player, String title, String text,
			String emailAddress) {

		System.out.println(text + " is " + emailAddress);
		Assert.assertEquals(title, players[player].getTitle());

		WebElement element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//h4[@class='form-signin-heading']"));
		System.out.println("XXX" + element.getText());

		element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//input[@id='inputEmail']"));
		element.click();
		element.sendKeys(emailAddress);

		pause();
		element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//button[@id='grant']"));
		element.click();

	}

	@Then("^I will check player (\\d+) page contains '(.+)'$")
	public void i_will_check_page_is_contains_Hi_bern_create_a_game_or_join_a_friend(
			int player, String contains) throws Throwable {

		Assert.assertTrue(players[player].getPageSource().contains(contains));
		pause();

	}

	@Then("^player (\\d+) will enter a game named '(.+)' and click '(.+)'$")
	public void i_will_enter_a_game_named_auto_and_click_create_game(
			int player, String gameName, String button) throws Throwable {

		WebElement element = players[player]
				.switchTo()
				.activeElement()
				.findElement(
						By.xpath("//input[contains(@id,'createGameComponent:gameName')]"));

		element.click();
		element.sendKeys(gameName);

		element = players[player]
				.switchTo()
				.activeElement()
				.findElement(
						By.xpath("//button[contains(@id,'" + button + "')]"));

		pause();
		element.click();

	}

	@Then("^player (\\d+) will play '(.+)' square '(.+)'$")
	public void play_centre_square_r_c(int player, String english, String id)
			throws Throwable {
		WebElement element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//button[@id='" + id + "']"));
		element.click();
		pause();

	}

	@And("^make sure player (\\d+) '(.+)' is now an '(.+)'$")
	public void and_make_sure(int player, String id, String is)
			throws Throwable {
		WebElement element = null;
		element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//button[@id='" + id + "']"));
		
		for (int i = 0; i < 8 && element.getText().contains(is)  == false ; i++) {
			element = players[player].switchTo().activeElement()
					.findElement(By.xpath("//button[@id='" + id + "']"));
			Thread.sleep(500);
		}
		
		Assert.assertEquals(element.getText(), is);
		pause();
	}

	@Then("^get player (\\d+) game url$")
	public void scrape_url(int player) throws Throwable {
		WebElement element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//input[@id='gameurltoshare']"));
		gameUrl = element.getAttribute("value");
		pause();

	}

	@Then("^player (\\d+) will enter url and click '(.+)'$")
	public void player_I_will_enter_url_and_click_joinGameButton(int player,
			String id) throws Throwable {

		WebElement ref = players[player].switchTo().activeElement()
				.findElement(By.xpath("//input[@id='displayHref']"));

		ref.click();
		ref.sendKeys(this.gameUrl);

		WebElement element = players[player].switchTo().activeElement()
				.findElement(By.xpath("//button[contains(@id,'" + id + "')]"));
		element.click();
		pause();

	}

	@Then("^pause (\\d+) seconds$")
	public void pause_seconds(int seconds) throws Throwable {
		Thread.sleep(1000 * seconds);
	}

	@Then("^i will wait (\\d+) seconds and close browsers$")
	public void i_will_wait_seconds_and_close_browsers(int seconds)
			throws Throwable {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_firefoxPlayer1.close();
		_chromePlayer2.close();
	}

	void pause() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
