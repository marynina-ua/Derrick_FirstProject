package test_Derrick686Site;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogInTest_withDataProvider extends TestBase {

    @BeforeMethod
    public void initULogIn(){
        wd.get(SITE_LOGIN_PAGE);
    }

    //========= НЕПРАВИЛЬНЫЕ ДАННЫЕ ЗАХАРДКОРД
    @Test(dataProvider = "getWrongLoginData", dataProviderClass = DataProviders.class)
    public void negativeTestDP_hardcore(String nameDP, String passDP) throws InterruptedException {
        authDataProvider(nameDP, passDP);
        Assert.assertEquals(wd.getPageSource().contains(ERROR_MESSAGE),Boolean.TRUE);
    }

    //========  НЕПРАВИЛЬНЫЕ ДАННЫЕ CSV
    @Test(dataProvider = "wrongCredsFromCSV", dataProviderClass = DataProviders.class)
    public void negativeTestDP(String nameCVS, String psdCSV) throws InterruptedException {
        authDataProvider(nameCVS, psdCSV);
        Assert.assertEquals(searchInPageSource(ERROR_MESSAGE), Boolean.TRUE);
    }
}
