package pl.grm.boll.config;
import java.util.Comparator;

public class VersionComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		String version1 = (String) o1;
		String version2 = (String) o2;

		VersionTokenizer tokenizer1 = new VersionTokenizer(version1);
		VersionTokenizer tokenizer2 = new VersionTokenizer(version2);

		int n1 = 0;
		int n2 = 0;
		String suffix1 = "";
		String suffix2 = "";

		while (tokenizer1.moveNext()) {
			if (!tokenizer2.moveNext()) {
				do {
					n1 = tokenizer1.getNumber();
					suffix1 = tokenizer1.getSuffix();
					if (n1 != 0 || suffix1.length() != 0) {
						return 1;
					}
				} while (tokenizer1.moveNext());
				return 0;
			}
			n1 = tokenizer1.getNumber();
			suffix1 = tokenizer1.getSuffix();
			n2 = tokenizer2.getNumber();
			suffix2 = tokenizer2.getSuffix();

			if (n1 < n2) {
				return -1;
			}

			if (n1 > n2) {
				return 1;
			}

			boolean empty1 = suffix1.length() == 0;
			boolean empty2 = suffix2.length() == 0;

			if (empty1 && empty2) {
				continue;
			}

			if (empty1) {
				return 1;
			}

			if (empty2) {
				return -1;
			}
			int result = suffix1.compareTo(suffix2);

			if (result != 0) {
				return result;
			}
		}

		if (tokenizer2.moveNext()) {
			do {
				n2 = tokenizer2.getNumber();
				suffix2 = tokenizer2.getSuffix();
				if (n2 != 0 || suffix2.length() != 0) {
					return -1;
				}
			} while (tokenizer2.moveNext());
			return 0;
		}
		return 0;
	}
}
