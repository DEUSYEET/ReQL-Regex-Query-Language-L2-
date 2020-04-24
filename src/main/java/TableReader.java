import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableReader {
    private List<Map<String,String>> rows = new ArrayList<>();


    public void ReadTable(String readString){

    }
    private String FilterData(String operator, String Condition){

        return null;
    }

    private void DisplayData(String data){

    }

    protected boolean VerifyString(String readString){
        Pattern p = Pattern.compile("(SELECT )(\\w+,? )+(FROM [A-Za-z][A-Za-z0-9]+ )(WHERE )(\\w+,?( => | = | < | <= | > | >= ))(.+)");
        Matcher matcher = p.matcher(readString);
        return matcher.matches();
    }

    protected String IsolateName(String read){
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
                columnsList.add(columnMatcher.group().replace(" ","").replace(",",""));
            }
        } else {
            System.out.println("No Match Found");
        }
        return columnsList;
    }

    protected Map<String, String> IsolateStatement(String read){
        Pattern p = Pattern.compile("(WHERE )(\\w+,?)( => | = | < | <= | > | >= )('.+)");
        Matcher matcher = p.matcher(read);
        Map<String,String> statement = new HashMap<>();

        if(matcher.find()){
            statement.put("column", matcher.group(2));
            statement.put("operator", matcher.group(3).replace(" ",""));
            statement.put("condition", matcher.group(4).replace("'",""));
        }

        return statement;
    }

    protected List<Map<String,String>> ReadIn(String filePath, Map<String,String> patterns){
        List<Map<String,String>> rows = new ArrayList<>();
        File file = new File(filePath);
        Set<String> keys = patterns.keySet();
        int index = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                //Separate line by the semicolon ;
                String[] lineSections = line.split(";");
                //Check each section against regex
                Map<String,String> row = new LinkedHashMap<>();
                for (String key : keys) {
                    Pattern p = Pattern.compile(patterns.get(key));
                    Matcher m = p.matcher(lineSections[index]);
                //Add to data row
                    if (m.matches()){
                        row.put(key,lineSections[index]);
                    } else {
                        row.put(key,"Invalid Data");
                    }
                    index++;
                }
                rows.add(row);
                index=0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rows;
    }


}
