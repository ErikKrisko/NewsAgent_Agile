import junit.framework.TestCase;


public class DB_PublicationTest extends TestCase {
    DB_Publication publication = new DB_Publication();

    /**
     * TEST 001
     * Test name for a string that is longer than the character limit
     * ============================
     * Inputs: String
     * TheSundayMailMirrorExpressTimes
     * ============================
     * Expected Outputs: Name = TheSundayMailMirrorExpressTimes is too long
     */
    public void testDB_Publication001()
    {
        String entry = "TheSundayMailMirrorExpressTimes";
        try {
            publication.validateName(entry);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Name = " + entry + ", is too long.", e.getMessage());

        }
    }

    /**
     * TEST 002
     * Test Name for a string that is empty
     * ============================
     * Inputs: String
     * " "
     * ============================
     * Expected Outputs: Name = cannot be Empty
     */
    public void testDB_publication002()
    {
        String entry = "";
        try {
            publication.validateName(entry);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Name = " + entry + ", cannot be empty.", e.getMessage());

        }
    }

    /**
     * TEST 003
     * Test Name for a string that meets parameters & passes
     * ============================
     * Inputs: String
     * "Westmeath Examiner"
     * ============================
     * Expected Outputs: Name = "Westmeath Examiner"
     */
    public void testDB_Publication003()
    {
        try {
            publication.validateName("Westmeath Examiner");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not expected.");
            assertEquals("Westmeath Examiner", e.getMessage());
        }
    }

    /**
     * TEST 004
     * Test type for an invalid type
     * ============================
     * Inputs: String
     * "Paper"
     * ============================
     * Expected Outputs: Name = "Type = Paper, must be either Tabloid, Broadsheet, or Magazine"
     */
    public void testDB_Publication004()
    {
        try {
            publication.validateType("Paper");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Type = Paper, must be either Tabloid, Broadsheet, or Magazine", e.getMessage());
        }
    }

    /**
     * TEST 005
     * Test type for an valid type
     * ============================
     * Inputs: String
     * "Tabloid"
     * ============================
     * Expected Outputs: Name = "Type = Tabloid"
     */
    public void testDB_Publication005()
    {
        try {
            publication.validateType("Tabloid");
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception not expected");
            assertEquals("Type = Tabloid", e.getMessage());
        }
    }
    /**
     * TEST 006
     * Test Price for an entry of zero
     * ============================
     * Inputs: double
     * 0.00
     * ============================
     * Expected Outputs: Name = "Price = " 0.00 ", must be greater than zero and less than ten."
     */
    public void testDB_Publication006()
    {
        try {
            publication.validatePrice(0.00);
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception Expected.");
            assertEquals("Price = must be greater than zero and less than ten.",e.getMessage());
        }
    }

