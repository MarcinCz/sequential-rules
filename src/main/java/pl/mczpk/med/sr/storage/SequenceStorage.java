package pl.mczpk.med.sr.storage;

import java.util.Set;

import pl.mczpk.med.sr.algorithm.Sequence;

public interface SequenceStorage {

	public SequenceInfo getSequenceInfo(Sequence sequence);
	public Set<Sequence> getFrequentPairSequences();
}
