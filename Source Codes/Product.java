import java.sql.*;
import DbInfo.*;
import java.util.Scanner;
import java.io.*;

class Product extends Entity {

    private String url = info.getUrl();
    private String password = info.getPassword();
    private String username = info.getUsername();

    @Override
    public void importData() throws FileNotFoundException, SQLException {

        Connection con = DriverManager.getConnection(url,username,password);
        Statement st = con.createStatement();

        Scanner s = new Scanner(new BufferedReader(new FileReader("Tables/Product.csv")));
        String query ;
        String temp;

        while(s.hasNext()){
            temp = s.nextLine();
            query = "insert into Product values (" + temp + ")";
            st.executeUpdate(query);
        }

        System.out.println("Data imported in table.");

        s.close();
        st.close();
        con.close();
    }

    @Override
    public void add(String[] args) throws ClassNotFoundException, SQLException {

        if (args[1].equals("Product")) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String query = "insert into Product values('" + args[2] + "','" + args[3] + "','" + args[4] + "','"
                    + args[5] + "','" + args[6] + "')";

            Statement st = con.createStatement();
            int value = st.executeUpdate(query);
            // System.out.println("Rows Added Successfully");
            System.out.println(value + " row/s added");

            st.close();
            con.close();

        } else {
            System.out.println("\nUnknown Error.Please Check your Syntax\n");
        }

    }

    @Override
    public void search(String args[]) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        String query;

        if (args[1].equals("Product")) {
            if (args[2].equals("-on") && args[3].equals("-all")) {
                query = "select * from Product";
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    System.out.printf("%-13s %-22s %-13s %-13s %-13s\n", rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5));
                }

                st.close();
                con.close();
            } else if (args[2].equals("-on")) {

                String att = new String();
                String op = new String();
                String val = new String();

                att = args[3];
                op = args[4];
                val = args[5];
                query = "select * from Product where " + att;

                if (op.equals("-gt")) {
                    query = query + " > " + val;
                } else if (op.equals("-gte")) {
                    query = query + " >= " + val;
                } else if (op.equals("-ls")) {
                    query = query + " < " + val;
                } else if (op.equals("-lse")) {
                    query = query + " <= " + val;
                } else if (op.equals("=")) {
                    query = query + " = " + val;
                } else if (op.equals("like")) {
                    query = query + " like '" + val + "'";
                } else {
                    System.out.println("INVALID OPERATION");
                }

                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    System.out.printf("%-13s %-22s %-13s %-13s %-13s\n", rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5));
                }
            } else if (args[2].equals("-get")) {
                int i = 3, dump = 3;
                String[] temp = new String[6];

                while (!args[i].equals("-on")) {
                    temp[i - 3] = args[i];
                    i++;
                }
                int j = i + 1;

                if (args[j].equals("-all")) {
                    query = "select";
                    while (dump < i) {
                        if (dump != i - 1)
                            query = query + " " + temp[dump - 3] + ",";

                        if (dump == i - 1)
                            query = query + " " + temp[dump - 3];

                        dump++;
                    }
                    query = query + " " + "from Product";
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        for (i = 0; i < j - 4; i++) {
                            System.out.printf("%-20s ", rs.getString(temp[i]));
                        }
                        System.out.println();
                    }
                }

                else {
                    String att = new String();
                    String op = new String();
                    String val = new String();
                    dump = 3;
                    att = args[j];
                    op = args[j + 1];
                    val = args[j + 2];
                    query = "select";
                    while (dump < i) {
                        if (dump != i - 1)
                            query = query + " " + temp[dump - 3] + ",";

                        if (dump == i - 1)
                            query = query + " " + temp[dump - 3];

                        dump++;
                    }
                    query = query + " " + "from Product where " + att;

                    if (op.equals("-gt"))
                        query = query + " > " + val;

                    else if (op.equals("-gte"))
                        query = query + " >= " + val;

                    else if (op.equals("-ls"))
                        query = query + " < " + val;

                    else if (op.equals("-lse"))
                        query = query + " <= " + val;

                    else if (op.equals("="))
                        query = query + " = " + val;

                    else if (op.equals("like"))
                        query = query + " like '" + val + "'";

                    else
                        System.out.println("Please Use valid Operator");

                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        for (i = 0; i < j - 4; i++) {
                            System.out.printf("%-20s ", rs.getString(temp[i]));
                        }
                        System.out.println();
                    }

                }

            } else {
                System.out.println("Unknown Error.Please Check your Syntax");
            }

        }

        st.close();
        con.close();
    }

    @Override
    public void delete(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String query;
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        if (args[1].equals("Product") && args[2].equals("-on")) {
            if (args[3].equals("-all"))
                query = "delete from Product";

            else {
                String att = args[3];
                String op = args[4];
                String val = args[5];
                query = "delete from Product where " + att;
                if (op.equals("-gt"))
                    query = query + " > " + val;

                else if (op.equals("-gte"))
                    query = query + " >= " + val;

                else if (op.equals("-ls"))
                    query = query + " < " + val;

                else if (op.equals("-lse"))
                    query = query + " <= " + val;

                else if (op.equals("="))
                    query = query + " = " + val;

                else if (op.equals("like")) {
                    query = query + " like '" + val + "'";
                }

            }
            int value = st.executeUpdate(query);
            System.out.println(value + " row/s deleted");

            st.close();
            con.close();
        }

    }

    @Override
    public void update(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();
        String query = "update Product set ";

        if (args[1].equals("Product") && args[2].equals("-set")) {
            String att1 = new String();
            String update_to = new String();
            att1 = args[3];
            if (args[4].equals("=")) {
                update_to = args[5];
                if (args[6].equals("-on")) {
                    if (args[7].equals("-all")) {
                        query = query + att1 + "=" + update_to;
                    } else {
                        String att2 = new String();
                        String op = new String();
                        String val = new String();
                        att2 = args[7];
                        op = args[8];
                        val = args[9];
                        query = query + att1 + "=" + update_to + " where " + att2;
                        if (op.equals("-gt"))
                            query = query + " > " + val;

                        else if (op.equals("-gte"))
                            query = query + " >= " + val;

                        else if (op.equals("-ls"))
                            query = query + " < " + val;

                        else if (op.equals("-lse"))
                            query = query + " <= " + val;

                        else if (op.equals("="))
                            query = query + " = " + val;

                        else if (op.equals("like")) {
                            query = query + " like '" + val + "'";
                        }

                        else
                            System.out.println("\nPlease use valid Operation\n");

                    }
                } else {
                    System.out.println("\nPlease Use valid Operation\n");
                }

            } else {
                System.out.println("\nOperation is not Valid.Please Check your syntax\n");
            }

        }

        int value = st.executeUpdate(query);
        System.out.println(value + " row/s updated");
        st.close();
        con.close();
    }

}
