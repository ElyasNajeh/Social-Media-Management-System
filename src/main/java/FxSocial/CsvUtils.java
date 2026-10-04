package FxSocial;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class CsvUtils {
	private CsvUtils() {
	}

	static List<List<String>> readRecords(Path path) throws IOException {
		try (Reader fileReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				PushbackReader reader = new PushbackReader(fileReader)) {
			List<List<String>> records = new ArrayList<>();
			List<String> record = new ArrayList<>();
			StringBuilder field = new StringBuilder();
			boolean quoted = false;
			int value;

			while ((value = reader.read()) != -1) {
				char character = (char) value;
				if (character == '"') {
					if (quoted) {
						int next = reader.read();
						if (next == '"') {
							field.append('"');
						} else {
							quoted = false;
							if (next != -1) {
								reader.unread(next);
							}
						}
					} else if (field.toString().trim().isEmpty()) {
						quoted = true;
					} else {
						field.append(character);
					}
				} else if (character == ',' && !quoted) {
					record.add(field.toString().trim());
					field.setLength(0);
				} else if ((character == '\n' || character == '\r') && !quoted) {
					if (character == '\r') {
						int next = reader.read();
						if (next != '\n' && next != -1) {
							reader.unread(next);
						}
					}
					record.add(field.toString().trim());
					field.setLength(0);
					if (!isBlankRecord(record)) {
						records.add(record);
					}
					record = new ArrayList<>();
				} else {
					field.append(character);
				}
			}

			if (quoted) {
				throw new IOException("Unclosed quoted field in " + path);
			}
			if (field.length() > 0 || !record.isEmpty()) {
				record.add(field.toString().trim());
				if (!isBlankRecord(record)) {
					records.add(record);
				}
			}
			return records;
		}
	}

	static String format(Object... values) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				result.append(',');
			}
			String value = String.valueOf(values[i]);
			if (value.indexOf(',') >= 0 || value.indexOf('"') >= 0 || value.indexOf('\n') >= 0
					|| value.indexOf('\r') >= 0) {
				result.append('"').append(value.replace("\"", "\"\"")).append('"');
			} else {
				result.append(value);
			}
		}
		return result.toString();
	}

	private static boolean isBlankRecord(List<String> record) {
		return record.size() == 1 && record.get(0).isBlank();
	}
}
