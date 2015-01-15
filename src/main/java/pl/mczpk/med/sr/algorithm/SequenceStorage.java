package pl.mczpk.med.sr.algorithm;

import java.util.Set;

public interface SequenceStorage {
	public boolean isSequenceFrequent(Sequence sequence);
	public Set<Sequence> getFrequentSequencePairs();
}
