import java.sql.*;
import DbInfo.*;
import java.util.Scanner;
import java.io.*;

class Customer extends Entity {

    private String url = info.getUrl();
    private String password = info.getPassword();
    private String username = info.getUsername();

    @Override
    public void importData() throws FileNotFoundException, SQLException {

        Connection con = DriverManager.getConnection(url,username,password);
        Statement st = con.createStatement();

        Scanner s = new Scanner(new BufferedReader(new FileReader("Tables/Customer.csv")));
        String query ;
        String temp;

        while(s.hasNext()){
          temp = s.nextLine();
          query = "insert into Customer values (" + temp + ")";
          st.executeUpdate(query);
        }

        System.out.println("Data imported in table.");

        s.close();
        st.close();
        con.close();
    }

    @Override
    public void add(String[] args) throws ClassNotFoundException, SQLException {

        if (args[0].equals("-add") && args[1].equals("Customer")) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into CUSTOMER values('" + args[2] + "','" + args[3] + "','" + args[4] + "','"
                    + args[5] + "','" + args[6] + "'," + args[7] + ")";
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            int count = st.executeUpdate(query);
            System.out.println(count + " row/s added");
            st.close();
            con.close();

        } else {
            System.out.println("\nSomething is wrong with the syntax\n");
        }

    }

    @Override
    public void search(String args[]) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String query;
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        if (args[0].equals("-search") && args[1].equals("Customer")) {
            if (args[2].equals("-on") && args[3].equals("-all")) {

                query = "select * from CUSTOMER";
                ResultSet rs = st.executeQuery(query);

                System.out.println("\nCustomer Id\t    FirstName\t\t LastName\t\t City\t\t   Country\t\t  Phone no.\n" +
                                    "-----------------------------------------------------------------------------------------------------------------------\n");

                while (rs.next()) {
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n\n", rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
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
                query = "select * from Customer where " + att;

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
                    System.out.printf("%-13s %-13s %-13s %-13s %-13s %-13s\n", rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                }
            } else if (args[2].equals("-get")) {
                int i = 3, dump = 3;
                String[] temp = new String[6];

                while (!(args[i].equals("-on"))) {
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
                    query = query + " " + "from CUSTOMER";

                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        for (i = 0; i < j - 4; i++) {
                            System.out.printf("%-13s ", rs.getString(temp[i]));
                        }
                        System.out.println();
                    }
                } else {
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
                    query = query + " " + "from CUSTOMER where " + att;

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
                        for (i = 0; i < j - 4; i++) {
                            System.out.printf("%-13s ", rs.getString(temp[i]));
                        }
                        System.out.println();
                    }

                }

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

        if (args[0].equals("-delete") && args[1].equals("Customer") && args[2].equals("-on")) {
            if (args[3].equals("-all")) {
                query = "delete from CUSTOMER";
            } else {
                String att = args[3];
                String op = args[4];
                String val = args[5];
                query = "delete from CUSTOMER where " + att;

                if (op.equals("-gt")) {
                    query = query + " > " + val;
                }
                if (op.equals("-gte")) {
                    query = query + " >= " + val;
                }
                if (op.equals("-ls")) {
                    query = query + " < " + val;
                }
                if (op.equals("-lse")) {
                    query = query + " <= " + val;
                }
                if (op.equals("=")) {
                    query = query + " = " + val;
                }
                if (op.equals("like")) {
                    query = query + " like '" + val + "'";
                }
            }
            int count = st.executeUpdate(query);
            System.out.println(count + " row/s deleted");
            st.close();
            con.close();
        }

    }

    @Override
    public void update(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "update CUSTOMER set ";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement st = con.createStatement();

        if (args[0].equals("-update") && args[1].equals("Customer") && args[2].equals("-set")) {
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
                        query = query + att1 + " = " + update_to + " where " + att2;
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
        System.out.println(count + " row/s updated");
        st.close();
        con.close();
    }
}
