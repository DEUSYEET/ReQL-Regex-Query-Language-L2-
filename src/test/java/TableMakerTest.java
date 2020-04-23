import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableMakerTest {
    TableMaker tableMaker;

    @BeforeEach
    void setUp() {
        tableMaker = new TableMaker();
    }


    @Test
    void VerifyInputPattern(){
        String validString = "CREATE TABLE 'appointments' (patient_name,doctor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';";
        String invalidString = "CREATE TABLE 'appointments' (patient_name,d octor_name,apt_date,apt_time,topic): line format /(\\w*);(\\w*);([^ ]*);([^ ]*);(.*$) /file 'C:/appts.txt';";

        boolean isValid = tableMaker.VerifyString(validString);
        boolean isInvalid = tableMaker.VerifyString(invalidString);

        assertTrue(isValid);
        assertFalse(isInvalid);
    }


}