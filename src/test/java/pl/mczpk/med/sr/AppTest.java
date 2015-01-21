package pl.mczpk.med.sr;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import pl.mczpk.med.sr.algorithm.FrequentWordSequenceFinder;
import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.storage.ConfigBasedSequenceStorage;

//@Ignore("test dla duzego zbioru - dlugo trwa")
public class AppTest {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Test
	public void test() {
		List<File> textFiles = new ArrayList<File>();
		textFiles.addAll(getFilesFromFolder("D:/downloads/20_newsgroups.tar/20_newsgroups/20_newsgroups/alt.atheism"));

		AlgorithmConfig config = mock(AlgorithmConfig.class);
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(5);
		when(config.getTextFiles()).thenReturn(textFiles);

		FrequentWordSequenceFinder sequenceFinder = new FrequentWordSequenceFinder();
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config);
		List<Sequence> sequenceList = sequenceFinder.findMaximalFrequentSequence(storage);
		
		for(Sequence seq : sequenceList)
			logger.info(seq);
		
//		Set<Sequence> pairs = storage.getFrequentPairSequences();
//		Iterator<Sequence> iterator = pairs.iterator();
//		iterator.next();
//		Sequence expanded = storage.expand(iterator.next());
//		System.out.println(expanded);
	}
	
	private List<File> getFilesFromFolder(String folderName) {
		File folder = new File(folderName);
		assertTrue(folder.exists());
		return Arrays.asList(folder.listFiles());
	}

}
