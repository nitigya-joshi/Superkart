package DbInfo;

/*
Change the username , password and url of database here
 */

public class info {
    private static String username = "root";
    private static String passwd = "root";
    private static String url = "jdbc:mysql://localhost:3306/oops_mini";

    public static String getPassword(){
        return passwd;
    }

    public static String getUsername(){
        return username;
    }

    public static String getUrl(){
        return url;
    }
}
