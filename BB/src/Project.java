import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;


public class Project {

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static void main(String[] args) throws SQLException, ParserConfigurationException {
        try {


            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");
            System.out.println("Connecting PostgreSQL database");

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "7363";

            Connection connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("연결 성공");
            } else {
                System.out.println("연결 실패");
            }


            Statement qry = connection.createStatement();

            qry.execute("drop table if exists Station");
            qry.execute("drop table if exists Congestion");
            qry.execute("drop table if exists PM");

            qry.execute("create table Station(line varchar(10), sName varchar(10), region varchar(10))");
            qry.execute("create table Congestion(day varchar(10), line varchar(10), sName varchar(10), time6cong float, time12cong float, time18cong float )");
            qry.execute("create table PM(date bigint , region varchar(10), fineP int, ultP int)");

            ///////////////////////////////////////////////////////////////////////////////////////////////Station 넣기
            List<List<String>> ret = new ArrayList<List<String>>();
            BufferedReader br = null;

            /*********************
             * Station 데이터 넣기
             *********************/
            try {
                br = Files.newBufferedReader(Paths.get("C:\\Users\\ADMIN\\Downloads\\Station.csv"));
                // Charset.forName("UTF-8");
                String line = "";

                while ((line = br.readLine()) != null) {
                    // CSV 1행을 저장하는 리스트
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    // 배열에서 리스트 반환
                    tmpList = Arrays.asList(array);
                    // System.out.println(tmpList);
                    ret.add(tmpList);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            System.out.println("Inserting tuples to Station relations");

            String insert_station = "insert into Station (line, sName, region) values(?,?,?);";
            PreparedStatement station = connection.prepareStatement(insert_station);

            for (int i = 1; i < ret.size(); i++) {
                for (int j = 0; j < ret.get(i).size(); j++) {
                    //System.out.print(ret.get(i).get(j) + "\t");
                    station.setString(j + 1, ret.get(i).get(j));

                }
                //System.out.println();
                station.executeUpdate();
            }


            PreparedStatement q1 = connection.prepareStatement("select * from Station");
            ResultSet res1 = q1.executeQuery();

//            System.out.println("[line/sName/region]");
//            while (res1.next()) {
//                String line = res1.getString(1);
//                String sname = res1.getString(2);
//                String region = res1.getString(3);
//                System.out.println(line + "/" + sname + "/" + region);
//            }


            /***********************
             * Congestion 데이터 넣기
             **********************/


            List<List<String>> ret2 = new ArrayList<List<String>>();
            BufferedReader br2 = null;

            try {
                br2 = Files.newBufferedReader(Paths.get("C:\\Users\\ADMIN\\Downloads\\Congestion.csv"));
                // Charset.forName("UTF-8");
                String line = "";

                while ((line = br2.readLine()) != null) {
                    // CSV 1행을 저장하는 리스트
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");
                    // 배열에서 리스트 반환
                    tmpList = Arrays.asList(array);
                    // System.out.println(tmpList);
                    ret2.add(tmpList);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br2 != null) {
                        br2.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            System.out.println("Inserting tuples to Congestion relations");


            String insert_congestion = "insert into Congestion (day, line, sName,time6cong, time12cong, time18cong ) values(?,?,?,?,?,?);";
            PreparedStatement congestion = connection.prepareStatement(insert_congestion);

            for (int i = 1; i < ret2.size(); i++) {

                String day = ret2.get(i).get(0);
                String line = ret2.get(i).get(1);
                String sName = ret2.get(i).get(2);

                float time6cong = Float.parseFloat(ret2.get(i).get(3));
                float time12cong = Float.parseFloat(ret2.get(i).get(4));
                float time18cong = Float.parseFloat(ret2.get(i).get(5));

                congestion.setString(1, day);
                congestion.setString(2, line);
                congestion.setString(3, sName);
                congestion.setFloat(4, time6cong);
                congestion.setFloat(5, time12cong);
                congestion.setFloat(6, time18cong);

                congestion.executeUpdate();

            }

            PreparedStatement q2 = connection.prepareStatement("select * from Congestion");
            ResultSet res2 = q2.executeQuery();

//            System.out.println("[day/line/sName/time6cong/time12cong/time18cong]");
//            while (res2.next()) {
//                String day = res2.getString(1);
//                String line = res2.getString(2);
//                String sName = res2.getString(3);
//                float a1 = res2.getFloat(4);
//                float a2 = res2.getFloat(5);
//                float a3 = res2.getFloat(6);
//
//                System.out.println(day + "/" + line + "/" + sName + "/" + a1 + "/" + a2 + "/" + a3);
//            }

            /*********************
             * PM 데이터 넣기
             *********************/
            List<List<String>> ret3 = new ArrayList<List<String>>();
            BufferedReader br3 = null;

            try {
                br3 = Files.newBufferedReader(Paths.get("C:\\Users\\ADMIN\\Downloads\\PM.csv"));
                // Charset.forName("UTF-8");
                String line = "";

                while ((line = br3.readLine()) != null) {
                    // CSV 1행을 저장하는 리스트
                    List<String> tmpList = new ArrayList<String>();
                    String array[] = line.split(",");

                    // 배열에서 리스트 반환
                    tmpList = Arrays.asList(array);
//                    System.out.println(tmpList);
                    ret3.add(tmpList);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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


//            System.out.println("Inserting tuples to Congestion relations");


            String insert_PM = "insert into PM (date, region, fineP, ultP) values(?,?,?,?);";
            PreparedStatement PM = connection.prepareStatement(insert_PM);

            for (int i = 1; i < ret3.size(); i++) {

                String arr0[] = ret3.get(i).get(0).split(" ");



                if(ret3.get(i).get(6) == ""){

                }
                int fineP = Integer.parseInt(ret3.get(i).get(6));
                int ultP = Integer.parseInt(ret3.get(i).get(7));
                long date = Long.parseLong(arr0[0]);
                String region = ret3.get(i).get(1);

//                if(arr1[0] == ""){
//                    fineP = 0;
//                }else{
//                    fineP = Integer.parseInt(arr1[0]);
//                }

//                if(arr2[0] == ""){
//                    ultP = 0;
//                }else{
//                    ultP = Integer.parseInt(arr2[0]);
//                }

//                int ultP = Integer.parseInt(arr2[0]);

                PM.setLong(1, date);
                PM.setString(2, region);
                PM.setInt(3, fineP);

                PM.setInt(4, ultP);

                PM.executeUpdate();

            }

            PreparedStatement q3 = connection.prepareStatement("select * from PM");
            ResultSet res3 = q3.executeQuery();

//            System.out.println("[date/region/fineP/ultP]");
//            while (res3.next()) {
//                long date = res3.getLong(1);
//                String region = res3.getString(2);
//
//                int fineP = res3.getInt(3);
//                int ultP = res3.getInt(4);
//
//                System.out.println(+date + "/" + region + "/" + fineP + "/" + ultP);
//            }


            /******************************
             *OpenAPI 가져오는 거
             * ***************************/


	         ///////////////////////////////////////////////////////////시간
	         long systemTime = System.currentTimeMillis();
	         
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH", Locale.KOREA);
	         
	         String dTime = formatter.format(systemTime);
	         int time = Integer.parseInt(dTime);
	         time = time - 1;
	         
	         String new_dTime = Integer.toString(time);
	         new_dTime= new_dTime+"00";
//	         System.out.println(new_dTime);
	         /////////////////////////////////////////////////////////////시간구함 new_dTime

	         
	         
	         String insert_pm = "insert into PM (date, region, fineP, ultP) values(?,?,?,?);";
				PreparedStatement pm = connection.prepareStatement(insert_pm);
		         
					
					  
					String APIurl ="http://openAPI.seoul.go.kr:8088/7344787a666a756e383453796f7461/xml/TimeAverageAirQuality/1/25/"+new_dTime;
					  
					
					DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
					  
					  Document doc = dBuilder.parse(APIurl);
					  
					  doc.getDocumentElement().normalize(); 
					  
					  NodeList nList = doc.getElementsByTagName("row");
					  System.out.println("\nRoot element : " +doc.getDocumentElement().getNodeName());
					  System.out.println("리스트 길이: "+ nList.getLength());
					  

					  
					  
					  
					for(int temp = 0; temp < nList.getLength(); temp++){ 
						  Node nNode = nList.item(temp);
					  
						  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					  
							  Element eElement = (Element) nNode;
							  
							  String msrdt = getTagValue("MSRDT", eElement);
			                  String msrste_nm = getTagValue("MSRSTE_NM", eElement);
			                  String pm10 = getTagValue("PM10",eElement);
			                  String pm25 = getTagValue("PM25",eElement);
			                    
			                  pm.setLong(1, Long.parseLong(msrdt));
			                  pm.setString(2, msrste_nm);
			                  pm.setInt(3, Integer.parseInt(pm10));
			                  pm.setInt(4, Integer.parseInt(pm25));
					  
						  } 
						  
						  pm.executeUpdate();
					}
					 

					PreparedStatement q4 = connection.prepareStatement("select * from PM");
					ResultSet res4 = q4.executeQuery();
					
//					System.out.println("[date/region/fineP/ultP]");
//					while(res4.next()) {
//						long date = res4.getLong(1);
//						String region = res4.getString(2);
//						int finep=res4.getInt(3);
//						int ultp=res4.getInt(4);
//
//
//						System.out.println(date+"/"+region+"/"+finep+"/"+ultp);
//					}



            /*************************
             * Query 작성
             *
             * ****************************/

















        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
