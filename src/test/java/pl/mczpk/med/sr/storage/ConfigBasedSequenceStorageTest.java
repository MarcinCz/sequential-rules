package pl.mczpk.med.sr.storage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.config.AlgorithmConfig;

public class ConfigBasedSequenceStorageTest {

	private AlgorithmConfig config = mock(AlgorithmConfig.class);
	private static File emptyFile = new File("src/test/resources/textFiles/emptyFile");
	
	@Test
	public void shouldFindFrequentPairSequencesWithTaxonomy() {
		when(config.getTextFiles()).thenReturn(Arrays.asList(emptyFile, emptyFile, emptyFile));
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(2);
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(true));
		Set<Sequence> sequences = storage.getFrequentPairSequences();
		printSequences(sequences);
	}
	
	@Test
	public void shouldFindFrequentPairSequencesWithoutTaxonomy() {
		when(config.getTextFiles()).thenReturn(Arrays.asList(emptyFile, emptyFile, emptyFile));
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(2);
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		Set<Sequence> sequences = storage.getFrequentPairSequences();
		assertEquals(3, sequences.size());
		printSequences(sequences);
	}
	
	private void printSequences(Set<Sequence> sequences) {
		for(Sequence seq: sequences) {
			System.out.println(seq);
		}
	}
 
}
