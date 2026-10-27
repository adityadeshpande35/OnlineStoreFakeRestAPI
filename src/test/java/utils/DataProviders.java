package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataProviders {

    @DataProvider
    public Object[][] jsonDataProvider() {

        String filepath = ".\\testdata\\product.json";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, String>> dataList =
                    objectMapper.readValue(
                            new File(filepath),
                            new TypeReference<List<Map<String, String>>>() {}
                    );

            Object[][] dataArray = new Object[dataList.size()][];

            for (int i = 0; i < dataList.size(); i++) {

                dataArray[i] = new Object[]{
                        dataList.get(i)
                };
            }

            return dataArray;

        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][];
        }
    }


    @DataProvider
    public Object[][] csvDataProvider() {

        String filepath = ".\\testdata\\product.csv";

        List<String[]> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filepath))) {

            // Skip the first line (header)
            br.readLine();

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                dataList.add(data);
            }

            Object[][] dataArray = new Object[dataList.size()][];

            for (int i = 0; i < dataList.size(); i++) {

                dataArray[i] = dataList.get(i);
            }

            return dataArray;

        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][];
        }
    }
}