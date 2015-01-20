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
	public void shouldReturnSequenceInfoForFrequentSequencesWithGivenStoredSubsequences() {
		when(config.getMinRuleSupport()).thenReturn(1);
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		storage.getFrequentPairSequences();
		SequenceItem itemA = new SequenceItem("a");
		SequenceItem itemB = new SequenceItem("b");
		SequenceItem itemD = new SequenceItem("d");
		
		//put breakpoint on line where we return info for all documents, because info for subsequences are not present
		//debugger should not stop on that breakpoint
		SequenceInfo info = storage.getSequenceInfo(new Sequence(itemA, itemA, itemB), Arrays.asList(new Sequence(itemA, itemA), new Sequence(itemA, itemB)));
		assertEquals(true, info.isFrequent());
		assertEquals(2, info.getSupport());
		
		info = storage.getSequenceInfo(new Sequence(itemA, itemB, itemD), Arrays.asList(new Sequence(itemA, itemB), new Sequence(itemB, itemD)));
		assertEquals(true, info.isFrequent());
		assertEquals(3, info.getSupport());

		info = storage.getSequenceInfo(new Sequence(itemA, itemA, itemB, itemD), Arrays.asList(new Sequence(itemA, itemB, itemD), new Sequence(itemA, itemA, itemB)));
		assertEquals(true, info.isFrequent());
		assertEquals(2, info.getSupport());
	}
	
	@Test
	public void shouldReturnSequenceInfoForFrequentSequencesWithGivenNotStoredSubsequences() {
		when(config.getMinRuleSupport()).thenReturn(1);
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		SequenceItem itemA = new SequenceItem("a");
		SequenceItem itemB = new SequenceItem("b");
		
		//put breakpoint on line where we return info for all documents, because info for subsequences are not present
		//debugger should stop on that breakpoint
		SequenceInfo info = storage.getSequenceInfo(new Sequence(itemA, itemA, itemB), Arrays.asList(new Sequence(itemA, itemA), new Sequence(itemA, itemB)));
		assertEquals(true, info.isFrequent());
		assertEquals(2, info.getSupport());
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
	
	@Test
	public void shouldExpandSequence() {
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config, SequenceStorageTestTokenizerMocks.getTwoFilesTokenizer(false));
		storage.getFrequentPairSequences();
		SequenceItem itemA = new SequenceItem("a");
		SequenceItem itemB = new SequenceItem("b");
		SequenceItem itemC = new SequenceItem("c");
		
		Sequence expanded = storage.expand(new Sequence(itemA, itemB));
		assertEquals(new Sequence(itemA, itemB, itemC), expanded);
		
		expanded = storage.expand(new Sequence(itemA, itemC));
		assertEquals(new Sequence(itemA, itemB, itemC), expanded);
		
		expanded = storage.expand(new Sequence(itemB, itemC));
		assertEquals(new Sequence(itemA, itemB, itemC), expanded);
		
		expanded = storage.expand(new Sequence(itemA, itemB, itemC));
		assertEquals(new Sequence(itemA, itemB, itemC), expanded);
	}
	
	private void printSequences(Set<Sequence> sequences) {
		for(Sequence seq: sequences) {
			System.out.println(seq);
		}
	}
 
}
