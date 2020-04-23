import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableReaderTest {
    TableReader tableReader;
    Table t;
    String readString = "SELECT patient_name, topic, doctor_name FROM appointments WHERE apt_date = '3/1/2020'";
    @BeforeEach
    void setUp() {
        TableMaker tableMaker = new TableMaker();
        t= tableMaker.CreateTable("CREATE TABLE 'appointments' (patient_name,doctor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';");
        tableReader = new TableReader();
    }

    @Test
    void VerifyString(){
      boolean valid =  tableReader.VerifyString(readString);

      assertTrue(valid);
    }

}