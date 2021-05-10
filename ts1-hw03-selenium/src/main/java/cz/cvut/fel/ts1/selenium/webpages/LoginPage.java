package cz.cvut.fel.ts1.selenium.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    private static String PAGE_TITLE = "Create Account - Springer";

    @FindBy(how = How.ID, using = "login-box-email")
    private WebElement emailField;

    @FindBy(how = How.ID, using = "login-box-pw")
    private WebElement passwordField;

    @FindBy(how = How.CSS, using = "button[title='Log in']")
    private WebElement loginButton;

    @FindBy(how = How.CSS, using = "#login-box > .error-message")
    private WebElement loginError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        // Check if the current page is the Login page
        if (!driver.getTitle().equals(PAGE_TITLE)) {
            throw new IllegalStateException("This is not the " + PAGE_TITLE + " page " +
                    "current page is: " + driver.getCurrentUrl());
        }

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public LoginPage setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public void clickOnLogin() {
        loginButton.click();
    }

    public HomePage loginAsValidUser(String email, String password) {
        this.setEmail(email).setPassword(password).clickOnLogin();
        return new HomePage(driver);
    }

    public LoginPage loginAsInvalidUser(String email, String password) {
        this.setEmail(email).setPassword(password).clickOnLogin();
        return new LoginPage(driver);
    }

    public WebElement getLoginError() {
        return loginError;
    }
}
