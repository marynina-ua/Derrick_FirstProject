package test_Derrick686Site;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class AmountOfUsersTest extends TestBase {

    @BeforeTest(alwaysRun=true)
    public void beSignIn() throws InterruptedException {
        wd.get(SITE_LOGIN_PAGE);
        Auth(BILLYE_EMAIL, VALID_PASSWORD);
    }

    @BeforeMethod(alwaysRun=true)
    public void beOnClientPage(){
        wd.get(SITE_CLIENTS_PAGE);
    }

    @Test
    public void searchClientsByCompanyNegative() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        int randomNum = ThreadLocalRandom.current().nextInt(10000, 50000 + 1);
        logger.info("Searching for a company with a random company name: " + randomNum);
        fieldSearchClient(String.valueOf(randomNum)); // в методе searchClientsBy параметры стринговые а randomNum это int поэтому используем- String.valueOf

        Boolean isErrorOnPage = searchInPageSource("No results found, try adjusting your search and filters");
        Assert.assertTrue(isErrorOnPage);
        logger.info("Error message is found on page, passed test "+methodName);
    }

    @Test
    public void oneClientByCompanyName_Worman() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String companyName = "Worman";

        logger.info("Searching for clients with a random company name: "+companyName);
        fieldSearchClient(companyName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
        logger.info("Searching for one client Lucie displayed, Loree not displayed");
        Assert.assertTrue(searchInPageSource("lucie@example.com"));
        Assert.assertFalse(searchInPageSource("loree@example.com"));
        Assert.assertTrue(searchInPageSource("billye@example.com"));
        logger.info("Test "+methodName+" finished, passed");
    }

    @Test
    public void oneClientByCompanyName_Montag() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String companyName = "Montag";

        logger.info("Searching for clients with a random company name: "+companyName);
        fieldSearchClient(companyName);
        int expectedSearchResults = 2;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
        logger.info("Searching for clients Lucie and billye displayed, Loree not displayed");
        Assert.assertTrue(searchInPageSource("edra@example.com"));
        Assert.assertFalse(searchInPageSource("loree@example.com"));
        Assert.assertTrue(searchInPageSource("billye@example.com"));
        logger.info("Test "+methodName+" finished, passed");
    }

    @Test
    public void oneClientByClientName_billye() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String clientName = "billye";

        logger.info("Searching for clients with a random company name: "+clientName);
        fieldSearchClient(clientName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);

        logger.info("Searching for one client billye displayed, Loree and Lucie not displayed");
        Assert.assertFalse(searchInPageSource("dominica@example.com"));
        Assert.assertFalse(searchInPageSource("loree@example.com"));
        Assert.assertTrue(searchInPageSource("billye@example.com")); //тут понятно что этот находится а те два не должны
        logger.info("Test "+methodName+" finished, passed");
    }

    @Test
    public void oneClientByClientName_lucie() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String clientName = "lucie";

        logger.info("Searching for clients with a random company name: "+clientName);
        fieldSearchClient(clientName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
        logger.error("Test passed " + methodName + ". Expected and actual results"+searchResults.size() + " " + expectedSearchResults);
        logger.info("Searching for one client Lucie displayed, Loree and billye not displayed");
        Assert.assertTrue(searchInPageSource("lucie@example.com"));
        Assert.assertFalse(searchInPageSource("loree@example.com"));
        Assert.assertFalse(searchInPageSource("billye@example.com"));
        logger.info("Test "+methodName+" finished, passed");
    }

    @AfterMethod(alwaysRun=true)
    public void makingScreenShots(ITestResult result){
        if (!result.isSuccess()){
            logger.error("Failed test: " +result.getMethod().getMethodName()+ "screenshot: " +takeScreenshot());
        }
    }

    @AfterTest(alwaysRun=true)
    public void logOut(){

        try {
            logout();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        logger.info("AfterTest DONE");
    }
}

