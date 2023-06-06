package test_Derrick686Site;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

public class EveryUserLogInTest_withDataProvider extends TestBase {

    @BeforeMethod
    public void initULogIn(){
        wd.get(SITE_LOGIN_PAGE);
    }

    @Test(dataProvider = "correctCredsFromCSV", dataProviderClass = DataProviders.class)
    public void positiveTestDP(String email, String pwd, String elementsTrue, String elementsFalse) throws InterruptedException{
        authDataProvider(email, pwd);

        //Check if split by ; strings from elementsTrue are presented on the page as links
        String[] presented = elementsTrue.split(";");
        for (String verification : presented) {
            wd.findElement(By.partialLinkText(verification));
        }

        //Check if split by ; strings from elementsFalse are not presented on the page as links (excluding space characters from verifications)
        String[] notpresented = elementsFalse.split(";");
        for (String verification : notpresented) {
            if (!Objects.equals(verification, ""))
            {
                Assert.assertEquals(wd.findElements(By.partialLinkText(verification)).size(), 0);
            }
        }
    }

    @AfterMethod
    public void afterMethodUserLogin(){
        System.out.println("Test done");
        logger.info("Test of Class UserLogIn_DataProvider is done");
    }
}