package pl.mczpk.med.sr.storage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.config.AlgorithmConfig;

public class ConfigBasedSequenceStorageTest {

	private AlgorithmConfig config = mock(AlgorithmConfig.class);
	private static File emptyFile = new File("src/test/resources/textFiles/emptyFile");
	
	@Before
	public void setUp() {
		when(config.getTextFiles()).thenReturn(Arrays.asList(emptyFile, emptyFile, emptyFile));
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(2);
	}
	
	@Test
	public void shouldFindFrequentPairSequencesWithTaxonomy() {
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(true));
		Set<Sequence> sequences = storage.getFrequentPairSequences();
		printSequences(sequences);
	}
	
	@Test
	public void shouldFindFrequentPairSequencesWithoutTaxonomy() {
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		Set<Sequence> sequences = storage.getFrequentPairSequences();
		assertEquals(3, sequences.size());
		printSequences(sequences);
	}
	
	@Test
	public void shouldReturnSequenceInfoForFrequentSequences() {
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		SequenceItem itemA = new SequenceItem("a");
		SequenceItem itemB = new SequenceItem("b");
		SequenceItem itemC = new SequenceItem("c");
		
		SequenceInfo info = storage.getSequenceInfo(new Sequence(itemA, itemB));
		assertEquals(true, info.isFrequent());
		assertEquals(3, info.getSupport());
		//check if stored info is returned with debugger
		info = storage.getSequenceInfo(new Sequence(itemA, itemB));
		assertEquals(true, info.isFrequent());
		assertEquals(3, info.getSupport());
		
		info = storage.getSequenceInfo(new Sequence(itemA, itemB, itemC));
		assertEquals(true, info.isFrequent());
		assertEquals(3, info.getSupport());
	}
	
	@Test
	public void shouldReturnSequenceInfoForNotFrequentSequences() {
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		SequenceItem itemA = new SequenceItem("a");
		
		SequenceInfo info = storage.getSequenceInfo(new Sequence(itemA, itemA));
		assertEquals(false, info.isFrequent());
		//check if stored info is returned with debugger
		info = storage.getSequenceInfo(new Sequence(itemA, itemA));
		assertEquals(false, info.isFrequent());
		info = storage.getSequenceInfo(new Sequence(itemA, itemA, itemA));
		assertEquals(false, info.isFrequent());
	}
	
	private void printSequences(Set<Sequence> sequences) {
		for(Sequence seq: sequences) {
			System.out.println(seq);
		}
	}
 
}
