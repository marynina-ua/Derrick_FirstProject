package test_Derrick686Site;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogInTest extends TestBase {

    @BeforeMethod
    public void setUp(Method m, Object[] p){
        System.out.println("Running BeforeMethod: setUp()");
        logger.info("Starting BeforeMethod " + m.getName()+ "with data: "+ Arrays.asList(p));
        wd.get(SITE_LOGIN_PAGE);
    }

    @Test (priority = 1) // позитивный тест - проверяя наличие Сообщения подтверждения
    public void positiveTest() throws InterruptedException {
        Auth(BILLYE_EMAIL, VALID_PASSWORD);
        Assert.assertEquals(wd.getPageSource().contains(CONFIRM_MESSAGE), Boolean.TRUE);
        // ВЗЯТЬ ВО ВНИМАНИЕ СПОСОБ НИЖЕ:
        //Assert.assertEquals(searchInPageSource(CONFIRM_MESSAGE), Boolean.TRUE);
    }

    @Test (priority = 2) // негативный тест - проверяя наличие Сообщения об ошибке
    public void negativeTest() throws InterruptedException {
        Auth(BILLYE_EMAIL, INVALID_PASSWORD);
        Assert.assertEquals(wd.getPageSource().contains(ERROR_MESSAGE),Boolean.TRUE);
        // ВЗЯТЬ ВО ВНИМАНИЕ СПОСОБ НИЖЕ:
        //Assert.assertEquals(searchInPageSource(ERROR_MESSAGE), Boolean.TRUE);
    }

    @Test (priority = 3) //позитивный тест После негативного - проверяя наличие Сообщения подтверждения
    public void goodAuthAfterBad() throws InterruptedException {
        negativeTest();
        wait(2000);
        positiveTest();
    }

    @Test (priority = 4)//позитивный тест После негативного - проверяя наличие Сообщения подтверждения
    public void goodAuthAfterBadWithRecorder() throws InterruptedException {
        startRecording();
        negativeTest();
        wait(2000);
        positiveTest();
        stopRecoding();
    }

    @AfterMethod
    public void message(){
        System.out.println("AfterMethod SOUT");
    }
}
//

