package pl.mczpk.med.sr.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputConfigReaderTest {

	@Test
	public void shouldReadCorrectConfig() {
		AlgorithmConfig config = AlgorithmConfigReader.readFromFile("src/test/resources/inputconfig/correct.conf");
		
		assertEquals(2, config.getTextFiles().size());
		assertEquals("file1.txt", config.getTextFiles().get(0).getName());
		assertEquals("file2.txt", config.getTextFiles().get(1).getName());
		assertEquals(1, config.getMinRuleSupport());
		assertEquals(2, config.getMaxGapBetweenSequenceItems());
		assertEquals(true, config.isTaxonomyEnabled());
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckRequiredKeys() {
		AlgorithmConfigReader.readFromFile("src/test/resources/inputconfig/incomplete.conf");
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckIntValues() {
		AlgorithmConfigReader.readFromFile("src/test/resources/inputconfig/incorrectInt.conf");
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldCheckBooleanValues() {
		AlgorithmConfigReader.readFromFile("src/test/resources/inputconfig/incorrectBool.conf");
	}
	
	@Test
	public void shouldReturnDefaultValues() {
		AlgorithmConfig config = AlgorithmConfigReader.readFromFile("src/test/resources/inputconfig/correctWithoutDefaults.conf");
		
		assertEquals(2, config.getTextFiles().size());
		assertEquals("file1.txt", config.getTextFiles().get(0).getName());
		assertEquals("file2.txt", config.getTextFiles().get(1).getName());
		assertEquals(1, config.getMinRuleSupport());
		assertEquals(2, config.getMaxGapBetweenSequenceItems());
		assertEquals(Boolean.parseBoolean(AlgorithmConfigReader.TAXONOMY_ENABLED_DEFAULT_VALUE), config.isTaxonomyEnabled());
	}

}
