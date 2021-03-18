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
     *  Inputs: publication.validateFrequency("");
     *  ==========
     *  Expected Outputs:  "Frequency = "" cannot be empty"
     */
    public void testDB_Publication009()
    {
        String entry = "";
        try {
            publication.validateFrequency(entry, 0);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency cannot be empty",e.getMessage());
        }
    }
    /** Test 010
     *  Test for publication daily day0 frequency success
     *  ==========
     *  Inputs: assertEquals("Daily", publication.validateFrequency("Daily", 0));
     *  ==========
     *  Expected Outputs:  "Daily"
     */
    public void testDB_Publication010()
    {
        String entry = "Daily";
        try {
            assertEquals(entry, publication.validateFrequency(entry, 0));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 011
     *  Test for publication weekly day1 frequency success
     *  ==========
     *  Inputs: assertEquals("Weekly, 1", publication.validateFrequency("Weekly, 1"));
     *  ==========
     *  Expected Outputs:  "Weekly, 1"
     */
    public void testDB_Publication011()
    {
        String entry = "Weekly";
        try {
            assertEquals("Weekly, 1", publication.validateFrequency(entry, 1));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 012
     *  Test for publication weekly day7 frequency success
     *  ==========
     *  Inputs: assertEquals("Weekly, 7", publication.validateFrequency("Weekly, 7"));
     *  ==========
     *  Expected Outputs:  "Weekly, 7"
     */
    public void testDB_Publication012()
    {
        String entry = "Weekly";
        try {
            assertEquals("Weekly, 7", publication.validateFrequency(entry, 7));
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 013
     *  Test for publication weekly frequency day lesser value fail
     *  ==========
     *  Inputs: assertEquals("Weekly, 0", publication.validateFrequency("Weekly, 0"));
     *  ==========
     *  Expected Outputs:  "Frequency is wrong : Weekly, 0"
     */
    public void testDB_Publication013()
    {
        String entry = "Weekly";
        try {
            publication.validateFrequency(entry, 0);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day between 1-7(Monday - Sunday)!", e.getMessage());
        }
    }
    /** Test 014
     *  Test for publication weekly frequency
     *  ==========
     *  Inputs: assertEquals("Weekly, 8", publication.validateFrequency("Weekly, 8"));
     *  ==========
     *  Expected Outputs:  "Frequency is invalid : Weekly"
     */
    public void testDB_Publication014()
    {
        String entry = "Weekly";
        try {
            publication.validateFrequency(entry, 8);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day between 1-7(Monday - Sunday)!", e.getMessage());
        }
    }
    /** Test 015
     *  Test for publication daily fail lesser day value
     *  ==========
     *  Inputs: assertEquals("Daily, -1", publication.validateFrequency("Daily, -1"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication015()
    {
        String entry = "Daily";
        try {
            publication.validateFrequency(entry, -1);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day as 0 when using Daily!", e.getMessage());
        }
    }
    /** Test 016
     *  Test for publication daily fail upper day value
     *  ==========
     *  Inputs: assertEquals("Daily, 1", publication.validateFrequency("Daily, 1"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication016()
    {
        String entry = "Daily";
        try {
            publication.validateFrequency(entry, 1);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day as 0 when using Daily!", e.getMessage());
        }
    }
    /** Test 017
     *  Test for publication Monthly fail lesser value
     *  ==========
     *  Inputs: assertEquals("Monthly, 0", publication.validateFrequency("Monthly, 0"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication017()
    {
        String entry = "Monthly";
        try {
            publication.validateFrequency(entry, 0);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day between 1-28!", e.getMessage());
        }
    }
    /** Test 018
     *  Test for publication Monthly fail upper value
     *  ==========
     *  Inputs: assertEquals("Monthly, 29", publication.validateFrequency("Monthly, 29"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication018()
    {
        String entry = "Monthly";
        try {
            publication.validateFrequency(entry, 29);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Set day between 1-28!", e.getMessage());
        }
    }
    /** Test 019
     *  Test for publication Monthly success lesser value
     *  ==========
     *  Inputs: assertEquals("Monthly, 29", publication.validateFrequency("Monthly, 29"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication019()
    {
        String entry = "Monthly";
        try {
            publication.validateFrequency(entry, 1);
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 020
     *  Test for publication Monthly success upper value
     *  ==========
     *  Inputs: assertEquals("Monthly, 29", publication.validateFrequency("Monthly, 29"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication020()
    {
        String entry = "Monthly";
        try {
            publication.validateFrequency(entry, 28);
        } catch (DB_PublicationExceptionHandler e)
        {
            e.printStackTrace();
            fail("Exception not Expected.");
        }
    }
    /** Test 021
     *  Test for publication Monthly fail upper value
     *  ==========
     *  Inputs: assertEquals("Monthly, 29", publication.validateFrequency("Monthly, 29"));
     *  ==========
     *  Expected Outputs:  "Set day as 0 when using Daily!"
     */
    public void testDB_Publication021()
    {
        String entry = "Yearly";
        try {
            publication.validateFrequency(entry, 1);
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency is invalid : " + entry, e.getMessage());
        }
    }


}