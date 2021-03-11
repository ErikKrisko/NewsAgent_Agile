import junit.framework.TestCase;

public class DB_PublicationTest extends TestCase {
    DB_Publication publication = new DB_Publication();

    //Test 1 - test for too long of an input//
    public void testDB_Publication001()
    {
        try {
            publication.validateName(DB_Publication.prod_name, "TheSundayMailMirrorExpressTimes");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Entry = \"TheSundayMailMirrorExpressTimes\", is too long,", e.getMessage());

        }
    }

    //Test 2 - input of an empty string//
    public void testDB_publication002()
    {
        try {
            publication.validateName(DB_Publication.prod_name, "");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Entry = \"\", is too short,", e.getMessage());

        }
    }
    //test 3 - a correct input within the parameters for product name//
    public void testDB_Publication003()
    {
        try {
            publication.validateName(DB_Publication.prod_name, "Westmeath Examiner");
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception not expected.");
        }
    }
    //Test 4 - Fail to enter a correct parameter for product type//
    public void testDB_Publication004()
    {
        try {
            publication.validateType(DB_Publication.prod_type, "Paper");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("must be either Tabloid, Broadsheet, or Magazine", e.getMessage());
        }
    }
    //Test 5 - enter correct parameter for Type//
    public void testDB_Publication005()
    {
        try {
            publication.validateType(DB_Publication.prod_type, "Tabloid");
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception not expected");
        }
    }
    //Test 6 - entering 0 as a value for price//
    public void testDB_Publication006()
    {
        try {
            publication.validatePrice(DB_Publication.prod_price, "0");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Price = must be greater than zero and less than ten.",e.getMessage());
        }
    }
    //test 7 - entering a value greater than 10//
    public void testDB_Publication007()
    {
        try {
            publication.validatePrice(DB_Publication.prod_price, "12");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Price = must be greater than zero and less than ten.",e.getMessage());
        }
    }
    //test 8 - Entering a value within the parameters //
    public void testDB_Publication008()
    {
        try {
            publication.validatePrice(DB_Publication.prod_price, "5");
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception Not Expected.");
        }
    }
    //Test 9 - entering a Frequency Value greater 25 //
    public void testDB_Publication009()
    {
        try {
            publication.validateFrequency(DB_Publication.frequency, "29");
            fail("Exception Expected.");
        } catch (DB_PublicationExceptionHandler e)
        {
            assertEquals("Frequency = is too short",e.getMessage());
        }
    }
    //test 10 - entering a value within the frequency parameters
    public void testDB_Publication010()
    {
        try {
            publication.validateFrequency(DB_Publication.frequency, "29");
        } catch (DB_PublicationExceptionHandler e)
        {
            fail("Exception not Expected.");
        }
    }
}