package pl.mczpk.med.sr.algorithm;

public interface SequenceInfo {
	public boolean isFrequent();
	
	/**
	 * Returns support for frequent sequences or 0 otherwise.
	 */
	public int getSupport();
	
	public final static SequenceInfo NOT_FREQUENT_SEQUENCE = new SequenceInfo() {
		
		@Override
		public boolean isFrequent() {
			return false;
		}
		
		@Override
		public int getSupport() {
			return 0;
		}
	};
}
