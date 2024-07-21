Feature: User login and add product to cart

  @CorrectLogin
  Scenario: Correct Username & Correct Password
    Given User at on homepage
    When Click dashboard login button
    When Check user at login page
    When Write correct username
    When Click continue button
    When Write correct password
    When Click login button
    When Check Successful login

  @AddBasket
  Scenario: User adds product to cart
    Given User at on homepage
    When Click dashboard login button
    When Check user at login page
    When Write correct username
    When Click continue button
    When Write correct password
    When Click login button
    When Check Successful login
    When Click the search field
    When Write product name
    When Click search button
    When Filter the price range
    When Select a random product from the bottom of the page
    When Add the product from the lowest-rated seller to the cart
    Then The user verifies that the product is correctly added to the cart



