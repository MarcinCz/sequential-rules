package pl.mczpk.med.sr.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.util.LimitedQueue;

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
		LimitedQueue<SequenceItem> visitedItemsWithinGap = new LimitedQueue<SequenceItem>(getMaxGapBetweenSequenceItems());
		Set<Sequence> sequencesSet = new HashSet<Sequence>();
		for(SequenceItem sequenceItem: sequence.getSequenceItems()) {
			for(SequenceItem visitedItem: visitedItemsWithinGap) {
				
				//add simple pair-sequence with all elements from items
				Sequence pairSequence = new Sequence();
				pairSequence.addFirstItem(visitedItem);
				pairSequence.addLastItem(sequenceItem);
				sequencesSet.add(pairSequence);
				
				//add pair-sequences with only-POS element in items for all possible combinations
				if(visitedItem.getElements().size() == 2) {
					Sequence pairSequenceFirstElementPOS = new Sequence();
					SequenceItem itemPOS = new SequenceItem(visitedItem.getElements().get(1));
					pairSequenceFirstElementPOS.addFirstItem(itemPOS);
					pairSequenceFirstElementPOS.addLastItem(sequenceItem);
					sequencesSet.add(pairSequenceFirstElementPOS);
				}
				
				if(sequenceItem.getElements().size() == 2) {
					Sequence pairSequenceSecondElementPOS = new Sequence();
					SequenceItem itemPOS = new SequenceItem(sequenceItem.getElements().get(1));
					pairSequenceSecondElementPOS.addFirstItem(visitedItem);
					pairSequenceSecondElementPOS.addLastItem(itemPOS);
					sequencesSet.add(pairSequenceSecondElementPOS);
				}
				
				if(visitedItem.getElements().size() == 2 && sequenceItem.getElements().size() == 2) {
					Sequence pairSequenceBothElementPOS = new Sequence();
					SequenceItem itemPOSFirst = new SequenceItem(visitedItem.getElements().get(1));
					SequenceItem itemPOSSecond = new SequenceItem(sequenceItem.getElements().get(1));
					pairSequenceBothElementPOS.addFirstItem(itemPOSFirst);
					pairSequenceBothElementPOS.addLastItem(itemPOSSecond);
					sequencesSet.add(pairSequenceBothElementPOS);
				}
			}
			visitedItemsWithinGap.add(sequenceItem);
		}
		return sequencesSet;
	}
	
	protected void addSequenceToStorage(Sequence sequence) {
		sequences.add(sequence);
	}
	
	protected abstract int getMaxGapBetweenSequenceItems();
	
	protected abstract int getMinSequenceSupport();
}
