package pl.mczpk.med.sr.algorithm;

import java.util.Set;

import pl.mczpk.med.sr.storage.SequenceStorage;

public interface MaximalFrequentSequenceFinder {

	public Set<Sequence> findMaximalFrequentSequence(SequenceStorage storage);
}
