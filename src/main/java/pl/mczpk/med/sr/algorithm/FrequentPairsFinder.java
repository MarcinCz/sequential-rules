package pl.mczpk.med.sr.algorithm;

import java.io.File;
import java.util.List;

public interface FrequentPairsFinder {
	public List<Sequence> findFrequentPairs(List<File> textFiles, int minimalSupport);
}
