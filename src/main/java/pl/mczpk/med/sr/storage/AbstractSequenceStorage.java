package pl.mczpk.med.sr.storage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;

abstract class AbstractSequenceStorage implements SequenceStorage {

	private final Logger logger = Logger.getLogger(this.getClass());

	private List<Sequence> sequences = new ArrayList<Sequence>();

	@Override
	public SequenceInfo getSequenceInfo(Sequence sequence) {
		return null;
	}

	@Override
	public Set<Sequence> getFrequentPairSequences() {
		logger.trace("Getting frequent pair-sequences.");
		Map<Sequence, Integer> sequenceCounterMap = new HashMap<Sequence, Integer>();
		
		//get all pair-sequences with their support
		for(Sequence sequence: sequences) {
			Set<Sequence> pairSequences = getPairSequences(sequence);
			for(Sequence pairSequence: pairSequences) {
				Integer currentSeqCount = sequenceCounterMap.get(pairSequence);
				if(currentSeqCount == null) {
					sequenceCounterMap.put(pairSequence, 1);
				} else {
					sequenceCounterMap.put(pairSequence, currentSeqCount + 1);
				}
			}
		}
		
		//find and return frequent pair-sequences
		int minSupport = getMinSequenceSupport();
		Set<Sequence> frequentPairSequences = new HashSet<Sequence>();
		for(Sequence sequence: sequenceCounterMap.keySet()) {
			if(sequenceCounterMap.get(sequence) > minSupport) {
				frequentPairSequences.add(sequence);
			}
		}
		return frequentPairSequences;
	}
	
	private Set<Sequence> getPairSequences(Sequence sequence) {
		Queue<SequenceItem> visitedItemsWithinGap = new ArrayDeque<SequenceItem>(getMaxGapBetweenSequenceItems());
		Set<Sequence> sequencesSet = new HashSet<Sequence>();
		for(SequenceItem sequenceItem: sequence.getSequenceItems()) {
			for(SequenceItem visitedItem: visitedItemsWithinGap) {
				Sequence pairSequence = new Sequence();
				pairSequence.addFirstItem(visitedItem);
				pairSequence.addLastItem(sequenceItem);
				sequencesSet.add(pairSequence);
			}
		}
		return sequencesSet;
	}
	
	protected void addSequenceToStorage(Sequence sequence) {
		sequences.add(sequence);
	}
	
	protected abstract int getMaxGapBetweenSequenceItems();
	
	protected abstract int getMinSequenceSupport();
}
