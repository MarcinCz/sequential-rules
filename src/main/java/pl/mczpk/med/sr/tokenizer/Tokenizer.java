package pl.mczpk.med.sr.tokenizer;

import java.util.List;

public interface Tokenizer {
	public interface Token {
		public String getValue();
		/**
		 * Before getting POS check if it's attached with {@link #isPOSAttached()}. If it's not then returned value is undefined.
		 */
		public String getPOS();
		public boolean isPOSAttached();
	}
	
	public List<Token> tokenize(String text, boolean attachPOS);
}
