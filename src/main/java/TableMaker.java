import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableMaker {

    public Table CreateTable(String tableString) {

        return null;
    }

    public Map<String, String> GetPatterns(String tableString) {

        Map<String, String> patternMap = new HashMap<>();
        if (VerifyString(tableString)) {
            String name = IsolateName(tableString);
            List<String> columns = IsolateColumns(tableString);
            List<String> patterns = IsolatePatterns(tableString);

            for (int i = 0; i < columns.size(); i++) {
                patternMap.put(columns.get(i), patterns.get(i));
            }

        } else{
            System.out.println("String Invalid");
        }

        return patternMap;
    }

    public boolean VerifyString(String tableString) {
        Pattern p = Pattern.compile("(CREATE TABLE ')([A-Za-z][A-Za-z0-9]+)(' \\()(\\w+[,]?)+(\\): line format \\/)(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$]+\\)(;| ))+(\\/file '[\\w:\\/\\\\.]+';)");
        Matcher matcher = p.matcher(tableString);
        return matcher.matches();
    }


    public String IsolateName(String tableString) {
        Pattern p = Pattern.compile("('[A-Za-z][A-Za-z0-9]+')");
        Matcher matcher = p.matcher(tableString);
        String name = "";
        if (matcher.find()) {
            name = matcher.group().replace("'", "");
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
            while (columnMatcher.find()) {
                columnsList.add(columnMatcher.group());
            }
        } else {
            System.out.println("No Match Found");
        }
        return columnsList;
    }

    public List<String> IsolatePatterns(String tableString) {
        Pattern p = Pattern.compile("(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$]+\\)(;| ))+");
        Matcher matcher = p.matcher(tableString);
        List<String> patternsList = new ArrayList<String>();
        if (matcher.find()) {
            String patterns = matcher.group();
            Pattern patternPattern = Pattern.compile("\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$]+\\)+");
            Matcher patternMatcher = patternPattern.matcher(patterns);
            while (patternMatcher.find()) {
                patternsList.add(patternMatcher.group());
            }
        } else {
            System.out.println("No Match Found");
        }
        return patternsList;
    }


}
