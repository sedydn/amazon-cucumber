package Pages;

import Util.ConfigReader;
import Util.ElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

import static java.lang.Thread.sleep;

public class LoginPage {
    WebDriver driver;
    ElementHelper elementHelper;
    WebDriverWait wait;

    static Properties properties;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.elementHelper = new ElementHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        properties = ConfigReader.getProperties();
    }

    By DashboardLoginButton = By.cssSelector("div[id='nav-signin-tooltip'] span[class='nav-action-inner']");

    By UsernameInput = By.cssSelector("#ap_email");
    By ContinueButton = By.xpath("//input[@id='continue']");
    By PasswordInput = By.cssSelector("#ap_password");
    By LoginButton = By.cssSelector("#signInSubmit");
    By CheckUsername = By.cssSelector("#nav-link-accountList-nav-line-1");
    public void verifyUserAtHomepage() {
        elementHelper.checkVisible(DashboardLoginButton);
    }
    public void clickDashboardLoginButton() {
        elementHelper.click(DashboardLoginButton);
    }
    public void verifyUserAtLoginPage() {
        elementHelper.checkVisible(UsernameInput);
    }

    public void writeUsername() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        elementHelper.click(UsernameInput);
        elementHelper.sendKey(UsernameInput, properties.getProperty("username"));
    }

    public void clickContinueButton() {
        try {
            // Sayfanın tam olarak yüklenmesini bekle
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            // Elementin var olup olmadığını kontrol et
            if (driver.findElements(ContinueButton).size() != 0) {
                WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(ContinueButton));

                // JavaScript ile doğrudan tıklama
               ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
               // continueButton.click();
            } else {
                System.out.println("Continue button not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void writePassword() {
        elementHelper.click(PasswordInput);
        elementHelper.sendKey(PasswordInput, properties.getProperty("password"));

    }

    public void clickLoginButton() {
        elementHelper.checkVisible(LoginButton);
        elementHelper.click(LoginButton);
    }

    public void checkSuccessfulLogin() {
        elementHelper.checkVisible(CheckUsername);
    }

}
