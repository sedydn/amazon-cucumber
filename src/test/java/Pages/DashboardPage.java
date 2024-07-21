package Pages;

import Util.ElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    WebDriver driver;
    ElementHelper elementHelper;
    WebDriverWait wait;

    public DashboardPage(WebDriver driver){
        this.driver = driver;
        this.elementHelper = new ElementHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By acceptButton = By.cssSelector("#sp-cc-accept");
    By searchField = By.cssSelector("#twotabsearchtextbox");
    By searchButton = By.cssSelector("#nav-search-submit-button");
    public void acceptCookie() {
        elementHelper.checkVisible(acceptButton);
        elementHelper.click(acceptButton);
    }


    public void clickSearchField() {
        elementHelper.checkVisible(searchField);
        elementHelper.click(searchField);
    }

    public void writeProductName() {
        elementHelper.sendKey(searchField, "cep telefonu");
    }

    public void clickSearchButton() {
        elementHelper.click(searchButton);
    }
}
