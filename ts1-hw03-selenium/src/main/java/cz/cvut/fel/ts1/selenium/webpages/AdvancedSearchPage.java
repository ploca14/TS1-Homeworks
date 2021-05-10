package cz.cvut.fel.ts1.selenium.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AdvancedSearchPage {
    private WebDriver driver;

    private static String PAGE_TITLE = "Advanced Search - Springer";

    @FindBy(how = How.ID, using = "all-words")
    private WebElement allWordsField;

    @FindBy(how = How.ID, using = "least-words")
    private WebElement leastWordsField;

    @FindBy(how = How.ID, using = "date-facet-mode")
    private WebElement dateFacetDropdown;

    @FindBy(how = How.ID, using = "facet-start-year")
    private WebElement startYearField;

    @FindBy(how = How.ID, using = "facet-end-year")
    private WebElement endYearField;

    @FindBy(how = How.ID, using = "title-is")
    private WebElement titleIsField;

    @FindBy(how = How.ID, using = "submit-advanced-search")
    private WebElement searchButton;

    public AdvancedSearchPage(WebDriver driver) {
        this.driver = driver;

        // Check if the current page is the Advanced search page
        if (!driver.getTitle().equals(PAGE_TITLE)) {
            throw new IllegalStateException("This is not the " + PAGE_TITLE + " page " +
                    "current page is: " + driver.getCurrentUrl());
        }

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public Select getSelectOptions() {
        return new Select(dateFacetDropdown);
    }

    public AdvancedSearchPage setAllWords(String value) {
        allWordsField.sendKeys(value);
        return this;
    }

    public AdvancedSearchPage setLeastWords(String value) {
        leastWordsField.sendKeys(value);
        return this;
    }

    public AdvancedSearchPage setStartYear(String value) {
        startYearField.sendKeys(value);
        return this;
    }

    public AdvancedSearchPage setEndYear(String value) {
        endYearField.sendKeys(value);
        return this;
    }

    public AdvancedSearchPage setTitleIs(String value) {
        titleIsField.sendKeys(value);
        return this;
    }

    public AdvancedSearchPage selectDateFacet(String value) {
        getSelectOptions().selectByValue(value);
        return this;
    }

    public void clickOnSearch() {
        searchButton.click();
    }

    public SearchResultPage searchByCriteria(String matchAllWords, String matchAtLeastOneWord, String publishedInYear) {
        this.setAllWords(matchAllWords)
            .setLeastWords(matchAtLeastOneWord)
            .selectDateFacet("between")
            .setStartYear(publishedInYear)
            .setEndYear(publishedInYear)
            .clickOnSearch();
        return new SearchResultPage(driver);
    }

    public SearchResultPage searchByTitleAndCriteria(String titleIs, String matchAllWords, String matchAtLeastOneWord, String publishedInYear) {
        this.setAllWords(matchAllWords)
            .setLeastWords(matchAtLeastOneWord)
            .setTitleIs(titleIs)
            .selectDateFacet("between")
            .setStartYear(publishedInYear)
            .setEndYear(publishedInYear)
            .clickOnSearch();
        return new SearchResultPage(driver);
    }

}
