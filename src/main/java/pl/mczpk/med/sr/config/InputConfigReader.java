package pl.mczpk.med.sr.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InputConfigReader {

	private final static String KEY_TEXT_FILE_NAMES = "fileNames";
	private final static String KEY_MIN_RULE_SUPPORT = "minRuleSupport";
	private final static String KEY_MAX_GAP_BETWEEN_WORDS = "maxGapBetweenWords";
	private final static String KEY_TAXONOMY_ENABLED = "taxonomyEnabled";

	private final static String VALUE_LIST_SEPARATOR = ";";
	final static String TAXONOMY_ENABLED_DEFAULT_VALUE = "false";

	public static InputConfig readFromFile(String fileName) {
		Properties prop = new Properties();
		try (InputStream inputStream = new FileInputStream(fileName)) {
			prop.load(inputStream);
			checkRequiredKeys(prop);
			return getConfigFromProperties(prop);

		} catch (FileNotFoundException e) {
			throw new RuntimeException(String.format("Could not find input config file '%s'", fileName), e);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Error while reading input config file '%s'", fileName), e);
		}
	}

	private static InputConfig getConfigFromProperties(Properties prop) {
		final List<File> textFiles = getTextFiles(prop);
		final int minRuleSupport = getValueForIntKey(prop, KEY_MIN_RULE_SUPPORT);
		final int maxGapBetweenWords = getValueForIntKey(prop, KEY_MAX_GAP_BETWEEN_WORDS);
		final boolean isTaxonomyEnabled = getTaxonomyEnabled(prop);
		return new InputConfig() {
			@Override
			public boolean isTaxonomyEnabled() {
				return isTaxonomyEnabled;
			}

			@Override
			public List<File> getTextFiles() {
				return textFiles;
			}

			@Override
			public int getMinRuleSupport() {
				return minRuleSupport;
			}

			@Override
			public int getMaxGapBetweenSequenceWords() {
				return maxGapBetweenWords;
			}
		};
	}

	private static List<File> getTextFiles(Properties prop) {
		String value = prop.getProperty(KEY_TEXT_FILE_NAMES);
		List<File> fileNames = new ArrayList<File>();
		for (String fileName : value.split(VALUE_LIST_SEPARATOR)) {
			fileNames.add(new File(fileName));
		}
		return fileNames;
	}

	private static int getValueForIntKey(Properties prop, String key) {
		String value = prop.getProperty(key);
		try {
			int intValue = Integer.parseInt(value);
			return intValue;
		} catch (NumberFormatException e) {
			throw new IllegalStateException(String.format("Value for key %s must be an integer", key));
		}
	}

	private static boolean getTaxonomyEnabled(Properties prop) {
		String value = prop.getProperty(KEY_TAXONOMY_ENABLED, TAXONOMY_ENABLED_DEFAULT_VALUE);
		if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
			throw new IllegalStateException(String.format("Value for key %s must be 'true' or 'false'", KEY_TAXONOMY_ENABLED));
		} else {
			return value.equalsIgnoreCase("true");
		}
	}

	private static void checkRequiredKeys(Properties prop) {
		for (String key : new String[] { KEY_TEXT_FILE_NAMES, KEY_MIN_RULE_SUPPORT, KEY_MAX_GAP_BETWEEN_WORDS }) {
			if (prop.get(key) == null) {
				throw new IllegalStateException(String.format("Key '%s' in input config is required", key));
			}
		}
	}
}
