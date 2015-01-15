package pl.mczpk.med.sr.config;

import java.io.File;
import java.util.List;

public interface AlgorithmConfig {

	public List<File> getTextFiles();
	public int getMinRuleSupport();
	public int getMaxGapBetweenSequenceItems();
	public boolean isTaxonomyEnabled();
}
