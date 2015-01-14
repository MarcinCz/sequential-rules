package pl.mczpk.med.sr.algorithm;

import java.io.File;
import java.util.List;

public interface FrequentPairsFinder {
	public List<SequenceItem> findFrequentPairs(List<File> textFiles, int minimalSupport);
}
