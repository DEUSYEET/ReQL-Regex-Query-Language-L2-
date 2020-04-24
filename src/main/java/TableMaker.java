import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableMaker {

    public Table CreateTable(String tableString) {

        if (VerifyString(tableString)) {
        String name = IsolateName(tableString);
        Map<String, String> patternMap = GetPatterns(tableString);
        String file = IsolateFilePath(tableString);
        return new Table(name,file,patternMap);
            }     else {
        System.out.println("String Invalid");
        return null;
        }
    }

    protected Map<String, String> GetPatterns(String tableString) {

        Map<String, String> patternMap = new LinkedHashMap<>();
            List<String> columns = IsolateColumns(tableString);
            List<String> patterns = IsolatePatterns(tableString);

            for (int i = 0; i < columns.size(); i++) {
                String pattern = patterns.get(i);
                String column = columns.get(i);
                patternMap.put(column, pattern );
//                System.out.println(patternMap);
            }
        return patternMap;
    }

    protected boolean VerifyString(String tableString) {
        Pattern p = Pattern.compile("(CREATE TABLE ')([A-Za-z][A-Za-z0-9]+)(' \\()(\\w+[,]?)+(\\): line format \\/)(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$\\+]+\\);?)+( ? \\/file '[\\w:\\/\\\\.\\-]+';)");
        Matcher matcher = p.matcher(tableString);
        return matcher.matches();
    }


    protected String IsolateName(String tableString) {
        Pattern p = Pattern.compile("('[A-Za-z][A-Za-z0-9]+')");
        Matcher matcher = p.matcher(tableString);
        String name = "";
        if (matcher.find()) {
            name = matcher.group().replace("'", "");
        }
        return name;
    }


    protected List<String> IsolateColumns(String tableString) {
        Pattern p = Pattern.compile("(' \\()(\\w+[,]?)+(\\))");
        Matcher matcher = p.matcher(tableString);
        List<String> columnsList = new ArrayList<String>();
        if (matcher.find()) {
            String columns = matcher.group();
            Pattern columnPattern = Pattern.compile("(\\w+)+");
            Matcher columnMatcher = columnPattern.matcher(columns);
            while (columnMatcher.find()) {
//                System.out.println(columnMatcher.group());
                columnsList.add(columnMatcher.group());
            }
        } else {
            System.out.println("No Match Found");
        }
        return columnsList;
    }

    protected List<String> IsolatePatterns(String tableString) {
        Pattern p = Pattern.compile("(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$\\+]+\\);?)+");
        Matcher matcher = p.matcher(tableString);
        List<String> patternsList = new ArrayList<String>();
        if (matcher.find()) {
            String patterns = matcher.group();
            Pattern patternPattern = Pattern.compile("(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$\\+]+\\))");
            Matcher patternMatcher = patternPattern.matcher(patterns);
            while (patternMatcher.find()) {
                patternsList.add(patternMatcher.group());
            }
        } else {
            System.out.println("No Match Found");
        }
        return patternsList;
    }

    protected String IsolateFilePath(String tableString) {
        Pattern p = Pattern.compile("(\\/file '[\\w:\\/\\\\.]+';)");
        Matcher matcher = p.matcher(tableString);
        String file = "";
        if (matcher.find()) {
            String fileString = matcher.group();
            Pattern filePattern = Pattern.compile("'[\\w:\\/\\\\.]+'");
            Matcher fileMatcher = filePattern.matcher(fileString);
            if (fileMatcher.find()) {
                file = fileMatcher.group().replace("'", "");
            }
        }
        return file;
    }

}
