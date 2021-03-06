import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableMakerTest {
    TableMaker tableMaker;
    String tableString = "CREATE TABLE 'appointments' (patient_name,doctor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';";

    @BeforeEach
    void setUp() {
        tableMaker = new TableMaker();
    }


    @Test
    void VerifyInputPattern() {
        String validString = "CREATE TABLE 'appointments' (patient_name,doctor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';";
        String invalidString = "CREATE TABLE 'appointments' (patient_name,d octor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';";

        boolean isValid = tableMaker.VerifyString(validString);
        boolean isInvalid = tableMaker.VerifyString(invalidString);

        assertTrue(isValid);
        assertFalse(isInvalid);
    }

    @Test
    void IsolateTableName() {

        String expected = "appointments";

        String result = tableMaker.IsolateName(tableString);

        assertEquals(expected, result);

    }

    @Test
    void IsolateColumnNamesFromString() {
        List<String> columns = new ArrayList<>();
        columns.add("patient_name");
        columns.add("doctor_name");
        columns.add("apt_date");
        columns.add("apt_time");
        columns.add("topic");

        List<String> result = tableMaker.IsolateColumns(tableString);


        assertTrue(result.containsAll(columns));
    }

    @Test
    void IsolatePatterns() {
        List<String> patterns = new ArrayList<>();
        patterns.add("(\\w*)");
        patterns.add("(\\w*)");
        patterns.add("([^ ]*)");
        patterns.add("([^ ]*)");
        patterns.add("(.*$)");

        List<String> result = tableMaker.IsolatePatterns(tableString);

        assertTrue(result.containsAll(patterns));
    }

    @Test
    void GetPatternsMap() {
        Map<String, String> patterns = tableMaker.GetPatterns(tableString);

        System.out.println(patterns);
    }

    @Test
    void GetFilePath() {
        String expected = "C:/appts.txt";

        String result = tableMaker.IsolateFilePath(tableString);

        assertEquals(expected, result);
    }


    @Test
    void GetTable(){
        Table t = tableMaker.CreateTable(tableString);
        String nameExpected = "appointments";
        String fileExpected = "C:/appts.txt";

        assertEquals(nameExpected, t.getName());
        assertEquals(fileExpected, t.getFilePath());
        System.out.println(t.getPatterns());
    }

}