package StepDefinitions;

import Pages.ProductPage;
import Util.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class ProductPageStepDefinitions {
    WebDriver driver = DriverFactory.getDriver();
    ProductPage productPage = new ProductPage(driver);

    @When("Filter the price range")
    public void filterThePriceRange() {
        productPage.filterPriceRange();
    }

    @When("Select a random product from the bottom of the page")
    public void selectARandomProductFromTheBottomOfThePage() {
        productPage.selectRandomProduct();
    }

    @When("Add the product from the lowest-rated seller to the cart")
    public void addTheProductFromTheLowestRatedSellerToTheCart() {
        productPage.selectLowestSellerAndAddCart();
    }

    @Then("The user verifies that the product is correctly added to the cart")
    public void theUserVerifiesThatTheProductIsCorrectlyAddedToTheCart() {
        productPage.verifyAddedProductToTheCart();
    }
}
