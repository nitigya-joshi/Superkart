import java.sql.*;
import DbInfo.*;
import java.util.Scanner;
import java.io.*;

public class Orders extends Entity {
    private String orderID;
    private String orderDate;
    private String customerID;
    private double totalAmount;

    private static String url = info.getUrl();
    private static String password = info.getPassword();
    private static String username = info.getUsername();

    private static Connection load() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection created Successfully.");
            return con;
        }

        catch (Exception e) {
            System.out.println("Error while registering JDBC driver.");
            return null;
        }
    }

    @Override
    public void importData() throws FileNotFoundException, SQLException {

        Connection con = DriverManager.getConnection(url,username,password);
        Statement st = con.createStatement();

        Scanner s = new Scanner(new BufferedReader(new FileReader("Tables/Orders.csv")));
        String query ;
        String temp;

        while(s.hasNext()){
            temp = s.nextLine();
            query = "insert into Orders values (" + temp + ")";
            st.executeUpdate(query);
        }

        System.out.println("Data imported in table.");

        s.close();
        st.close();
        con.close();
    }

    @Override
    public void add(String[] args) {
        if (args.length < 6) {
            System.out.println("Error : Input for all attributes not given or there is a mistake.");
            return;
        }

        else if (args.length > 6) {
            System.out.println("Error : You have given more input than required. Try again.");
            return;
        }

        orderID = args[2];
        orderDate = args[3];
        customerID = args[4];
        totalAmount = Double.parseDouble(args[5]);

        Connection con = null;
        Statement s = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = load();

            String query_1 = "SELECT * FROM ORDERS WHERE OrderId = '" + orderID + "'";
            s = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(query_1);

            if (rs.next()) {
                rs.updateString(2, orderDate);
                rs.updateString(3, customerID);
                rs.updateDouble(4, totalAmount);
                rs.updateRow();

                System.out.println("Duplicate order id found. Previous order data was updated.");
            }

            else {
                String query_2 = "INSERT INTO ORDERS VALUES (?, ?, ?, ?)";
                st = con.prepareStatement(query_2);
                st.setString(1, orderID);
                st.setString(2, orderDate);
                st.setString(3, customerID);
                st.setDouble(4, totalAmount);
                st.executeUpdate();

                System.out.println("Order added successfuly.");
            }

        }

        catch (SQLException e) {
            System.out.println(
                    "SQL exception found.\nLocation : Orders.java/add/try.\nDetails :-\n-------------------------------------------");
            e.printStackTrace();
        }

        finally {
            try {
                if (s != null)
                    s.close();
                if (st != null)
                    st.close();
                if (con != null)
                    con.close();
                if (rs != null)
                    rs.close();
            }

            catch (SQLException e) {
                System.out.println(
                        "SQL exception found.\nLocation : Orders.java/add/finally.\nDetails :-\n-------------------------------------------");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String[] args) {
        Connection con = null;
        Statement st = null;

        try {
            con = load();
            st = con.createStatement();
            String query = "DELETE FROM ORDERS";

            if (args[3].equals("-all") || args[3].equalsIgnoreCase("TotalAmount")
                    || args[3].equalsIgnoreCase("OrderId")) {

                if (args[3].equalsIgnoreCase("OrderId"))
                    query += " WHERE OrderId = " + args[5];

                else if (args[3].equalsIgnoreCase("TotalAmount")) {
                    query += " WHERE TotalAmount";

                    if (args[4].equals("-gt"))
                        query += " > ";

                    else if (args[4].equals("-gte"))
                        query += " >= ";

                    else if (args[4].equals("-ls"))
                        query += " < ";

                    else if (args[4].equals("-lse"))
                        query += " <= ";

                    else
                        query += " = ";

                    query += args[5];
                }

                int count = st.executeUpdate(query);
                System.out.printf("%d rows deleted from orders table.", count);
            }

            else
                System.out.println("Error : Input given wrong. See the syntax and try again.");
        }

        catch (SQLException e) {
            System.out.println(
                    "SQL exception found.\nLocation : Orders.java/delete/try.\nDetails :-\n-------------------------------------------");
            e.printStackTrace();
        }

        finally {
            try {
                if (st != null)
                    st.close();
                if (con != null)
                    con.close();
            }

            catch (SQLException e) {
                System.out.println(
                        "SQL exception found.\nLocation : Orders.java/delete/finally.\nDetails :-\n-------------------------------------------");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "update Orders set ";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        if (args[0].equals("-update") && args[1].equals("Orders") && args[2].equals("-set")) {
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
                        } else
                            System.out.println("\nINVALID OPERATION\n");

                    }

                } else {
                    System.out.println("\nINVALID OPERATION\n");
                }

            } else {
                System.out.println("\nINVALID OPERATION\n");
            }

        }

        int count = st.executeUpdate(query);
        System.out.printf("%d rows udated in orders table.", count);
        st.close();
        con.close();
    }

    @Override
    public void search(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ResultSetMetaData rmd = null;
        String query = "";

        try {
            con = load();
            st = con.createStatement();

            if (args[3].equals("-all")) {
                query += "SELECT * FROM ORDERS ";

                if (args.length > 4) {
                    if (args[4].equals("-sort")) {
                        query += "ORDER BY " + args[5];

                        if (args.length > 6 && args[6].equals("-desc"))
                            query += " DESC";
                    }
                }

                rs = st.executeQuery(query);

                System.out.println("\nOrder Id\t    Order Date\t\t Customer Id\t     Total Amount\n" +
                                    "-----------------------------------------------------------------------------------\n");

                while (rs.next()) {
                    System.out.printf("%-20s %-20s %-20s %-20s\n\n", rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getDouble(4));
                }
            } else if (args[2].equals("-on")) {
                query = "SELECT ";

                if (args[3].equals("-agg")) {
                    if (args.length <= 6) {
                        query += args[4].toUpperCase() + "(" + args[5] + ") FROM ORDERS";
                    }

                    rs = st.executeQuery(query);
                    rs.next();
                    System.out.println(args[4].toUpperCase() + " = " + rs.getString(1));
                }

                else {
                    int i = 2;

                    query += "* FROM ORDERS";

                    if (args[i + 1].compareTo("-all") != 0) {
                        if (args[i + 1].equals("TotalAmount")) {
                            query += " WHERE TotalAmount";

                            if (args[i + 2].equals("-gt"))
                                query += " > ";

                            else if (args[i + 2].equals("-gte"))
                                query += " >= ";

                            else if (args[i + 2].equals("-ls"))
                                query += " < ";

                            else if (args[i + 2].equals("-lse"))
                                query += " <= ";

                            else
                                query += " = ";

                            query += args[i + 3];
                        }

                        else {
                            query += " WHERE " + args[i + 1] + " = '" + args[i + 3] + "'";
                        }
                    }
                    System.out.println(query);
                    rs = st.executeQuery(query);
                    rmd = rs.getMetaData();
                    int columns = rmd.getColumnCount();

                    while (rs.next()) {
                        for (int j = 1; j < columns; j++)
                            System.out.printf("%-20s", rs.getString(j));

                        System.out.println();
                    }
                }
            }

            else if (args[2].equals("-get")) {
                query = "SELECT ";

                if (args[3].equals("-agg")) {
                    if (args.length <= 6) {
                        query += args[4].toUpperCase() + "(" + args[5] + ") FROM ORDERS";
                    }

                    rs = st.executeQuery(query);
                    rs.next();
                    System.out.println(args[4].toUpperCase() + "\n---------\n" + (rs.getDouble(1)));
                }

                else {
                    int i = 4;
                    query += args[3];

                    while (args[i].compareTo("-on") != 0) {
                        query += ", " + args[i];
                        i++;
                    }
                    query += " FROM ORDERS";

                    if (args[i + 1].compareTo("-all") != 0) {
                        if (args[i + 1].equals("TotalAmount")) {
                            query += " WHERE TotalAmount";

                            if (args[i + 2].equals("-gt"))
                                query += " > ";

                            else if (args[i + 2].equals("-gte"))
                                query += " >= ";

                            else if (args[i + 2].equals("-ls"))
                                query += " < ";

                            else if (args[i + 2].equals("-lse"))
                                query += " <= ";

                            else
                                query += " = ";

                            query += args[i + 3];
                        }

                        else {
                            query += " WHERE " + args[i + 1] + " = '" + args[i + 3] + "'";
                        }
                    }

                    if (args.length > 7) {
                        if (args[7].equals("-sort")) {
                            query += " ORDER BY " + args[8];

                            if (args.length > 9 && args[9].equals("-desc"))
                                query += " DESC";
                        }
                    }

                    rs = st.executeQuery(query);
                    rmd = rs.getMetaData();
                    int columns = rmd.getColumnCount();

                    while (rs.next()) {
                        for (int j = 1; j <= columns; j++)
                            System.out.printf("%-20s", rs.getString(j));

                        System.out.println();
                    }
                }
            }

            else {
                System.out.println("Syntax error. Try again");
            }
        }

        catch (SQLException e) {
            System.out.println(
                    "SQL exception found.\nLocation : Orders.java/search/try.\nDetails :-\n-------------------------------------------");
            e.printStackTrace();
        }

        finally {
            try {
                if (st != null)
                    st.close();
                if (con != null)
                    con.close();
                if (rs != null)
                    rs.close();
            }

            catch (SQLException e) {
                System.out.println(
                        "SQL exception found.\nLocation : Orders.java/search/finally.\nDetails :-\n-------------------------------------------");
                e.printStackTrace();
            }
        }
    }
}