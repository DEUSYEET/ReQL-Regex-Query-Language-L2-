import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableReader {
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


}
