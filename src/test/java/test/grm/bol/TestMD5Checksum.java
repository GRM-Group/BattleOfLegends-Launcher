package test.grm.bol;

import java.io.File;

import org.junit.Test;

public class TestMD5Checksum {
	
	@Test
	public final void testGetFileChecksum() {
		File file = new File(
				"C:\\Users\\£ukasz\\AppData\\Roaming\\BOL\\BoL-Launcher-0.0.2-SNAPSHOT.jar");
		// String checksum = MD5HashChecksum.getFileChecksum(file);
		
		// assertThat(checksum,
		// containsString("7e88d862a1b3f7e197e06d8792c03690"));
	}
	
	@Test
	public final void testEqualmd5Checksums() {
		File file = new File(
				"C:\\Users\\£ukasz\\AppData\\Roaming\\BOL\\BoL-Launcher-0.0.2-SNAPSHOT.jar");
		// String fileName = file.getName();
		// String checksum = MD5HashChecksum.getFileChecksum(file);
		// boolean result = MD5HashChecksum.equalmd5Checksums(checksum, file);
		// assertThat(result, is(true));
	}
	
	@Test
	public final void testGetOriginalChecksumForFile() {
		File file = new File(
				"C:\\Users\\£ukasz\\AppData\\Roaming\\BOL\\BoL-Launcher-0.0.2-SNAPSHOT.jar");
		String result = null;
		// try {
		// result = MD5HashChecksum.getOriginalChecksumForFile(file);
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
		// assertThat(result,
		// containsString("7e88d862a1b3f7e197e06d8792c03690"));
	}
	
	@Test
	public final void testIsFileCorrect() {
		File file = new File(
				"C:\\Users\\£ukasz\\AppData\\Roaming\\BOL\\BoL-Launcher-0.0.2-SNAPSHOT.jar");
		boolean result = false;
		// try {
		// result = MD5HashChecksum.isFileCorrect(file);
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
		// assertThat(result, is(true));
	}
}
