package cz.cvut.fel.ts1.selenium.tests;

import cz.cvut.fel.ts1.selenium.webpages.AdvancedSearchPage;
import cz.cvut.fel.ts1.selenium.webpages.ArticlePage;
import cz.cvut.fel.ts1.selenium.webpages.HomePage;
import cz.cvut.fel.ts1.selenium.webpages.SearchResultPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class SavedArticlesTest {
    private static WebDriver driver;
    private static HomePage homePage;

    @BeforeAll
    private static void setup() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().setSize(new Dimension(1024, 768));
        driver.get("https://link.springer.com/");

        homePage = new HomePage(driver);
        homePage.acceptCookies();

        homePage.goToLoginPage().loginAsValidUser("plocivoj@fel.cvut.cz", "c*#ui#s2mRRy$hT");
    }

    @BeforeEach
    private void goToHomePage() {
        driver.get("https://link.springer.com/");
    }

    @ParameterizedTest
    @CsvFileSource(files = "data.csv")
    public void verifyArticleData_ValidArticle_Success(String expectedTitle, String expectedPublishedAt, String expectedDoi) {
        AdvancedSearchPage advancedSearchPage =  homePage.goToAdvancedSearchPage();

        SearchResultPage searchResultPage =  advancedSearchPage.searchByTitleAndCriteria(
                expectedTitle,
                "Page Object Model",
                "Sellenium Testing",
                "2021"
        );

        ArticlePage articlePage = searchResultPage.openArticle(0);

        String title = articlePage.getTitle();
        String publishedAt = articlePage.getPublishedAt();
        String doi = articlePage.getDoi();

        assertEquals(expectedTitle, title);
        assertEquals(expectedPublishedAt, publishedAt);
        assertEquals(expectedDoi, doi);
    }


    @AfterAll
    private static void closeDriver() {
        driver.close();
    }
}
