package pl.mczpk.med.sr.algorithm;

import java.util.List;

import pl.mczpk.med.sr.storage.SequenceStorage;

public interface MaximalFrequentSequenceFinder {

	public List<Sequence> findMaximalFrequentSequence(SequenceStorage storage);
}
