package pl.mczpk.med.sr.storage;

import java.util.List;

import pl.mczpk.med.sr.algorithm.Sequence;
import pl.mczpk.med.sr.algorithm.SequenceInfo;

public interface StoredSequenceInfo extends SequenceInfo {
	public List<Sequence> getStoringSequnces();

}
