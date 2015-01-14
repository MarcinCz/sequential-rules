package pl.mczpk.med.sr.config;

import java.io.File;
import java.util.List;

public interface InputConfig {

	public List<File> getTextFiles();
	public int getMinRuleSupport();
	public int getMaxGapBetweenSequenceWords();
	public boolean isTaxonomyEnabled();
}
