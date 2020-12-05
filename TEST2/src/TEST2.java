import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TEST2 {
    public static void main(String[] args) throws SQLException, ParserConfigurationException {

        List<List<String>> ret3 = new ArrayList<List<String>>();
        BufferedReader br3 = null;

        try {
            br3 = Files.newBufferedReader(Paths.get("C:\\Users\\ADMIN\\Downloads\\PM.csv"));
             Charset.forName("UTF-8");
            String line = "";

            while ((line = br3.readLine()) != null) {
                // CSV 1행을 저장하는 리스트
                List<String> tmpList = new ArrayList<String>();
                String array[] = line.split(",");
                // 배열에서 리스트 반환
                tmpList = Arrays.asList(array);
                System.out.println(tmpList);
                ret3.add(tmpList);
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br3 != null) {
                    br3.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
