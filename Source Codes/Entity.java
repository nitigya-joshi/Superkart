import java.io.FileNotFoundException;
import java.sql.SQLException;

public abstract class Entity {
    public abstract void search(String[] ary) throws ClassNotFoundException, SQLException;

    public abstract void update(String[] ary) throws ClassNotFoundException, SQLException;

    public abstract void add(String[] ary) throws ClassNotFoundException, SQLException;

    public abstract void delete(String[] ary) throws ClassNotFoundException, SQLException;

    public abstract void importData() throws FileNotFoundException, SQLException;
}
