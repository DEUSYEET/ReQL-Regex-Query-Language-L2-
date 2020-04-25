import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    public void Run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        TableMaker tableMaker = new TableMaker();
        TableReader tableReader = new TableReader();
        String tableString = "";
        String readString = "";
        Table t;
        boolean run = true;
        try {
            do {
                System.out.println("Create Table");
                System.out.println("Format:  CREATE TABLE '<table-name>' (<col-names-list>) : line format /<line-format-regex>; /file '<file-path>'");
                tableString = bufferedReader.readLine();
            } while (!tableMaker.VerifyString(tableString));
            t = tableMaker.CreateTable(tableString);
            do {
                do {
                    System.out.println("Query Table");
                    System.out.println("Format:  SELECT <col-names> FROM <table-name> WHERE <criteria>");
                    readString = bufferedReader.readLine();
                } while (!tableReader.VerifyString(readString));
                tableReader.ReadTable(readString, t);

                System.out.println("Continue? [y/n]");
                String response = bufferedReader.readLine().toLowerCase();
                run = (response.equals("y"));
            } while (run);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
