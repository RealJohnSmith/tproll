package com.darkyen.tproll.util.prettyprint;

import com.darkyen.tproll.util.PrettyPrinter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Implementation of pretty printing of {@link File}s.
 */
public class PrettyPrinterFileModule implements PrettyPrinter.PrettyPrinterModule {

	@Override
	public boolean accepts(@NotNull Object item) {
		return item instanceof File;
	}

	@Override
	public void append(@NotNull StringBuilder sb, @NotNull Object item, int maxCollectionElements) {
		final File file = (File)item;

		final File absoluteFile = file.getAbsoluteFile();
		File canonicalFile = null;
		try {
			canonicalFile = file.getCanonicalFile();
		} catch (Exception ignored) {}

		if (canonicalFile == null) {
			sb.append(absoluteFile.getPath());
		} else {
			sb.append(canonicalFile.getPath());
		}

		if (absoluteFile.isDirectory()) {
			sb.append('/');
		} else if (!absoluteFile.exists()) {
			sb.append(" ⌫");
		}
	}
}
