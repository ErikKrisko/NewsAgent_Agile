import junit.framework.TestCase;

import java.util.Map;

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
            fail("Exception not expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
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
            assertEquals(Price = + Entry +);
        }
    }
    /** Test 009
     *  Test for publication empty frequency
     *  ==========
     *  Inputs: publication.validateFrequency("");
     *  ==========
     *  Expected Outputs:  "Frequency = "" cannot be empty"
     */
    public void testDB_Publication009()
    {
        String entry = "";
        try {
            publication.validateFrequency(entry);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency cannot be empty",e.getMessage());
        }
    }
    /** Test 010
     *  Test for publication daily frequency
     *  ==========
     *  Inputs: assertEquals("Daily", publication.validateFrequency("Daily"));
     *  ==========
     *  Expected Outputs:  "Daily"
     */
    public void testDB_Publication010()
    {
        String entry = "Daily";
        try {
            assertEquals(entry, publication.validateFrequency(entry));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 011
     *  Test for publication weekly day1 frequency
     *  ==========
     *  Inputs: assertEquals("Weekly, 1", publication.validateFrequency("Weekly, 1"));
     *  ==========
     *  Expected Outputs:  "Weekly, 1"
     */
    public void testDB_Publication011()
    {
        String entry = "Weekly, 1";
        try {
            assertEquals(entry, publication.validateFrequency(entry));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 012
     *  Test for publication weekly day7 frequency
     *  ==========
     *  Inputs: assertEquals("Weekly, 7", publication.validateFrequency("Weekly, 7"));
     *  ==========
     *  Expected Outputs:  "Weekly, 7"
     */
    public void testDB_Publication012()
    {
        String entry = "Weekly, 7";
        try {
            assertEquals(entry, publication.validateFrequency(entry));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 013
     *  Test for publication weekly frequency
     *  ==========
     *  Inputs: assertEquals("Weekly, 0", publication.validateFrequency("Weekly, 0"));
     *  ==========
     *  Expected Outputs:  "Frequency is wrong : Weekly, 0"
     */
    public void testDB_Publication013()
    {
        String entry = "Weekly, 0";
        try {
            publication.validateFrequency("Weekly, 0");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency is wrong : " + entry, e.getMessage());
        }
    }
    /** Test 014
     *  Test for publication weekly frequency
     *  ==========
     *  Inputs: assertEquals("Weekly, 8", publication.validateFrequency("Weekly, 8"));
     *  ==========
     *  Expected Outputs:  "Frequency is wrong : Weekly, 8"
     */
    public void testDB_Publication014()
    {
        String entry = "Weekly, 8";
        try {
            publication.validateFrequency("Weekly, 8");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency is wrong : " + entry, e.getMessage());
        }
    }


}