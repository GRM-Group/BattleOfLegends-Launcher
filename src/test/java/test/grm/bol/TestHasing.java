package test.grm.bol;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import pl.grm.bol.launcher.math.PasswordHash;

public class TestHasing {
	
	@Test
	public void testHash() {
		String hash = null;
		hash = PasswordHash.hash("asdf", "asrf4n6546d75n");
		assertThat(hash,
				containsString("d29443d3cd76cd2e05c1f5d6ace2bdcd220a57338d58d2f318c9b91c1269a10a"));
	}
}
