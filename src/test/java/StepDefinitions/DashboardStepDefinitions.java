package StepDefinitions;

import Pages.DashboardPage;
import Pages.LoginPage;
import Util.DriverFactory;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class DashboardStepDefinitions {
    WebDriver driver = DriverFactory.getDriver();
    DashboardPage dashboardPage = new DashboardPage(driver);
    @When("Click accept cookies")
    public void clickAcceptCookies() {
        dashboardPage.acceptCookie();

    }


    @When("Click the search field")
    public void clickTheSearchField() {
        dashboardPage.clickSearchField();
    }

    @When("Write product name")
    public void writeProductName() {
        dashboardPage.writeProductName();
    }

    @When("Click search button")
    public void clickSearchButton() {
        dashboardPage.clickSearchButton();
    }


}
