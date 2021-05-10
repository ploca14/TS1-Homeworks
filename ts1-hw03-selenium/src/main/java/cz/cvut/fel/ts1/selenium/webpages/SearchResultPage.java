package cz.cvut.fel.ts1.selenium.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage {
    private WebDriver driver;

    private static String PAGE_TITLE = "Search Results - Springer";

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "Article")
    private WebElement articleFacetLink;

    @FindBy(how = How.CSS, using = "#results-list > * > h2 > a")
    private List<WebElement> articles;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;

        // Check if the current page is the Search results page
        if (!driver.getTitle().equals(PAGE_TITLE)) {
            throw new IllegalStateException("This is not the " + PAGE_TITLE + " page " +
                    "current page is: " + driver.getCurrentUrl());
        }

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public SearchResultPage clickOnArticleFacet() {
        articleFacetLink.click();
        return this;
    }

    public void clickOnArticle(int index) {
        articles.get(index).click();
    }

    public ArticlePage openArticle(int index) {
        clickOnArticle(index);
        return new ArticlePage(driver);
    }
}