    /**
     * TEST 007
     * Test Price for an entry greater than ten
     * ============================
     * Inputs: double
     * 12.00
     * ============================
     * Expected Outputs: Name = "Price = " 12.00 ", must be greater than zero and less than ten."
     */
    public void testDB_Publication007()
    {
        try {
            publication.validatePrice(12.00);

        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception Expected.");
            assertEquals("Price = must be greater than zero and less than ten.",e.getMessage());
        }
    }
    /**
     * TEST 008
     * Test Price for an entry of zero
     * ============================
     * Inputs: double
     * 5.00
     * ============================
     * Expected Outputs: Name = "Price = " 5.00 ",
     */
    public void testDB_Publication008()
    {
        Double Entry = 5.00;
        try {
            publication.validatePrice(Entry);
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception Not Expected.");
            //assertEquals("Price = " + Entry +" ");//
        }
    }
    /** Test 009
     *  Test for publication empty frequency
     *  ==========
     *  Inputs: publication.setFrequency("");
     *  ==========
     *  Expected Outputs:  "Frequency has to be 'DAILY/WEEKLY/MONTHLY DAY(if WEEKLY/MONTHLY)'"
     */
    public void testDB_Publication009()
    {
        try {
            publication.setFrequency("");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency has to be 'DAILY/WEEKLY/MONTHLY DAY(if WEEKLY/MONTHLY)'",e.getMessage());
        }
    }
    /** Test 010
     *  Test for publication daily day0 frequency success
     *  ==========
     *  Inputs: publication.setFrequency("DAILY");
     *  ==========
     *  Expected Outputs:  NO ERROR
     */
    public void testDB_Publication010()
    {
        try {
            publication.setFrequency("DAILY");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 011
     *  Test for publication weekly day1 frequency success
     *  ==========
     *  Inputs: publication.setFrequency("WEEKLY 1");
     *  ==========
     *  Expected Outputs:  NO ERROR
     */
    public void testDB_Publication011()
    {
        try {
            publication.setFrequency("WEEKLY 1");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 012
     *  Test for publication weekly day7 frequency success
     *  ==========
     *  Inputs: publication.setFrequency("WEEKLY 7");
     *  ==========
     *  Expected Outputs:  NO ERROR
     */
    public void testDB_Publication012()
    {
        try {
            publication.setFrequency("WEEKLY 7");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 013
     *  Test for publication weekly frequency day lesser value fail
     *  ==========
     *  Inputs: publication.setFrequency("WEEKLY 0");
     *  ==========
     *  Expected Outputs:  "Weekly frequency has to have a day of 1-7(MON-SUN)"
     */
    public void testDB_Publication013()
    {
        try {
            publication.setFrequency("WEEKLY 0");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Weekly frequency has to have a day of 1-7(MON-SUN)", e.getMessage());
        }
    }
    /** Test 014
     *  Test for publication weekly frequency
     *  ==========
     *  Inputs: publication.setFrequency("WEEKLY 8");
     *  ==========
     *  Expected Outputs:  "Weekly frequency has to have a day of 1-7(MON-SUN)"
     */
    public void testDB_Publication014()
    {
        try {
            publication.setFrequency("WEEKLY 8");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Weekly frequency has to have a day of 1-7(MON-SUN)", e.getMessage());
        }
    }

    /** Test 015
     *  Test for publication Monthly fail lesser value
     *  ==========
     *  Inputs: publication.setFrequency("MONTHLY 8");
     *  ==========
     *  Expected Outputs:  "Monthly frequency has to have a day of 1-28"
     */
    public void testDB_Publication015()
    {
        try {
            publication.setFrequency("MONTHLY 0");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Monthly frequency has to have a day of 1-28", e.getMessage());
        }
    }
    /** Test 016
     *  Test for publication Monthly fail upper value
     *  ==========
     *  Inputs: publication.setFrequency("MONTHLY 29");
     *  ==========
     *  Expected Outputs:  "Monthly frequency has to have a day of 1-28"
     */
    public void testDB_Publication016()
    {
        try {
            publication.setFrequency("MONTHLY 29");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Monthly frequency has to have a day of 1-28", e.getMessage());
        }
    }
    /** Test 017
     *  Test for publication Monthly success lesser value
     *  ==========
     *  Inputs: publication.setFrequency("MONTHLY 1");
     *  ==========
     *  Expected Outputs:  NO ERROR
     */
    public void testDB_Publication017()
    {
        try {
            publication.setFrequency("MONTHLY 1");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 018
     *  Test for publication Monthly success upper value
     *  ==========
     *  Inputs: publication.setFrequency("MONTHLY 28");
     *  ==========
     *  Expected Outputs:  NO ERROR
     */
    public void testDB_Publication018()
    {
        try {
            publication.setFrequency("MONTHLY 28");
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 019
     *  Test for publication wrong frequency
     *  ==========
     *  Inputs: publication.setFrequency("YEARLY");
     *  ==========
     *  Expected Outputs:  "Frequency has to be 'DAILY/WEEKLY/MONTHLY DAY(if WEEKLY/MONTHLY)'"
     */
    public void testDB_Publication019()
    {
        try {
            publication.setFrequency("YEARLY");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency has to be 'DAILY/WEEKLY/MONTHLY DAY(if WEEKLY/MONTHLY)'", e.getMessage());
        }
    }


}