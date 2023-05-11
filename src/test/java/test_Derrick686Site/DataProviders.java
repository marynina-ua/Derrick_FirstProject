package test_Derrick686Site;

import com.github.hemanthsridhar.CSVUtils;
import com.github.hemanthsridhar.ExcelUtils;
import com.github.hemanthsridhar.lib.ExtUtils;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataProviders extends TestBase {

    @DataProvider // ЗАХАРДКОДЖИН
    public Iterator<Object[]> getWrongLoginData(){
        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{"wrong email","password"});
        list.add(new Object[]{"biley@example.com","12345"});
        list.add(new Object[]{"biley_example.com","123456"});

        return list.iterator();
    }

    @DataProvider // ПРАВИЛЬНЫЕ ДАННЫЕ
    public static Iterator<Object[]> correctCredsFromCSV() throws IOException{
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/right_creds.csv"));

        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",", -1); // -1 тоже самое что и 4. При 0 если будет подряд две ,, то пропустит их, а при -1 оно засчитает все
            logger.info(Arrays.toString(split));

            list.add(split);
            line = reader.readLine();
        }

        return list.iterator();
    }

    @DataProvider // НЕПРАВИЛЬНЫЕ ДАННЫЕ
    public static Iterator<Object[]> wrongCredsFromCSV() throws IOException {
        List<Object[]> list = new ArrayList<>();

//      BufferedReader - это способ чтения Файлов
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/wrong_creds.csv"));

        String line = reader.readLine(); //readLine - читать построчно
        while (line != null) { //пока строка читается
            String[] split = line.split(",", -1); //мы делаем разделение строки ","
            logger.info(Arrays.toString(split));
            list.add(split);
            line = reader.readLine();
        }
        return list.iterator();
    }

    // Документация - https://github.com/hemanthsridhar/testng-excel-dataprovider
    @DataProvider
    public Object[][] excelCorrectDataRead() throws Exception {
        ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "correctData");
        return ext.parseData();
    }

    @DataProvider
    public Object[][] excelWrongDataRead() throws Exception {
        ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "wrongData");
        return ext.parseData();
    }

    @DataProvider
    public Object[][] csvWrongDataRead() throws Exception {
        String path = "src/test/resources/wrong_creds.csv";
        ExtUtils ext = new CSVUtils(path, true);
        return ext.parseData();
    }
}
