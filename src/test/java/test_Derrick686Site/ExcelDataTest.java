package test_Derrick686Site;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ExcelDataTest extends TestBase {

   @BeforeMethod
    public void openLoginPage() {
       wd.get("https://derrick686.softr.app/login");
   }

   //-------------------------------EXCEL, csv----------------------------------------------------------------
   @Test(dataProvider = "excelCorrectDataRead", dataProviderClass = DataProviders.class)
   public void rightExcel(String emailXl, String passXl) throws InterruptedException {
       authDataProvider(emailXl, passXl);
       String text = "Invalid email or password";
       Assert.assertEquals(wd.getPageSource().contains(text), Boolean.FALSE);
   }

    @Test(dataProvider = "excelWrongDataRead", dataProviderClass = DataProviders.class)
    public void BadAuthTestWithDataProviderExcel(String email, String pwd) throws InterruptedException {
        authDataProvider(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

    @Test(dataProvider = "csvWrongDataRead", dataProviderClass = DataProviders.class)
    public void BadAuthTestWithDataProviderCsv(String email, String pwd) throws InterruptedException {
        authDataProvider(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }
}


