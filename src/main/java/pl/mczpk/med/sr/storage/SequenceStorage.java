package pl.mczpk.med.sr.storage;

import java.util.Set;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;

public interface SequenceStorage {

	public SequenceInfo getSequenceInfo(Sequence sequence);
	public Set<Sequence> getFrequentPairSequences();
	/**
	 * Returns frequent sentence with one item added to given sequence. 
	 * If there is no longer frequent sentence (given sequence is maximal) then the same sequence is returned. 
	 */
	public Sequence expand(Sequence sequece);
}
