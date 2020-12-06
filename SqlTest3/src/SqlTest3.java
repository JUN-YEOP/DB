import java.sql.*;

import java.sql.SQLException;
import java.util.Scanner;


public class SqlTest3 {


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



            /*********************************************************************************
             ****************************** Recursive test 1 ***********************************
             ********************************************************************************/
            //Query 1 (Recursive test 1)
            System.out.println("\nRecursive test 1 :\n");
            String recursive1_sql = "with recursive Ancestor(a,d) as ( select parent as a, child as d from Parentof union select Ancestor.a, ParentOf.child as d from Ancestor, ParentOf where Ancestor.d = ParentOf.parent) select a from Ancestor where d = 'Mary'";

            Statement recursive1_st = conn.createStatement();
            recursive1_st.execute(recursive1_sql);
            recursive1_st.close();

            // Query 1 Display
            PreparedStatement showParentOf = conn.prepareStatement("select * from ParentOf;");
            PreparedStatement showA = conn.prepareStatement(recursive1_sql);

            ResultSet result_1_parentof = showParentOf.executeQuery();
            ResultSet result_1_a = showA.executeQuery();

            int count_parentof = 0;
            int count_a = 0;

            System.out.println("<ParentOf>");

            while (result_1_parentof.next()) {
                String parent = result_1_parentof.getString(1);
                String child = result_1_parentof.getString(2);

                System.out.println(++count_parentof + " parent: " + parent + ", child: " + child);
            }

            System.out.println("\n<Result 1>");

