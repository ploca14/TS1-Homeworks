package cz.cvut.fel.ts1.selenium.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_TITLE = "Home - Springer";

    @FindBy(how = How.CLASS_NAME, using = "register-link")
    private WebElement registerLink;

    @FindBy(how = How.CLASS_NAME, using = "open-search-options")
    private WebElement searchOptionsButton;

    @FindBy(how = How.ID, using = "advanced-search-link")
    private WebElement advancedSearchLink;

    @FindBy(how = How.ID, using = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        // Check if the current page is the Home page
        if (!driver.getTitle().equals(PAGE_TITLE)) {
            throw new IllegalStateException("This is not the " + PAGE_TITLE + " page " +
                    "current page is: " + driver.getCurrentUrl());
        }

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public LoginPage goToLoginPage() {
        registerLink.click();
        return new LoginPage(driver);
    }

    public HomePage openSearchOptions() {
        searchOptionsButton.click();
        return this;
    }

    public void clickOnAdvancedSearch() {
        advancedSearchLink.click();
    }

    public AdvancedSearchPage goToAdvancedSearchPage() {
        openSearchOptions();
        clickOnAdvancedSearch();
        return new AdvancedSearchPage(driver);
    }

    public void acceptCookies() {
        acceptCookiesButton.click();
    }
}
