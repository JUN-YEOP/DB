import java.sql.*;

import java.sql.SQLException;
import java.util.Scanner;

public class SqlTest {

    public static void main(String[] args) throws SQLException {
        try {

            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");
            System.out.println("Connecting PostgreSQL database");

            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "7363";

            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("connection success!");
            } else {
                System.out.println("connection fali!");
            }


            System.out.println("Creating College, Student, Apply relations");

            // 3개 테이블 생성: Create table문 이용
            Statement statement = conn.createStatement();

            //이미 생성한 경우 drop
            statement.execute("drop table if exists Apply;");
            statement.execute("drop table if exists College;");
            statement.execute("drop table if exists Student;");

            statement.execute("create table College(cName varchar(20), state char(2), enrollment int, primary key(cName));");
            statement.execute("create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int, primary key(sID));");
            statement.execute("create table Apply(sID int, cName varchar(20), major varchar(20), decision char,primary key(sID,cName,major),foreign key(sID) references Student(sID),foreign key(cName) references College(cName));");

            System.out.println("Inserting tuples to College, Student, Apply relations");

            // 3개 테이블에 튜플 생성: Insert문 이용
            PreparedStatement p1 = conn.prepareStatement("insert into College (cName, state, enrollment) values (?,?,?);");
            PreparedStatement p2 = conn.prepareStatement("insert into Student (sID, sName, GPA, sizeHS) values (?,?,?,?);");
            PreparedStatement p3 = conn.prepareStatement("insert into Apply (sID, cName, major, decision) values (?,?,?,?);");

            //몇 개의 데이터 들어갔는 지 확인을 위한 데이터
            int nRowsInserted_p1 = 0;
            int nRowsInserted_p2 = 0;
            int nRowsInserted_p3 = 0;

            //college 삽입
            p1.setString(1, "Stanford");
            p1.setString(2, "CA");
            p1.setInt(3, 15000);
            nRowsInserted_p1 += p1.executeUpdate();

            p1.setString(1, "Berkeley");
            p1.setString(2, "CA");
            p1.setInt(3, 36000);
            nRowsInserted_p1 += p1.executeUpdate();

            p1.setString(1, "MIT");
            p1.setString(2, "MA");
            p1.setInt(3, 10000);
            nRowsInserted_p1 += p1.executeUpdate();

            p1.setString(1, "Cornell");
            p1.setString(2, "NY");
            p1.setInt(3, 21000);
            nRowsInserted_p1 += p1.executeUpdate();

            System.out.println(String.format("College data : %d", nRowsInserted_p1));

            //student 삽입
            p2.setInt(1, 123);
            p2.setString(2, "Amy");
            p2.setDouble(3, 3.9);
            p2.setInt(4, 1000);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 234);
            p2.setString(2, "Bob");
            p2.setDouble(3, 3.6);
            p2.setInt(4, 1500);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 345);
            p2.setString(2, "Craig");
            p2.setDouble(3, 3.5);
            p2.setInt(4, 500);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 456);
            p2.setString(2, "Doris");
            p2.setDouble(3, 3.9);
            p2.setInt(4, 1000);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 567);
            p2.setString(2, "Edward");
            p2.setDouble(3, 2.9);
            p2.setInt(4, 2000);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 678);
            p2.setString(2, "Fay");
            p2.setDouble(3, 3.8);
            p2.setInt(4, 200);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 789);
            p2.setString(2, "Gary");
            p2.setDouble(3, 3.4);
            p2.setInt(4, 800);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 987);
            p2.setString(2, "Helen");
            p2.setDouble(3, 3.7);
            p2.setInt(4, 800);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 876);
            p2.setString(2, "Irene");
            p2.setDouble(3, 3.9);
            p2.setInt(4, 400);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 765);
            p2.setString(2, "Jay");
            p2.setDouble(3, 2.9);
            p2.setInt(4, 1500);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 654);
            p2.setString(2, "Amy");
            p2.setDouble(3, 3.9);
            p2.setInt(4, 1000);
            nRowsInserted_p2 += p2.executeUpdate();

            p2.setInt(1, 543);
            p2.setString(2, "Craig");
            p2.setDouble(3, 3.4);
            p2.setInt(4, 2000);
            nRowsInserted_p2 += p2.executeUpdate();

            System.out.println(String.format("Student data : %d", nRowsInserted_p2));

            //apply 삽입
            p3.setInt(1, 123);
            p3.setString(2, "Stanford");
            p3.setString(3, "CS");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 123);
            p3.setString(2, "Stanford");
            p3.setString(3, "EE");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 123);
            p3.setString(2, "Berkeley");
            p3.setString(3, "CS");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 123);
            p3.setString(2, "Cornell");
            p3.setString(3, "EE");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 234);
            p3.setString(2, "Berkeley");
            p3.setString(3, "biology");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 345);
            p3.setString(2, "MIT");
            p3.setString(3, "bioengineering");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 345);
            p3.setString(2, "Cornell");
            p3.setString(3, "bioengineering");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 345);
            p3.setString(2, "Cornell");
            p3.setString(3, "CS");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 345);
            p3.setString(2, "Cornell");
            p3.setString(3, "EE");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 678);
            p3.setString(2, "Stanford");
            p3.setString(3, "history");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 987);
            p3.setString(2, "Stanford");
            p3.setString(3, "CS");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 987);
            p3.setString(2, "Berkeley");
            p3.setString(3, "CS");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 876);
            p3.setString(2, "Stanford");
            p3.setString(3, "CS");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 876);
            p3.setString(2, "MIT");
            p3.setString(3, "Biology");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 876);
            p3.setString(2, "MIT");
            p3.setString(3, "marine biology");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 765);
            p3.setString(2, "Stanford");
            p3.setString(3, "history");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 765);
            p3.setString(2, "Cornell");
            p3.setString(3, "history");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 765);
            p3.setString(2, "Cornell");
            p3.setString(3, "psychology");
            p3.setString(4, "Y");
            nRowsInserted_p3 += p3.executeUpdate();

            p3.setInt(1, 543);
            p3.setString(2, "MIT");
            p3.setString(3, "CS");
            p3.setString(4, "N");
            nRowsInserted_p3 += p3.executeUpdate();

            System.out.println(String.format("Apply data : %d", nRowsInserted_p3));

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            //Query1
            System.out.println("Query 1 :");
            PreparedStatement queryone = conn.prepareStatement("select * from College;");
            ResultSet result_1 = queryone.executeQuery();

            while (result_1.next()) {
                String cname = result_1.getString(1);
                String st = result_1.getString(2);
                int enroll = result_1.getInt(3);

                System.out.println("cName: " + cname + ", state: " + st + ", enrollment: " + enroll);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query2
            System.out.println("Query 2 :");
            PreparedStatement querytwo = conn.prepareStatement("select * from Student;");
            ResultSet result_2 = querytwo.executeQuery();

            while (result_2.next()) {
                int sid = result_2.getInt(1);
                String sname = result_2.getString(2);
                double gpa = result_2.getDouble(3);
                int sizehs = result_2.getInt(4);

                System.out.println("sID: " + sid + ", sName: " + sname + ", GPA: " + gpa + ", sizeHS: " + sizehs);
            }


            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            //Query3
            System.out.println("Query 3 :");

            PreparedStatement querythree = conn.prepareStatement("select * from Apply;");
            ResultSet result_3 = querythree.executeQuery();

            while (result_3.next()) {
                int sid = result_3.getInt(1);
                String cname = result_3.getString(2);
                String major = result_3.getString(3);
                String dec = result_3.getString(4);

                System.out.println("sID: " + sid + ", cName: " + cname + ", major: " + major + ", decision: " + dec);
            }


            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query4
            System.out.println("Query 4 :");

            PreparedStatement queryfour = conn.prepareStatement("select * from Student S1 where (select count(*) from Student S2 where S2.sID <> S1.sID and S2.GPA = S1.GPA) = (select count(*) from Student S2 where S2.sID <> S1.sID and S2.sizeHS = S1.sizeHS);");
            ResultSet result_4 = queryfour.executeQuery();

            while (result_4.next()) {
                int sid = result_4.getInt(1);
                String sname = result_4.getString(2);
                double gpa = result_4.getDouble(3);
                int sizehs = result_4.getInt(4);

                System.out.println("sID: " + sid + ", sName: " + sname + ", GPA: " + gpa + ", sizeHs: " + sizehs);
            }


            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query5 시작
            System.out.println("Query 5 :");

            PreparedStatement queryfive = conn.prepareStatement("select Student.sID, sName, count(distinct cName) from Student, Apply where Student.sID = Apply.sID group by Student.sID, sName;");
            ResultSet result_5 = queryfive.executeQuery();

            while (result_5.next()) {
                int sid = result_5.getInt(1);
                String sname = result_5.getString(2);
                int count = result_5.getInt(3);

                System.out.println("sID: " + sid + ", sName: " + sname + ", count(distinct cName): " + count);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query6 시작
            System.out.println("Query 6 :");

            PreparedStatement querysix = conn.prepareStatement("select major from Student, Apply where Student.sID = Apply.sID group by major having max(GPA) < (select avg(GPA) from Student);");
            ResultSet result_6 = querysix.executeQuery();

            while (result_6.next()) {
                String major = result_6.getString(1);

                System.out.println("major: " + major);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            //Query7 시작
            System.out.println("Query 7 :");

            PreparedStatement queryseven = conn.prepareStatement("select sName, GPA from Student join Apply on Student.sID = Apply.sID where sizeHS < ? and major = ? and cName = ?;");

            //사용자로부터 데이터 입력받음
            System.out.printf("Enter sizeHS: ");
            int sizehs = scan.nextInt();

            System.out.printf("Enter major: ");
            String major = scan.next();

            System.out.printf("Enter cname: ");
            String cname = scan.next();

            queryseven.setInt(1, sizehs);
            queryseven.setString(2, major);
            queryseven.setString(3, cname);

            ResultSet result_7 = queryseven.executeQuery();

            while (result_7.next()) {
                String sname = result_7.getString(1);
                double gpa = result_7.getDouble(2);

                System.out.println("sName: " + sname + ", GPA: " + gpa);
            }
            scan.close();
        } catch (SQLException ex) {
            throw ex;
        }

    }


}
