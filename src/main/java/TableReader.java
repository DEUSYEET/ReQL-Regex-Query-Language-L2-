import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableReader {
    private List<Map<String, String>> rows = new ArrayList<>();


    public void ReadTable(String readString ,Table t) {
        if(VerifyString(readString, t)){
            try{
        List<Map<String, String>> data = ReadIn(t.getFilePath(), t.getPatterns());
        Map<String, String> statement = IsolateStatement(readString);
        List<String> columns = IsolateColumns(readString);
        List<Map<String, String>> results = FilterData(data, columns, statement);
        DisplayData(results);

            } catch (IndexOutOfBoundsException e){
                System.out.println("File Invalid");
            }
            catch (NullPointerException e){
                System.out.println("Query Failed");
            }
        }
    }


    protected boolean VerifyString(String readString, Table t) {
        Pattern p = Pattern.compile("(SELECT )(\\w+,? )+(FROM [A-Za-z][A-Za-z0-9]+ )(WHERE )(\\w+,?( => | = | < | <= | > | >= ))(.+)");
        Matcher matcher = p.matcher(readString);
        boolean matches = matcher.matches();
        if (!IsolateName(readString).equals(t.getName())){
            System.out.println("Table not found");
            matches = false;
        }
        return matches;
    }

    protected String IsolateName(String read) {
        Pattern p = Pattern.compile("(FROM [A-Za-z][A-Za-z0-9]+)");
        Matcher matcher = p.matcher(read);
        String name = "";
        if (matcher.find()) {
            name = matcher.group().replace("FROM ", "");
        }
        return name;
    }

    protected List<String> IsolateColumns(String tableString) {
        Pattern p = Pattern.compile("(SELECT )(\\w+,? )+(FROM)");
        Matcher matcher = p.matcher(tableString);
        List<String> columnsList = new ArrayList<String>();
        if (matcher.find()) {
            String columns = matcher.group();
            Pattern columnPattern = Pattern.compile("[^SELECT](\\w+,?)+[^FROM]");
            Matcher columnMatcher = columnPattern.matcher(columns);
            while (columnMatcher.find()) {
                columnsList.add(columnMatcher.group().replace(" ", "").replace(",", ""));
            }
        } else {
            System.out.println("No Match Found");
        }
        return columnsList;
    }

    protected Map<String, String> IsolateStatement(String read) {
        Pattern p = Pattern.compile("(WHERE )(\\w+,?)( => | = | < | <= | > | >= )('.+)");
        Matcher matcher = p.matcher(read);
        Map<String, String> statement = new HashMap<>();

        if (matcher.find()) {
            statement.put("column", matcher.group(2));
            statement.put("operator", matcher.group(3).replace(" ", ""));
            statement.put("condition", matcher.group(4).replace("'", ""));
        }

        return statement;
    }

    protected List<Map<String, String>> ReadIn(String filePath, Map<String, String> patterns) {
        List<Map<String, String>> rows = new ArrayList<>();
        File file = new File(filePath);
        Set<String> keys = patterns.keySet();
        int index = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //Separate line by the semicolon ;
                String[] lineSections = line.split(";");
                //Check each section against regex
                Map<String, String> row = new LinkedHashMap<>();
                for (String key : keys) {
                    Pattern p = Pattern.compile(patterns.get(key));
                    Matcher m = p.matcher(lineSections[index]);
                    //Add to data row
                    if (m.matches()) {
                        row.put(key, lineSections[index]);
                    } else {
                        row.put(key, "Invalid Data");
                    }
                    index++;
                }
                rows.add(row);
                index = 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<Map<String, String>> FilterData(List<Map<String, String>> data, List<String> columns, Map<String, String> statement) {
        List<Map<String, String>> results = new ArrayList<>();
        String operator = statement.get("operator");
        String column = statement.get("column");
        String condition = statement.get("condition");

        for (Map<String, String> row : data) {
            switch (operator) {
                case "=":
                    if (row.get(column).equals(condition)) {
                        Map<String, String> resultRow = new LinkedHashMap<>();
                        for (String selectedColumn : columns) {
                            resultRow.put(selectedColumn, row.get(selectedColumn));
                        }
                        results.add(resultRow);
                    }
                    break;
                case ">=":
                    if (row.get(column).compareTo(condition)>=0) {
                        Map<String, String> resultRow = new LinkedHashMap<>();
                        for (String selectedColumn : columns) {
                            resultRow.put(selectedColumn, row.get(selectedColumn));
                        }
                        results.add(resultRow);
                    }
                    break;
                case "<=":
                    if (row.get(column).compareTo(condition)<=0) {
                        Map<String, String> resultRow = new LinkedHashMap<>();
                        for (String selectedColumn : columns) {
                            resultRow.put(selectedColumn, row.get(selectedColumn));
                        }
                        results.add(resultRow);
                    }
                    break;
                case ">":
                    if (row.get(column).compareTo(condition)>0) {
                        Map<String, String> resultRow = new LinkedHashMap<>();
                        for (String selectedColumn : columns) {
                            resultRow.put(selectedColumn, row.get(selectedColumn));
                        }
                        results.add(resultRow);
                    }
                    break;
                case "<":
                    if (row.get(column).compareTo(condition)<0) {
                        Map<String, String> resultRow = new LinkedHashMap<>();
                        for (String selectedColumn : columns) {
                            resultRow.put(selectedColumn, row.get(selectedColumn));
                        }
                        results.add(resultRow);
                    }
                    break;
            }


        }
        return results;
    }

    public void DisplayData(List<Map<String, String>> results) {
        if(results.size()>0){
        Set<String> columns = results.get(0).keySet();
        int width = (20);
        int totalWidth = (20*columns.size());
        for (String s : columns) {
            System.out.print(s);
            for (int i = 0; i < (width-s.length()); i++) {
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
        for (Map<String, String> m: results) {
            for (String s : columns) {
                System.out.print(m.get(s));

                for (int i = 0; i < ((width-m.get(s).length()-1)/2); i++) {
                    System.out.print(" ");
                }
                    System.out.print(":");
                for (int i = 0; i < ((width-m.get(s).length())/2); i++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            for (int i = 0; i < totalWidth; i++) {
                System.out.print(".");
            }
            System.out.println();
        }
        } else {
            System.out.println("No Results");
        }
    }
}
