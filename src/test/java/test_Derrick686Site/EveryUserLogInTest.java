package test_Derrick686Site;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EveryUserLogInTest extends TestBase {

    @BeforeMethod
    public void initULogIn(){
        wd.get(SITE_LOGIN_PAGE);
    }

    @Test(priority = 1)
    public void managerAuthTest() throws InterruptedException {
        System.out.println("sout Running test managerAuthTest()");
        logger.info("logger starting method: Auth(BILLYE_EMAIL, VALID_PASSWORD)");

        Auth(BILLYE_EMAIL, VALID_PASSWORD);

        wd.findElement(By.linkText("PROJECT OVERVIEW"));
        wd.findElement(By.linkText("CLIENTS"));
        wd.findElement(By.linkText("TEAM"));
        wd.findElement(By.linkText("INVOICES"));
    }

    @Test(priority = 2)
    public void clientAuthTest() throws InterruptedException {
        System.out.println("Running test clientAuthTest()");
        logger.info("starting method: Auth(LUCIE_EMAIL, VALID_PASSWORD)");

        Auth(LUCIE_EMAIL, VALID_PASSWORD);

        Assert.assertTrue(searchInPageSource("LC"));
        Assert.assertFalse(searchInPageSource("CLIENTS"));

        wd.findElement(By.partialLinkText("PROJECTS OVERVIEW"));
//        wd.findElement(By.partialLinkText("INVOICES"));

        //Verify there is no more links from manager/consultant: **first way**
        Assert.assertFalse(searchInPageSource("CLIENTS"));
        Assert.assertFalse(searchInPageSource("TEAM"));

        //Verify there is no more links from manager/consultant: **second way**
        //Attention to that findElements is used instead findElement to get an empty array (expected)
        Assert.assertTrue(wd.findElements(By.partialLinkText("CLIENTS")).isEmpty());
        Assert.assertTrue(wd.findElements(By.partialLinkText("TEAM")).isEmpty());
    }

    @Test(priority = 3)
    public void consultantAuthTest() throws InterruptedException {
        System.out.println("Running test consultantAuthTest()");
        logger.info("starting method: Auth(EDRA_EMAIL, VALID_PASSWORD)");

        Auth(EDRA_EMAIL, VALID_PASSWORD);

        wd.findElement(By.partialLinkText("PROJECT OVERVIEW"));
        wd.findElement(By.partialLinkText("CLIENTS"));
        wd.findElement(By.partialLinkText("TEAM"));
        wd.findElement(By.partialLinkText("INVOICES"));
    }

    @AfterMethod
    public void finishLogIn(){
        logout();
    }
}



/** Manager Email: billye@example.com
 PROJECT OVERVIEW
 CLIENTS -
 TEAM    -
 INVOICES */

/** Client Email: lucie@example.com
 PROJECTS OVERVIEW
 INVOICES */

/**  Consultant Email: edra@example.com
 PROJECT OVERVIEW
 CLIENTS -
 TEAM    -
 INVOICES */
