import java.sql.*;

import java.sql.SQLException;
import java.util.Scanner;

public class SqlTest2 {

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
                System.out.println("connection fail!");
            }


            System.out.println("Creating College, Student, Apply relations");

            // 3개 테이블 생성: Create table문 이용
            Statement statement = conn.createStatement();

            //이미 생성한 경우 drop
            statement.execute("drop view if exists CSaccept;");
            statement.execute("drop table if exists Apply;");
            statement.execute("drop table if exists College;");
            statement.execute("drop table if exists Student;");

            statement.execute("create table College(cName varchar(20), state char(2), enrollment int);");
            statement.execute("create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int);");
            statement.execute("create table Apply(sID int, cName varchar(20), major varchar(20), decision char);");

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


            /*********************************************************************************
             ****************************** Trigger Test 1 ***********************************
             ********************************************************************************/
            //Query 1 (Trigger test 1)
            System.out.println("\nTrigger test 1 :\n");
            String trigger1_sql = "create or replace function test() returns trigger as $$\n" +
                    "begin\n" +
                    " insert into Apply values(New.sID, 'Stanford', 'geology', null);\n" +
                    " insert into Apply values(New.sID, 'MIT', 'biology', null);\n" +
                    " return New;\n" +
                    "end;\n" +
                    "$$\n" +
                    "language 'plpgsql';\n" +
                    "create trigger R1\n" +
                    "after insert on Student\n" +
                    "for each row\n" +
                    "when (New.GPA > 3.3 and New.GPA <= 3.6)\n" +
                    "execute procedure test();";

            Statement trigger1_st = conn.createStatement();
            trigger1_st.executeUpdate(trigger1_sql);
            trigger1_st.close();

            //Insert문 수행
            PreparedStatement trigger1_insert = conn.prepareStatement(
                    "insert into Student values ('111', 'Kevin', 3.5, 1000);" +
                            "insert into Student values ('222', 'Lori', 3.8, 1000);");

            trigger1_insert.execute();

            //결과값을 위한 Query 1 실행
            PreparedStatement showStudent = conn.prepareStatement("select * from Student;");
            PreparedStatement showApply = conn.prepareStatement("select * from Apply;");

            ResultSet result_1_student = showStudent.executeQuery();
            ResultSet result_1_apply = showApply.executeQuery();

            System.out.println("Student Table");
            while (result_1_student.next()) {
                int sID = result_1_student.getInt(1);
                String sname = result_1_student.getString(2);
                float GPA = result_1_student.getFloat(3);
                int sizeHS = result_1_student.getInt(4);

                System.out.println("sID: " + sID + ", sName: " + sname + ", GPA: " + GPA + ", sizeHS: " + sizeHS);
            }

            System.out.println("\nApply Table");

            while (result_1_apply.next()) {
                int sID = result_1_apply.getInt(1);
                String cname = result_1_apply.getString(2);
                String major = result_1_apply.getString(3);
                String decision = result_1_apply.getString(4);

                System.out.println("sID: " + sID + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            /*********************************************************************************
             ****************************** Trigger Test 2 ***********************************
             ********************************************************************************/
            //Query 2 (Trigger test 2)
            System.out.println("\nTrigger test 2 :");

            String trigger2_sql =
                    "create or replace function test2() returns trigger as $test2$\n" +
                            "begin\n" +
                            "update Apply set cName = New.cName where cName = Old.cName;" +
                            "return New;\n" +
                            "end;" +
                            "$test2$\n" +
                            "language 'plpgsql';\n" +
                            "create trigger R3\n" +
                            "after update of cName on College\n" +
                            "for each row " +
                            "execute procedure test2();";


            PreparedStatement trigger2_st = conn.prepareStatement(trigger2_sql);
            trigger2_st.execute();

            //Update문 실행
            PreparedStatement trigger2_update = conn.prepareStatement(
                    "update College set cName = 'The Farm' where cName = 'Stanford';" +
                            "update College set cName = 'Bezerkeley' where cName = 'Berkeley';");
            trigger2_update.execute();


            PreparedStatement showCollege = conn.prepareStatement("select * from College;");
            ResultSet result_2_college = showCollege.executeQuery();
            ResultSet result_2_apply = showApply.executeQuery();

            System.out.println("College Table");
            while (result_2_college.next()) {
                String cname = result_2_college.getString(1);
                String state = result_2_college.getString(2);
                int enrollment = result_2_college.getInt(3);

                System.out.println("cName: " + cname + ", state: " + state + ", enrollment: " + enrollment);
            }

//            //Trigger 적용 확인을 위한 출력
//            System.out.println("\nApply Table");
//            while (result_2_apply.next()) {
//                int sID = result_2_apply.getInt(1);
//                String cname = result_2_apply.getString(2);
//                String major = result_2_apply.getString(3);
//                String decision = result_2_apply.getString(4);
//
//                System.out.println("sID: " + sID + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
//            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();



        /**************************************RESET************************************************/
            //2) 테이블, 튜플 상태로 리셋
//            PreparedStatement reset = conn.prepareStatement(
//                    "update College set cName = 'Stanford' where cName = 'The Farm';" +
//                            "update College set cName = 'Berkeley' where cName = 'Bezerkeley';" +
//                            "update Apply set cName = 'Berkeley' where cName = 'Bezerkeley';" +
//                            "update Apply set cName = 'Stanford' where cName = 'The Farm';" +
//                            "delete from Apply where sID = '111' or sID = '222';" +
//                            "delete from Student where sID = '111' or sID = '222';"
//                           );
//            reset.execute();

            System.out.println("2) 상태로 RESET");

            //Trigger 비활성화
            statement.execute("alter Table College disable trigger all;");
            statement.execute("alter Table Student disable trigger all;");
            statement.execute("alter Table Apply disable trigger all;");

            //각 Table의 data 삭제
            statement.execute("delete from Apply;");
            statement.execute("delete from College;");
            statement.execute("delete from Student;");

            //몇 개의 데이터 들어갔는 지 확인을 위한 데이터
             nRowsInserted_p1 = 0;
             nRowsInserted_p2 = 0;
             nRowsInserted_p3 = 0;

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

//            System.out.println(String.format("College data : %d", nRowsInserted_p1));

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

//            System.out.println(String.format("Student data : %d", nRowsInserted_p2));

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

//            System.out.println(String.format("Apply data : %d", nRowsInserted_p3));

//            //리셋 확인
//            ResultSet result_rest_a = showApply.executeQuery();
//            ResultSet result_rest_c = showCollege.executeQuery();
//            ResultSet result_rest_S = showStudent.executeQuery();
//
//            System.out.println("Student Table");
//            while (result_rest_S.next()) {
//                int sID = result_rest_S.getInt(1);
//                String sname = result_rest_S.getString(2);
//                float GPA = result_rest_S.getFloat(3);
//                int sizeHS = result_rest_S.getInt(4);
//
//                System.out.println("sID: " + sID + ", sName: " + sname + ", GPA: " + GPA + ", sizeHS: " + sizeHS);
//            }
//
//            System.out.println("\nApply Table");
//
//            while (result_rest_a.next()) {
//                int sID = result_rest_a.getInt(1);
//                String cname = result_rest_a.getString(2);
//                String major = result_rest_a.getString(3);
//                String decision = result_rest_a.getString(4);
//
//                System.out.println("sID: " + sID + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
//            }
//            System.out.println("\nCollege Table");
//            while (result_rest_c.next()) {
//
//                String cname = result_rest_c.getString(1);
//                String state = result_rest_c.getString(2);
//                int enrollment = result_rest_c.getInt(3);
//
//                System.out.println("cName: " + cname + ", state: " + state + ", enrollment: " + enrollment);
//            }

            /*********************************************************************************
             ****************************** View test 1 **************************************
             ********************************************************************************/
            //Query 3 (View test 1)
            System.out.println("\nView test 1 :");

            //View CSaccept 생성
            String view1_sql = "create view CSaccept as select sID, cName from Apply where major = 'CS' and decision = 'Y';";
            PreparedStatement view1_st = conn.prepareStatement(view1_sql);
            view1_st.execute();


            PreparedStatement showCSaccpet = conn.prepareStatement("select * from CSaccept;");
            ResultSet result_3 = showCSaccpet.executeQuery();

            System.out.println("\nCsaccept");
            while (result_3.next()) {
                int sid = result_3.getInt(1);
                String cname = result_3.getString(2);

                System.out.println("sID: " + sid + ", cName: " + cname);
            }


            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            /*********************************************************************************
             ****************************** View test 2 **************************************
             ********************************************************************************/
            //Query 4 (View test 2)
            System.out.println("\nView test 2 :");

            // View CSacceptDelete 생성
            String view2_sql =
                    "create or replace function test3() returns trigger as $test3$\n" +
                            "begin\n" +
                            "delete from Apply where sID = Old.sId and cName = Old.cName and major = 'CS' and decision = 'Y';" +
                            " return New;\n" +
                            "end;" +
                            "$test3$\n" +
                            "language 'plpgsql';\n" +
                            "create trigger CSacceptDelete\n" +
                            "instead of delete on CSaccept\n" +
                            "for each row " +
                            "execute procedure test3();";
            PreparedStatement view2_st = conn.prepareStatement(view2_sql);
            view2_st.execute();

            //Delete문 실행
            PreparedStatement view2_delete = conn.prepareStatement(
                    "delete from CSaccept where sID = 123;");
            view2_delete.execute();

            ResultSet result_4 = showCSaccpet.executeQuery();
            ResultSet result_4_apply = showApply.executeQuery();

            System.out.println("\nCsaccept");
            while (result_4.next()) {
                int sid = result_4.getInt(1);
                String cname = result_4.getString(2);
                System.out.println("sID: " + sid + ", cName: " + cname);
            }

            System.out.println("\nApply Table");
            while (result_4_apply.next()) {
                int sid = result_4_apply.getInt(1);
                String cname = result_4_apply.getString(2);
                String major = result_4_apply.getString(3);
                String decision = result_4_apply.getString(4);
                System.out.println("sID: " + sid + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
            }
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            /*********************************************************************************
             ****************************** View test 3 **************************************
             ********************************************************************************/
            //Query 5 (View test 3)
            System.out.println("\nView test 3 :");

            // View CSacceptUpdate 생성
            String view3_sql =
                    "create or replace function test4() returns trigger as $test4$\n" +
                            "begin\n" +
                            "update Apply set cName = New.cName where sID = Old.sID and cName = Old.cName and major='CS' and decision ='Y';" +
                            " return New;\n" +
                            "end;" +
                            "$test4$\n" +
                            "language 'plpgsql';\n" +
                            "create trigger CSacceptUpdate\n" +
                            "instead of update on CSaccept\n" +
                            "for each row " +
                            "execute procedure test4();";
            PreparedStatement view3_st = conn.prepareStatement(view3_sql);
            view3_st.execute();

            //Update문 실행
            PreparedStatement view3_update = conn.prepareStatement(
                    "update CSaccept set cName = 'CMU' where sID ='345';");
            view3_update.execute();


            ResultSet result_5 = showCSaccpet.executeQuery();
            ResultSet result_5_apply = showApply.executeQuery();

            System.out.println("\nCsaccept");
            while (result_5.next()) {
                int sid = result_5.getInt(1);
                String cname = result_5.getString(2);
                System.out.println("sID: " + sid + ", cName: " + cname);
            }

            System.out.println("\nApply Table");
            while (result_5_apply.next()) {
                int sid = result_5_apply.getInt(1);
                String cname = result_5_apply.getString(2);
                String major = result_5_apply.getString(3);
                String decision = result_5_apply.getString(4);
                System.out.println("sID: " + sid + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
            }
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            scan.close();

        } catch (SQLException ex) {
            throw ex;
        }

    }


}
