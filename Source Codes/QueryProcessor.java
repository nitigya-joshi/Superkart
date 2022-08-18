import java.io.FileNotFoundException;
import java.sql.SQLException;

class QueryProcessor {

    static Entity e;

    static void process(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {

        switch (args[1]) {
            case "Customer":
                e = new Customer();
                break;
            case "Product":
                e = new Product();
                break;
            case "Supplier":
                e = new Supplier();
                break;
            case "Orders":
                e = new Orders();
                break;
            default:
                System.out.println("Invalid Expression !!");
                return;
        }

        switch (args[0]) {
            case "-add":
                e.add(args);
                break;
            case "-update":
                e.update(args);
                break;
            case "-search":
                e.search(args);
                break;
            case "-delete":
                e.delete(args);
                break;
            case "-importData" :
                e.importData();
                break;
            default:
                System.out.println("Invalid Expression !!");
                return;
        }

    }
}
