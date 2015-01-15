package pl.mczpk.med.sr.storage;

import java.util.Set;

import pl.mczpk.med.sr.algorithm.Sequence;

public interface SequenceStorage {
	public boolean isSequenceFrequent(Sequence sequence);
	public Set<Sequence> getFrequentPairSequences();
}
