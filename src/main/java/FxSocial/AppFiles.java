package FxSocial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class AppFiles {
	private static final String APP_DIRECTORY = ".social-media-management-system";

	private AppFiles() {
	}

	static File getLoadDirectory() {
		Path bundledSamples = Path.of("src", "main", "resources", "data").toAbsolutePath().normalize();
		if (Files.isDirectory(bundledSamples)) {
			return bundledSamples.toFile();
		}
		Path dataDirectory = getDataDirectory();
		try {
			Files.createDirectories(dataDirectory);
		} catch (IOException e) {
			return Path.of(System.getProperty("user.home")).toFile();
		}
		return dataDirectory.toFile();
	}

	static Path getExportFile(String fileName) throws IOException {
		Path exportDirectory = getDataDirectory().resolve("exports");
		Files.createDirectories(exportDirectory);
		return exportDirectory.resolve(fileName);
	}

	private static Path getDataDirectory() {
		return Path.of(System.getProperty("user.home"), APP_DIRECTORY).toAbsolutePath().normalize();
	}
}
