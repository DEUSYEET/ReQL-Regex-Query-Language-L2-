import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableMaker {

    public Table CreateTable(String tableString) {

        return null;
    }

    public Map<String, String> GetPatterns(String tableString) {

        return null;
    }
    public boolean VerifyString(String tableString) {
        Pattern p = Pattern.compile("(CREATE TABLE ')([A-Za-z][A-Za-z0-9]+)(' \\()(\\w+[,]?)+(\\): line format \\/)(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$]+\\)(;| ))+(\\/file '[\\w:\\/\\\\.]+';)");
        Matcher matcher = p.matcher(tableString);
        return matcher.matches();
    }


    public  String IsolateName(String tableString){
        Pattern p = Pattern.compile("('[A-Za-z][A-Za-z0-9]+')");
        Matcher matcher = p.matcher(tableString);
        String name = "";
        if (matcher.find()){
            name=matcher.group().replace("'","");
        }
        return name;
    }


    public List<String> IsolateColumns(String tableString) {
        Pattern p = Pattern.compile("(' \\()(\\w+[,]?)+(\\))");
        Matcher matcher = p.matcher(tableString);
        List<String> columnsList = new ArrayList<String>();
        if (matcher.find()) {
            String columns = matcher.group();
            Pattern columnPattern = Pattern.compile("(\\w+)+");
            Matcher columnMatcher = columnPattern.matcher(columns);

            while (columnMatcher.find()){
                columnsList.add(columnMatcher.group());
            }


        } else {
            System.out.println("No Match Found");
        }


        return columnsList;
    }



}