            while (result_1_a.next()) {
                String a = result_1_a.getString(1);

                System.out.println(++count_a + " a: " + a);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            /*********************************************************************************
             ****************************** Recursive test 2 ***********************************
             ********************************************************************************/
            //Query 2 (Recursive test 2)
            System.out.println("\nRecursive test 2 :");
            String recursive2_sql = "with recursive Route(orig,dest,total) as ( select orig, dest, cost as total from Flight union select R.orig, F.dest, cost+total as total from Route R, Flight F where R.dest = F.orig) select * from Route where orig = 'A' and dest = 'B'";

            Statement recursive2_st = conn.createStatement();
            recursive2_st.execute(recursive2_sql);
            recursive2_st.close();

            // Query 2 Display
            PreparedStatement showFlight = conn.prepareStatement("select * from Flight;");
            PreparedStatement showResult2 = conn.prepareStatement(recursive2_sql);

            ResultSet result_2_flight = showFlight.executeQuery();
            ResultSet result_2 = showResult2.executeQuery();

            int count_flight = 0;
            int count_result2 = 0;

            System.out.println("<Flight>");

            while (result_2_flight.next()) {
                String orig = result_2_flight.getString(1);
                String dest = result_2_flight.getString(2);
                String airline = result_2_flight.getString(3);
                int cost = result_2_flight.getInt(4);

                System.out.println(++count_flight + " orig: " + orig + ", dest: " + dest + ", airline: " + airline + ", cost: " + cost);
            }

            System.out.println("\n<Result 2>");

            while (result_2.next()) {
                String orig = result_2.getString(1);
                String dest = result_2.getString(2);
                int total = result_2.getInt(3);

                System.out.println(++count_result2 + " orig: " + orig + ", dest: " + dest + ", total: " + total);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            /*********************************************************************************
             ****************************** Recursive test 3 ***********************************
             ********************************************************************************/
            //Query 3 (Recursive test 3)
            System.out.println("\nRecursive test 3 :");
            String recursive3_sql = "with recursive ToB(orig,total) as ( select orig, cost as total from Flight where dest = 'B' union select F.orig, cost+total as total from Flight F, ToB TB where F.dest = TB.orig) select min(total) from ToB where orig = 'A'";

            Statement recursive3_st = conn.createStatement();
            recursive3_st.execute(recursive3_sql);
            recursive3_st.close();


            // Query 3 Display
            PreparedStatement showResult3 = conn.prepareStatement(recursive3_sql);

            ResultSet result_3_flight = showFlight.executeQuery();
            ResultSet result_3 = showResult3.executeQuery();

            int count_flight2 = 0;
            int count_result3 = 0;

            System.out.println("<Flight>");
            while (result_3_flight.next()) {
                String orig = result_3_flight.getString(1);
                String dest = result_3_flight.getString(2);
                String airline = result_3_flight.getString(3);
                int cost = result_3_flight.getInt(4);

                System.out.println(++count_flight2 + " orig: " + orig + ", dest: " + dest + ", airline: " + airline + ", cost: " + cost);
            }

            System.out.println("\n<Result 3>");

            while (result_3.next()) {
                int min = result_3.getInt(1);

                System.out.println(++count_result3 + " min: " + min);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            /*********************************************************************************
             ****************************** OLAP test 1 ***********************************
             ********************************************************************************/
            //Query 4 (OLAP test 1)
            System.out.println("\nOLAP test 1 :");
            String OLAP1_sql = "select storeID, itemID, custID, sum(price) from Sales group by cube (storeID, itemID, custID) order by storeID, itemID, custID;";

            Statement OLAP1_st = conn.createStatement();
            OLAP1_st.execute(OLAP1_sql);
            OLAP1_st.close();

            // Query 4 Display
            PreparedStatement showResult4 = conn.prepareStatement(OLAP1_sql);


            ResultSet result_4 = showResult4.executeQuery();

            int count_4 = 0;

            System.out.println("<Result 4>");
            while (result_4.next()) {
                String storeid = result_4.getString(1);
                String itemid = result_4.getString(2);
                String custid = result_4.getString(3);
                int sum = result_4.getInt(4);

                System.out.println(++count_4 + " storeID: " + storeid + ", itemID: " + itemid + ", custID: " + custid + ", sum: " + sum);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            /*********************************************************************************
             ****************************** OLAP test 2 ***********************************
             ********************************************************************************/
            //Query 5 (OLAP test 2)
            System.out.println("\nOLAP test 2 :");
            String OLAP2_sql = "select storeID, itemID, custID, sum(price) from Sales F group by (storeID, itemID, custID), cube (storeID, custID);";

            Statement OLAP2_st = conn.createStatement();
            OLAP2_st.execute(OLAP2_sql);
            OLAP2_st.close();

            // Query 5 Display
            PreparedStatement showResult5 = conn.prepareStatement(OLAP2_sql);


            ResultSet result_5 = showResult5.executeQuery();

            int count_5 = 0;

            System.out.println("<Result 5>");
            while (result_5.next()) {
                String storeid = result_5.getString(1);
                String itemid = result_5.getString(2);
                String custid = result_5.getString(3);
                int sum = result_5.getInt(4);

                System.out.println(++count_5 + " storeID: " + storeid + ", itemID: " + itemid + ", custID: " + custid + ", sum: " + sum);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();


            /*********************************************************************************
             ****************************** OLAP test 3 ***********************************
             ********************************************************************************/
            //Query 5 (OLAP test 3)
            System.out.println("\nOLAP test 3 :");
            String OLAP3_sql = "select storeID, itemID, custID, sum(price) from Sales F group by rollup (storeID, itemID, custID) ;";

            Statement OLAP3_st = conn.createStatement();
            OLAP3_st.execute(OLAP3_sql);
            OLAP3_st.close();

            // Query 6 Display
            PreparedStatement showResult6 = conn.prepareStatement(OLAP3_sql);


            ResultSet result_6 = showResult6.executeQuery();

            int count_6 = 0;

            System.out.println("<Result 6>");
            while (result_6.next()) {
                String storeid = result_6.getString(1);
                String itemid = result_6.getString(2);
                String custid = result_6.getString(3);
                int sum = result_6.getInt(4);

                System.out.println(++count_6 + " storeID: " + storeid + ", itemID: " + itemid + ", custID: " + custid + ", sum: " + sum);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
















//
//            /*********************************************************************************
//             ****************************** View test 1 **************************************
//             ********************************************************************************/
//            //Query 3 (View test 1)
//            System.out.println("\nView test 1 :");
//
//            //View CSaccept 생성
//            String view1_sql = "create view CSaccept as select sID, cName from Apply where major = 'CS' and decision = 'Y';";
//            PreparedStatement view1_st = conn.prepareStatement(view1_sql);
//            view1_st.execute();
//
//
//            PreparedStatement showCSaccpet = conn.prepareStatement("select * from CSaccept;");
//            ResultSet result_3 = showCSaccpet.executeQuery();
//
//            System.out.println("\nCsaccept");
//            while (result_3.next()) {
//                int sid = result_3.getInt(1);
//                String cname = result_3.getString(2);
//
//                System.out.println("sID: " + sid + ", cName: " + cname);
//            }
//
//
//            System.out.println("Continue? (Enter 1 for continue)");
//            scan.nextLine();
//
//
//            /*********************************************************************************
//             ****************************** View test 2 **************************************
//             ********************************************************************************/
//            //Query 4 (View test 2)
//            System.out.println("\nView test 2 :");
//
//            // View CSacceptDelete 생성
//            String view2_sql =
//                    "create or replace function test3() returns trigger as $test3$\n" +
//                            "begin\n" +
//                            "delete from Apply where sID = Old.sId and cName = Old.cName and major = 'CS' and decision = 'Y';" +
//                            " return New;\n" +
//                            "end;" +
//                            "$test3$\n" +
//                            "language 'plpgsql';\n" +
//                            "create trigger CSacceptDelete\n" +
//                            "instead of delete on CSaccept\n" +
//                            "for each row " +
//                            "execute procedure test3();";
//            PreparedStatement view2_st = conn.prepareStatement(view2_sql);
//            view2_st.execute();
//
//            //Delete문 실행
//            PreparedStatement view2_delete = conn.prepareStatement(
//                    "delete from CSaccept where sID = 123;");
//            view2_delete.execute();
//
//            ResultSet result_4 = showCSaccpet.executeQuery();
//            ResultSet result_4_apply = showApply.executeQuery();
//
//            System.out.println("\nCsaccept");
//            while (result_4.next()) {
//                int sid = result_4.getInt(1);
//                String cname = result_4.getString(2);
//                System.out.println("sID: " + sid + ", cName: " + cname);
//            }
//
//            System.out.println("\nApply Table");
//            while (result_4_apply.next()) {
//                int sid = result_4_apply.getInt(1);
//                String cname = result_4_apply.getString(2);
//                String major = result_4_apply.getString(3);
//                String decision = result_4_apply.getString(4);
//                System.out.println("sID: " + sid + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
//            }
//            System.out.println("Continue? (Enter 1 for continue)");
//            scan.nextLine();
//
//
//            /*********************************************************************************
//             ****************************** View test 3 **************************************
//             ********************************************************************************/
//            //Query 5 (View test 3)
//            System.out.println("\nView test 3 :");
//
//            // View CSacceptUpdate 생성
//            String view3_sql =
//                    "create or replace function test4() returns trigger as $test4$\n" +
//                            "begin\n" +
//                            "update Apply set cName = New.cName where sID = Old.sID and cName = Old.cName and major='CS' and decision ='Y';" +
//                            " return New;\n" +
//                            "end;" +
//                            "$test4$\n" +
//                            "language 'plpgsql';\n" +
//                            "create trigger CSacceptUpdate\n" +
//                            "instead of update on CSaccept\n" +
//                            "for each row " +
//                            "execute procedure test4();";
//            PreparedStatement view3_st = conn.prepareStatement(view3_sql);
//            view3_st.execute();
//
//            //Update문 실행
//            PreparedStatement view3_update = conn.prepareStatement(
//                    "update CSaccept set cName = 'CMU' where sID ='345';");
//            view3_update.execute();
//
//
//            ResultSet result_5 = showCSaccpet.executeQuery();
//            ResultSet result_5_apply = showApply.executeQuery();
//
//            System.out.println("\nCsaccept");
//            while (result_5.next()) {
//                int sid = result_5.getInt(1);
//                String cname = result_5.getString(2);
//                System.out.println("sID: " + sid + ", cName: " + cname);
//            }
//
//            System.out.println("\nApply Table");
//            while (result_5_apply.next()) {
//                int sid = result_5_apply.getInt(1);
//                String cname = result_5_apply.getString(2);
//                String major = result_5_apply.getString(3);
//                String decision = result_5_apply.getString(4);
//                System.out.println("sID: " + sid + ", cName: " + cname + ", major: " + major + ", decision: " + decision);
//            }
//            System.out.println("Continue? (Enter 1 for continue)");
//            scan.nextLine();
            scan.close();

        } catch (SQLException ex) {
            throw ex;
        }

    }


}
