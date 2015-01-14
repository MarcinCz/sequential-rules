package pl.mczpk.med.sr.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputConfigReaderTest {

	@Test
	public void shouldReadCorrectConfig() {
		InputConfig config = InputConfigReader.readFromFile("src/test/resources/correctInputConfig.conf");
		
		assertEquals(2, config.getTextFiles().size());
		assertEquals("file1.txt", config.getTextFiles().get(0).getName());
		assertEquals("file2.txt", config.getTextFiles().get(1).getName());
		assertEquals(1, config.getMinRuleSupport());
		assertEquals(2, config.getMaxGapBetweenSequenceWords());
		assertEquals(true, config.isTaxonomyEnabled());
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckRequiredKeys() {
		InputConfigReader.readFromFile("src/test/resources/incompleteInputConfig.conf");
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckIntValues() {
		InputConfigReader.readFromFile("src/test/resources/incorrectIntInputConfig.conf");
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckBooleanValues() {
		InputConfigReader.readFromFile("src/test/resources/incorrectBoolInputConfig.conf");
	}
	
	@Test
	public void shouldReturnDefaultValues() {
		InputConfig config = InputConfigReader.readFromFile("src/test/resources/correctInputConfigWithoutDefaults.conf");
		
		assertEquals(2, config.getTextFiles().size());
		assertEquals("file1.txt", config.getTextFiles().get(0).getName());
		assertEquals("file2.txt", config.getTextFiles().get(1).getName());
		assertEquals(1, config.getMinRuleSupport());
		assertEquals(2, config.getMaxGapBetweenSequenceWords());
		assertEquals(Boolean.parseBoolean(InputConfigReader.TAXONOMY_ENABLED_DEFAULT_VALUE), config.isTaxonomyEnabled());
	}

}
