package cz.cvut.fel.ts1.selenium;

import cz.cvut.fel.ts1.selenium.webpages.AdvancedSearchPage;
import cz.cvut.fel.ts1.selenium.webpages.ArticlePage;
import cz.cvut.fel.ts1.selenium.webpages.HomePage;
import cz.cvut.fel.ts1.selenium.webpages.SearchResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final List<Article> articles = new ArrayList<>();

    private static final int NUMBER_OF_ARTICLES = 4;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://link.springer.com/");

        HomePage homePage = new HomePage(driver);
        homePage.acceptCookies();

        AdvancedSearchPage advancedSearchPage =  homePage.goToAdvancedSearchPage();

        SearchResultPage searchResultPage =  advancedSearchPage.searchByCriteria(
                "Page Object Model",
                "Sellenium Testing",
                "2021"
        );

        searchResultPage.clickOnArticleFacet();

        for (int i = 0; i < NUMBER_OF_ARTICLES; i++) {
            ArticlePage articlePage = searchResultPage.openArticle(i);

            String title = articlePage.getTitle();
            String doi = articlePage.getDoi();
            String publishedAt = articlePage.getPublishedAt();

            Article article = new Article(title, doi, publishedAt);
            articles.add(article);

            driver.navigate().back();
        }

        try (PrintWriter writer = new PrintWriter("data.csv")) {
            for (Article article:
            articles) {
                writer.println(article);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        driver.close();
    }

    private static class Article {
        private final String title;
        private final String doi;
        private final String publishedAt;

        private Article(String title, String doi, String publishedAt) {
            this.title = title;
            this.publishedAt = publishedAt;
            this.doi = doi;
        }

        @Override
        public String toString() {
            return String.format("\"%s\", %s, %s", title, publishedAt, doi);
        }
    }
}
