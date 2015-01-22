package pl.mczpk.med.sr;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import pl.mczpk.med.sr.algorithm.FrequentWordSequenceFinder;
import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.storage.ConfigBasedSequenceStorage;

@Ignore("test dla duzego zbioru - dlugo trwa i trzeba lokalna sciezke podac")
public class AppTest {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Test
	public void test() {
		List<File> textFiles = new ArrayList<File>();
		textFiles.addAll(getFilesFromFolder("D:/downloads/20_newsgroups.tar/20_newsgroups/20_newsgroups/temp"));

		AlgorithmConfig config = mock(AlgorithmConfig.class);
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(8);
		when(config.getTextFiles()).thenReturn(textFiles);
//		when(config.isTaxonomyEnabled()).thenReturn(true);

		FrequentWordSequenceFinder sequenceFinder = new FrequentWordSequenceFinder();
		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config);
		long startTime = System.currentTimeMillis();
		List<Sequence> sequenceList = sequenceFinder.findMaximalFrequentSequence(storage);
		long crTime = System.currentTimeMillis() - startTime;
		
		int max = 0;
		Sequence maxSeq = null;
		for(Sequence seq : sequenceList) {
				if (seq.getSequenceItems().size() > max){
					maxSeq = seq;
					max = maxSeq.getSequenceItems().size();
				}
			   logger.info("Support: " + storage.getSequenceInfo(seq, null).getSupport() + ", sequence: " + seq);
			  }
		logger.info(sequenceList.size() + " sequences has been found in " + crTime + " ms");
	}
	
	private List<File> getFilesFromFolder(String folderName) {
		File folder = new File(folderName);
		assertTrue(folder.exists());
		return Arrays.asList(folder.listFiles());
	}

}
