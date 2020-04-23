import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableMaker {

    public Table CreateTable(String tableString){

        return null;
    }

    public Map<String, String> GetPatterns(String tableString){

        return null;
    }
    public String IsolateColumns(String tableString){

        return null;
    }
    public boolean VerifyString(String tableString){
        Pattern p = Pattern.compile("(CREATE TABLE ')([A-Za-z][A-Za-z0-9]+)(' \\()(\\w+[,]?)+(\\): line format \\/)(\\([A-Za-z0-9\\*\\'\\{\\}\\[\\]\\(\\)\\?\\\\\\/ \\.\\^\\$]+\\)(;| ))+(\\/file '[\\w:\\/\\\\.]+';)");
        Matcher matcher = p.matcher(tableString);
        return matcher.matches();
    }





}
