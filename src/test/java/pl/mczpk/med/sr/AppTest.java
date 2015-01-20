package pl.mczpk.med.sr;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.config.AlgorithmConfig;
import pl.mczpk.med.sr.storage.ConfigBasedSequenceStorage;

@Ignore("test dla duzego zbioru - dlugo trwa")
public class AppTest {

	@Test
	public void test() {
		List<File> textFiles = new ArrayList<File>();
		textFiles.addAll(getFilesFromFolder("C:/Studia/MGR1/MED/20_newsgroups/alt.atheism"));

		AlgorithmConfig config = mock(AlgorithmConfig.class);
		when(config.getMaxGapBetweenSequenceItems()).thenReturn(2);
		when(config.getMinRuleSupport()).thenReturn(5);
		when(config.getTextFiles()).thenReturn(textFiles);

		ConfigBasedSequenceStorage storage = new ConfigBasedSequenceStorage(config);
		Set<Sequence> pairs = storage.getFrequentPairSequences();
		Iterator<Sequence> iterator = pairs.iterator();
		iterator.next();
		Sequence expanded = storage.expand(iterator.next());
		System.out.println(expanded);
	}

	private List<File> getFilesFromFolder(String folderName) {
		File folder = new File(folderName);
		assertTrue(folder.exists());
		return Arrays.asList(folder.listFiles());
	}

}
