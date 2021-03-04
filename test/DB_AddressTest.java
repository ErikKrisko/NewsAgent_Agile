import junit.framework.TestCase;

public class DB_AddressTest extends TestCase {
    DB_Address address = new DB_Address();

    /** TEST 001
     *  Test full_address, area_code, eir_code for an empty string / too short entry
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.full_address, "");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "Entry = "", cannot be an empty String."
     */
    public void testDB_Address001() {
        try {
            address.validateEntry( Att_Address.full_address, "");
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("Entry = \"\", cannot be an empty String.", e.getMessage());
        }
    }

    /** TEST 002
     *  Test full_address for too long entry.
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.full_address, "Place that takes 51 or more characters to identify.");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "full_address too long."
     */
    public void testDB_Address002() {
        try {
            address.validateEntry( Att_Address.full_address, "Place that takes 51 or more characters to identify.");
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("full_address too long.", e.getMessage());
        }
    }

    /** TEST 003
     *  Test full_address for valid entry.
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.full_address, "Athlone, Maple way 5");
     *  ==========
     *  Expected Outputs:   "Athlone, Maple way 5"
     */
    public void testDB_Address003() {
        try {
            assertEquals("Athlone, Maple way 5", address.validateEntry( Att_Address.full_address, "Athlone, Maple way 5"));
        } catch (DB_AddressExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 004
     *  Test for too long area_code.
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.area_code, "AreaCode 05");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "area_code too long."
     */
    public void testDB_Address004() {
        try {
            address.validateEntry( Att_Address.area_code, "AreaCode 05");
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("area_code too long.", e.getMessage());
        }
    }

    /** TEST 005
     *  Test for valid area_code
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.area_code, "B1");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "area_code too long."
     */
    public void testDB_Address005() {
        try {
            assertEquals("B1", address.validateEntry( Att_Address.area_code, "B1"));
        } catch (DB_AddressExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 006
     *  Test for invalid eir_code
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.eir_code, "123456");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address006() {
        try {
            address.validateEntry( Att_Address.eir_code, "123456");
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("eir_code invalid length.", e.getMessage());
        }
    }

    /** TEST 007
     *  Test for valid eir_code
     *  ==========
     *  Inputs: address.validateEntry( Att_Address.eir_code, "1234567");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address007() {
        try {
            assertEquals("1234567", address.validateEntry( Att_Address.eir_code, "1234567"));
        } catch (DB_AddressExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 008
     *  Test for invalid id and failed constructor
     *  ==========
     *  Inputs: new DB_Address( -1, "", "", "");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address008() {
        try {
            new DB_Address( -1, "", "", "");
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("ID must be 0 or greater.", e.getMessage());
        }
    }

    /** TEST 009
     *  Test for valid ID and successful constructor and getters
     *  ==========
     *  Inputs: new DB_Address( 0, "Athlone, Maple way 5", "B1", "1234567");
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address009() {
        try {
            DB_Address test = new DB_Address( 0, "Athlone, Maple way 5", "B1", "1234567");
            assertEquals( 0, test.getAddress_id());
            assertEquals( "Athlone, Maple way 5", test.getFull_address());
            assertEquals( "B1", test.getArea_code());
            assertEquals( "1234567", test.getEir_code());
        } catch (DB_AddressExceptionHandler e) {
            fail("Exception not expected.");
        }
    }

    /** TEST 010
     *  Test for invalid lower ID
     *  ==========
     *  Inputs: address.validateID( -Long.MAX_VALUE);
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address010() {
        try {
            address.validateID( -Long.MAX_VALUE);
            fail("Exception expected.");
        } catch (DB_AddressExceptionHandler e) {
            assertEquals("ID must be 0 or greater.", e.getMessage());
        }
    }

    /** TEST 011
     *  Test for valid upper ID
     *  ==========
     *  Inputs: address.validateID( Long.MAX_VALUE);
     *  ==========
     *  Expected Outputs:   DB_AddressExceptionHandler = "eir_code invalid length."
     */
    public void testDB_Address011() {
        try {
            assertEquals( Long.MAX_VALUE, address.validateID( Long.MAX_VALUE));
        } catch (DB_AddressExceptionHandler e) {
            fail("Exception expected.");
        }
    }

}