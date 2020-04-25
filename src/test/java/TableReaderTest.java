import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TableReaderTest {
    TableReader tableReader;
    Table t;
    String readString = "SELECT patient_name, topic, doctor_name FROM appointments WHERE apt_date = '3/1/2020'";
    @BeforeEach
    void setUp() {
        TableMaker tableMaker = new TableMaker();
        t= tableMaker.CreateTable("CREATE TABLE 'appointments' (patient_name,doctor_name,apt_date,apt_time,topic): line format /(.+);(.+);(.+);(.+);(.+); /file 'test.txt';");
        tableReader = new TableReader();
    }

    @Test
    void VerifyString(){
      boolean valid =  tableReader.VerifyString(readString,t);

      assertTrue(valid);
    }

    @Test
    void IsolateTableName() {
        String expected = "appointments";
        String result = tableReader.IsolateName(readString);
        assertEquals(expected, result);
    }
    @Test
    void IsolateColumnNamesFromString() {
        List<String> columns = new ArrayList<>();
        columns.add("patient_name");
        columns.add("doctor_name");
        columns.add("topic");

        List<String> result = tableReader.IsolateColumns(readString);
        System.out.println(result);

        assertTrue(result.containsAll(columns));
    }

    @Test
    void IsolateStatement(){
        String column = "apt_date";
        String operator = "=";
        String condition = "3/1/2020";

        Map<String,String> statement = tableReader.IsolateStatement(readString);

        assertEquals(column, statement.get("column"));
        assertEquals(operator, statement.get("operator"));
        assertEquals(condition, statement.get("condition"));
    }

    @Test
    void ReadInDataFromFile(){
        List<Map<String,String>> data = tableReader.ReadIn(t.getFilePath(), t.getPatterns());
        System.out.println(data);
    }

    @Test
    void FilterDataBasedOffOfEquals(){
        List<Map<String,String>> data = tableReader.ReadIn(t.getFilePath(), t.getPatterns());
        Map<String,String> statement = tableReader.IsolateStatement(readString);
        List<String> columns = tableReader.IsolateColumns(readString);


        List<Map<String,String>> results = tableReader.FilterData(data, columns, statement);

        System.out.println(results);

        assertEquals(results.size(),1);
        assertEquals(results.get(0).get("patient_name"),"Selena Gomez");
    }
    @Test
    void FilterData() {
        String readString = "SELECT patient_name, topic, doctor_name FROM appointments WHERE apt_date >= '3/1/2020'";
        List<Map<String, String>> data = tableReader.ReadIn(t.getFilePath(), t.getPatterns());
        Map<String, String> statement = tableReader.IsolateStatement(readString);
        List<String> columns = tableReader.IsolateColumns(readString);


        List<Map<String, String>> results = tableReader.FilterData(data, columns, statement);

        System.out.println(results);
    }

    @Test
    void DisplayData(){
        String readString = "SELECT patient_name, topic, doctor_name FROM appointments WHERE apt_date > '1'";

        List<Map<String, String>> data = tableReader.ReadIn(t.getFilePath(), t.getPatterns());
        Map<String, String> statement = tableReader.IsolateStatement(readString);
        List<String> columns = tableReader.IsolateColumns(readString);
        List<Map<String, String>> results = tableReader.FilterData(data, columns, statement);

        tableReader.DisplayData(results);
    }





}