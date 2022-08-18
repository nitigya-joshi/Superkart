import java.sql.*;

public class ProjectMain {
        public static void main(String[] args) {

                try {
                        if (args.length == 0 || args[0].equals("-h")) {
                                help();
                                return;
                        }

                        else if (args[0].equals("-v")) {
                                System.out.println("E-Shoping system - Version 1.0");
                                return;
                        }

                        else if (args.length >= 2)
                                QueryProcessor.process(args);

                        else
                                System.out.println("\nInvalid Instruction !!\n");
                } catch (ClassNotFoundException ce) {
                        System.out.println(
                                        "Error : ClassNotFound Exception occured\nLocation : Main function\nDetails :-\n-----------------------");
                        ce.printStackTrace();
                } catch (SQLException se) {
                        System.out.println(
                                        "Error : SQL Exception occured\nLocation : Main function\nDetails :-\n-----------------------");
                        se.printStackTrace();
                } catch (Exception e) {
                        System.out.println(
                                        "Error : Exception occured\nLocation : Main function\nDetails :-\n-----------------------");
                        e.printStackTrace();
                }

                return;
        }

        private static void help() {

                System.out.println("//// Instruction Set :-");
                System.out.print(
                                "Comparison operators :- (use the following key words in place of mathematical operators)\n");
                System.out.print("> : -gt\n");
                System.out.print(">= : -gte\n");
                System.out.print("< : -ls\n");
                System.out.print("<= : -lse\n");
                System.out.println();
                System.out.println("To import the data in table use : -importData <TableName>");
                System.out.println();
                System.out.print("To apply operation on or specify every record :: -all (in <condition>)\n");
                System.out.print("Eg. -search Customer -on -all :: it will return all records in \"Customer\" Table\n");
                System.out.print(
                                "Eg. -update Order TotalAmount = TotalAmount + 5 -all :: it will increment TotalAmount of every order by 5\n");
                System.out.println();
                System.out.print("operators of searching partial strings :-\n");
                System.out.print("use , % -> any substring , _ -> any single character\n");
                System.out.print(
                                "Eg. <String Field> like \"%abc__\" , i.e the string could be \"abcde\" , \"gabcty\" ,\"popabcqw\" etc.\n");
                System.out.println();
                System.out.print("Instructions :-\n");
                System.out.println();
                System.out.print(
                                "1. -add <Table Name> <entry details> :: add records in table <Table Name> with details <entry details>\n");
                System.out.print("Eg. -add Customer 1 \"Mark\" \"Johnson\" \"las Vegas\" \"USA\" 123456\n");
                System.out.print("\n");
                System.out.print(
                                "2. -delete <Table Name> -on <condition> :: delete records in table <Table Name> over the condition <condition>\n");
                System.out.print("Eg. -delete Customer -on -all :: it will delete all records from Customer table\n");
                System.out.print(
                                "Eg. -delete Order -on TotalAmount -lse 5000 :: it will delete all orders with TotalAmount <= 5000\n");
                System.out.println();
                System.out.print(
                                "3. -update <Table Name> -set <field> = <update to> -on <condition> :: update records in table <Table Name>\n");
                System.out
                                .print("   apply updates on field <field> , update values to <update to> over condition <condition>\n");
                System.out.print(" Eg. -update Order -set TotalAmount = 1000 -on TotalAmount -gt 5000 ::\n");
                System.out.print("          it will set TotalAmount of every order with TotalAmount > 5000\n");
                System.out.print("         to 1000.\n");
                System.out.println();
                System.out.print("4. -search <Table Name> -get <field1> <field2>.. -on <condition> ::\n");
                System.out.print("search for records in table <Table Name> filter the fields after -get\n");
                System.out.print("over the condition <condition> , with <Limit_value> number of lines\n");
                System.out.print("Eg. -search Customer -on -all :: it will print all records from customer table\n");
                System.out.print("Eg. -search Customer -get cust_id FirstName City -on -all :: it will print\n");
                System.out.print("         (cust_id,FirstName,City) of all records in Customer\n");
                System.out.print(
                                "Eg. -search Customer -get cust_id FirstName -on country = \"India\" :: it will print\n");
                System.out.print("        (cust_id,FirstName) of all customers who are from India.\n");
                System.out.println();
                System.out.print("For aggregate functions use keyword \"-agg\" followed by aggregate type and field\n");
                System.out.print(
                                "Eg. -serach Order -get -agg sum TotalAmount :: it will print sum of TotalAmount of all orders\n");
                System.out.println();
                System.out.print(
                                "For sorting in a order : use keyword \"-sort\" followed by field name followed by \"-asc\" for\n");
                System.out.print(" ascending and \"-desc\" for descending order\n");
                System.out.print(
                                "Eg. -search Order -on -all -sort TotalAmount -asc :: it will print all orders in ascending order\n");
                System.out.print("of TotalAmount\n");
                System.out.print(
                                "Eg. -search Cusomter -on -all -sort FirstName -asc LastName -desc :: it will print all customers\n");
                System.out.print("in ascending order of FirstName and descending order of LastName\n");
                System.out.println("//// End ...");

        }
}
