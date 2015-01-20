package pl.mczpk.med.sr.storage;

import java.util.List;
import java.util.Set;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;

public interface SequenceStorage {

	/**
	 * @deprecated use {@link #getSequenceInfo(Sequence, List)} instead
	 */
	public SequenceInfo getSequenceInfo(Sequence sequence);
	
	/**
	 * Returns {@link SequenceInfo} for given sequence, checking only documents in which given subsequences are frequent.
	 * @return
	 */
	public SequenceInfo getSequenceInfo(Sequence sequence, List<Sequence> subsequences);
	
	public Set<Sequence> getFrequentPairSequences();
	/**
	 * Returns frequent sentence with one item added to given sequence. 
	 * If there is no longer frequent sentence (given sequence is maximal) then the same sequence is returned. 
	 */
	public Sequence expand(Sequence sequence);
}
