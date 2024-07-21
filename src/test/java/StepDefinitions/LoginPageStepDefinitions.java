package StepDefinitions;

import Pages.LoginPage;
import Util.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class LoginPageStepDefinitions {
    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);


    @Given("User at on homepage")
    public void userAtOnHomepage() {
        loginPage.verifyUserAtHomepage();
    }
    @When("Click dashboard login button")
    public void clickDashboardLoginButton() {
        loginPage.clickDashboardLoginButton();
    }
    @When("Check user at login page")
    public void checkUserAtLoginPage() {
        loginPage.verifyUserAtLoginPage();
    }
    @When("Write correct username")
    public void writeCorrectUsername() {
        loginPage.writeUsername();
    }

    @When("Click continue button")
    public void clickContinueButton() {
        loginPage.clickContinueButton();
    }

    @When("Write correct password")
    public void writeCorrectPassword() {
        loginPage.writePassword();
    }

    @When("Click login button")
    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("Check Successful login")
    public void checkSuccessfulLogin() {
        loginPage.checkSuccessfulLogin();
    }


}
