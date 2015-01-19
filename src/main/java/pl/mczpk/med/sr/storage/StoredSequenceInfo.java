package pl.mczpk.med.sr.storage;

import java.util.List;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;

public interface StoredSequenceInfo extends SequenceInfo {
	public List<Sequence> getStoringSequnces();
	
	public static class StoredSequenceInfoImpl implements StoredSequenceInfo {
		final int support;
		final boolean isFrequent;
		final List<Sequence> storingSequences;
		
		public StoredSequenceInfoImpl(int support, boolean isFrequent, List<Sequence> storingSequences) {
			this.support = support;
			this.isFrequent = isFrequent;
			this.storingSequences = storingSequences;
		}

		@Override
		public boolean isFrequent() {
			return isFrequent;
		}

		@Override
		public int getSupport() {
			return support;
		}

		@Override
		public List<Sequence> getStoringSequnces() {
			return storingSequences;
		}
	}
}
