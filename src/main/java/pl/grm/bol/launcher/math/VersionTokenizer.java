package pl.grm.bol.launcher.math;

public class VersionTokenizer {
	private String	version;
	private int		length;
	private int		position;
	private int		number;
	private String	suffix;
	private boolean	value;
	
	public VersionTokenizer(String version) {
		if (version == null) { throw new IllegalArgumentException("version jest null"); }
		this.version = version;
		length = version.length();
	}
	
	public boolean moveNext() {
		number = 0;
		suffix = "";
		value = false;
		if (position >= length) { return false; }
		value = true;
		while (position < length) {
			char xD = version.charAt(position);
			if (xD < '0' || xD > '9') {
				break;
			}
			number = number * 10 + (xD - '0');
			position++;
		}
		int suffixStart = position;
		while (position < length) {
			char xD = version.charAt(position);
			if (xD == '.') {
				break;
			}
			position++;
		}
		suffix = version.substring(suffixStart, position);
		if (position < length) {
			position++;
		}
		return true;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public boolean isValue() {
		return value;
	}
}
