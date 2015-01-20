package pl.mczpk.med.sr.storage;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import pl.mczpk.med.sr.tokenizer.Tokenizer;
import pl.mczpk.med.sr.tokenizer.Tokenizer.Token;

public class SequenceStorageTestTokenizerMocks {

	public static Tokenizer getOneFileTokenizer(boolean attachPOS) {
		Tokenizer tokenizer = mock(Tokenizer.class);
		List<Token> tokensFirst = getTokensFirstExample(attachPOS);
		when(tokenizer.tokenize(anyString(), anyBoolean())).thenReturn(tokensFirst);
		return tokenizer;
	}
	
	public static Tokenizer getTwoFilesTokenizer(boolean attachPOS) {
		Tokenizer tokenizer = mock(Tokenizer.class);
		List<Token> tokensFirst = getTokensFirstExample(attachPOS);
		List<Token> tokensSecond = getTokensSecondExample(attachPOS);
		when(tokenizer.tokenize(anyString(), anyBoolean())).thenReturn(tokensFirst).thenReturn(tokensSecond);
		return tokenizer;
	}

	private static List<Token> getTokensFirstExample(boolean attachPOS) {
		return createTokenList("aN bV bV cN dV aN", attachPOS);
	}


	private static List<Token> getTokensSecondExample(boolean attachPOS) {
		return createTokenList("aN aN bV dV cN cN", attachPOS);
	}
	
	private static List<Token> createTokenList(String text, boolean attachPOS) {
		List<Token> tokens = new ArrayList<Token>();
		for(String val: text.split(" ")) {
			tokens.add(getToken(val.substring(0, 1), val.substring(1, 2), attachPOS));
		}
		return tokens;
	}
	
	private static Token getToken(String value, String POS, boolean isPOSAttached) {
		Token token = mock(Token.class);
		when(token.getPOS()).thenReturn(POS);
		when(token.getValue()).thenReturn(value);
		when(token.isPOSAttached()).thenReturn(isPOSAttached);
		when(token.toString()).thenReturn(value + POS);
		return token;
	}
}
