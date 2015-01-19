package pl.mczpk.med.sr.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;
import pl.mczpk.med.sr.algorithm.SequenceItem;
import pl.mczpk.med.sr.util.LimitedQueue;

abstract class AbstractSequenceStorage implements SequenceStorage {

	private final Logger logger = Logger.getLogger(this.getClass());

	private List<Sequence> sequences = new ArrayList<Sequence>();

	private List<StoredSequenceInfo> storedFrequentSequences = new ArrayList<StoredSequenceInfo>();
	private List<Sequence> notFrequendSequences = new ArrayList<Sequence>();
	
	private Map<SequenceItem, List<StoredSequenceInfo>> pairsStartingWithItemMap = new HashMap<SequenceItem, List<StoredSequenceInfo>>();
	
	private Map<SequenceItem, List<StoredSequenceInfo>> pairsEndingWithItemMap = new HashMap<SequenceItem, List<StoredSequenceInfo>>();
	
	
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
				sequencesSet.addAll(getAllSequenceCombinations(visitedItem, sequenceItem));
			}
			visitedItemsWithinGap.add(sequenceItem);
		}
		return sequencesSet;
	}
	
	static Set<Sequence> getAllSequenceCombinations(SequenceItem itemFirst, SequenceItem itemSecond) {
		Set<Sequence> possibleSequences = new HashSet<Sequence>();
		for (int i = 0; i < itemFirst.getElements().size(); i++) {
			for (int j = 0; j < itemSecond.getElements().size(); j++) {
				SequenceItem newItemFirst = new SequenceItem(itemFirst.getElements().subList(i, itemFirst.getElements().size()).toArray(new String[0]));
				SequenceItem newItemSecond = new SequenceItem(itemSecond.getElements().subList(j, itemSecond.getElements().size()).toArray(new String[0]));
				possibleSequences.add(new Sequence(newItemFirst, newItemSecond));
			}
		}
		return possibleSequences;
	}
	
	
	@Override
	public Sequence expand(Sequence sequece) {
		return null;
	}
	
	protected void addSequenceToStorage(Sequence sequence) {
		sequences.add(sequence);
	}
	
	protected abstract int getMaxGapBetweenSequenceItems();
	
	protected abstract int getMinSequenceSupport();
}
