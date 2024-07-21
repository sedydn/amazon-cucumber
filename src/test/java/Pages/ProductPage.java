package Pages;

import Util.ElementHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class ProductPage {
    WebDriver driver;
    ElementHelper elementHelper;
    WebDriverWait wait;

    Actions actions;


    public ProductPage(WebDriver driver){
        this.driver = driver;
        this.elementHelper = new ElementHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

   // By filterRangeMinPrice = By.cssSelector("input[id='p_36/range-slider_slider-item_lower-bound-slider']");
    By filterButton = By.cssSelector(".a-button-input");
    By otherSellerBox = By.cssSelector("div[id='dynamic-aod-ingress-box'] div[class='a-box-inner']");
    By otherSellerLink = By.cssSelector("#dynamic-aod-ingress-box > div > div.a-section.a-spacing-none.daodi-content > a");
    By addToCartButton = By.cssSelector("#add-to-cart-button");
    By cart = By.id("nav-cart");


    public void filterPriceRange() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement rangeInputLower = driver.findElement(By.xpath("//input[@type='range']"));

        // Change the value of the range input using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", rangeInputLower, "91");

        // Optionally, trigger the change event if necessary
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", rangeInputLower);

        WebElement rangeInputUpper = driver.findElement(By.xpath("//input[@id='p_36/range-slider_slider-item_upper-bound-slider']"));

        // Change the value of the range input using JavaScript

        js.executeScript("arguments[0].value = arguments[1];", rangeInputUpper, "103");

        // Optionally, trigger the change event if necessary
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", rangeInputUpper);


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        elementHelper.click(filterButton);

    }

    public void selectRandomProduct() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);"); // 200 piksel yukarda durmak için

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break; // Sayfanın sonuna ulaştık
            }
            lastHeight = newHeight;
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-main-slot .s-result-item")));

        js.executeScript("window.scrollBy(0, -1200);");

        List<WebElement> products = driver.findElements(By.cssSelector(".s-image-square-aspect"));
        if (products.isEmpty()) {
            System.out.println("Ürünler bulunamadı.");
            return;
        }
        //WebElement lastRow = products.get(products.size() - 1); // En son satırı alın

        // Bu satırdaki ürünleri bulun (örneğin, ürünler bir liste öğesi içinde olabilir)
        //List<WebElement> productsInLastRow = lastRow.findElements(By.cssSelector(".s-result-item"));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        // Rastgele bir ürün seçin

        int randomProductIndex = new Random().nextInt(products.size());
        WebElement randomProduct = products.get(randomProductIndex);
        // Seçilen ürüne tıklayın
        randomProduct.click();

    }

    public WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }
    public void selectLowestSellerAndAddCart() {
        System.out.println("Starting the selectLowestSellerAndAddCart method.");

        waitForPageToLoad();
        scrollPage(280);

        try {

            if (isOtherSellerBoxDisplayed()) {
                clickOtherSellerLink();
                selectLowestRatedSeller();

            } else {
                addToCart();
            }
        } catch (TimeoutException e) {
            System.out.println("An error occurred: TimeoutException - " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("An error occurred: NoSuchElementException - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void waitForPageToLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private void scrollPage(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + pixels + ");");
    }
    public boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty();
    }
    public boolean isElementPresentWithinTimeout(By locator, int timeoutInSeconds) {
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000;
        while (System.currentTimeMillis() < endTime) {
            if (!driver.findElements(locator).isEmpty()) {
                return true;
            }
            try {
                Thread.sleep(500); // Kısa bir süre bekle
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
    public boolean isElementVisible(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            WebElement element = elements.get(0);
            try {
                boolean displayed = element.isDisplayed();
                System.out.println("Element visibility status: " + displayed);
                return displayed;
            } catch (Exception e) {
                System.out.println("Exception while checking visibility: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    private boolean isOtherSellerBoxDisplayed() {
        System.out.println("Display control start");
        waitForPageToLoad();
        boolean isPresent = isElementPresentWithinTimeout(otherSellerBox, 2);
        System.out.println("Display control start" + isPresent);

        if (isPresent) {
            boolean isVisible = isElementVisible(otherSellerBox);
            if (isVisible) {
                System.out.println("Element is present and visible.");
                return true;
            } else {
                System.out.println("Element is present but not visible.");
                return false;
            }
        } else {
            System.out.println("Element is not present in the DOM.");
            return false;
        }


    }

    private void clickOtherSellerLink() {
        WebElement otherLink = waitForElementToBeVisible(otherSellerLink, 25);
        if (otherLink.isDisplayed()) {
            System.out.println("Other seller link is displayed.");
            otherLink.click();
        }
    }

    private void selectLowestRatedSeller() {
        List<WebElement> sellerRatings = driver.findElements(By.cssSelector("span[id='a-autoid-2-offer-1'] input[name='submit.addToCart']"));
        if (!sellerRatings.isEmpty()) {
            System.out.println("Seller ratings found.");
            WebElement lowestRatedSeller = sellerRatings.get(0); // İlk satıcıyı seç (en düşük not)
            lowestRatedSeller.click();
        } else {
            System.out.println("No seller ratings found.");
        }
    }

    private void addToCart() {
        System.out.println("No other sellers available.");
        if (driver.findElements(addToCartButton).size() != 0) {
            WebElement addToCartBtn = waitForElementToBeVisible(addToCartButton, 25);
            if (addToCartBtn.isDisplayed()) {
                System.out.println("Add to cart button is displayed.");
                addToCartBtn.click();
            } else {
                // JavaScript ile doğrudan tıklama
                System.out.println("Add to cart button is not displayed, trying JavaScript click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);
            }
        } else {
            System.out.println("Add to cart button not available.");
        }
    }

    public void testAddCart(){
        WebElement otherSellerBox = waitForElementToBeVisible(By.cssSelector(".a-spacing-base .daodi-header-font"), 10);
        WebElement otherChoice = waitForElementToBeVisible(By.cssSelector("#buybox-see-all-buying-choices"), 10);
        WebElement noProduct = waitForElementToBeVisible(By.cssSelector(".a-text-bold"), 10);

        if (otherSellerBox.isDisplayed()) {
            WebElement otherSellers = driver.findElement(By.cssSelector("i[class='a-icon a-icon-arrow a-icon-small daodi-arrow-icon']"));
            otherSellers.click();
            List<WebElement> sellerRatings = driver.findElements(By.cssSelector(".aod-seller-rating-count-class.a-button-input"));
            if (!sellerRatings.isEmpty()) {
                WebElement lowestRatedSeller = sellerRatings.get(0); // İlk satıcıyı seç (en düşük not)
                lowestRatedSeller.click();
            }
        } else if (otherChoice.isDisplayed()) {
            WebElement otherChoiceAddButton = waitForElementToBeVisible(By.cssSelector("#buybox-see-all-buying-choices.a-button-input"),10);
            otherChoiceAddButton.click();
        } else if (noProduct.isDisplayed()) {
            System.out.println("No other seller or other choice available.");
        } else {
            WebElement addToCartButton = waitForElementToBeVisible(By.cssSelector("#add-to-cart-button"), 20);
            if (addToCartButton.isDisplayed()) {
                addToCartButton.click();

            } else {
                System.out.println("Add to cart button not available.");
            }
        }
    }

    public void verifyAddedProductToTheCart() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        try {
            // Sepet butonunun varlığını ve tıklanabilir olmasını bekle
            if (driver.findElements(cart).size() != 0) {
                WebElement cartBtn = wait.until(ExpectedConditions.elementToBeClickable(cart));

                // JavaScript ile doğrudan tıklama
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartBtn);
                //wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
                driver.navigate().refresh();
                wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
                // Sepet sayısının güncellenmesini bekle
                WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart-count")));
                String itemCount = cartCount.getText();

                // Beklenen değeri "1" ile karşılaştır
                Assert.assertEquals(itemCount, "1", "Cart count does not match expected value.");
            } else {
                System.out.println("Add to cart button not available.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
