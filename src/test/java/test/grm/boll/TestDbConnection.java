package test.grm.boll;

import static org.junit.Assert.fail;

import org.junit.Test;

import pl.grm.boll.database.DBConnect;

public class TestDbConnection {

	@Test
	public void testRegister() {
		DBConnect connect = new DBConnect();
		try {
			connect.register("ann", null);
		} catch (Exception e) {
			fail("Error: " + e.getLocalizedMessage());
		}
	}
}
