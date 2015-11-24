package twitter_nlp_wrapper;

import com.twitter.common.text.token.TokenStream;

public class NerTokenStream extends TokenStream {
	private final NamedEntityTypeAttribute neAttribute;

	private String[] tokens;
	private NamedEntityType[] tags;
	private int currIndex;
	private NamedEntityWrapper tagger;

	public NerTokenStream() {
		this.neAttribute = addAttribute(NamedEntityTypeAttribute.class);
		this.tagger = new NamedEntityWrapper();
	}

	@Override
	public boolean incrementToken() {
		if(this.currIndex == this.tokens.length) {
			return false;
		}
		this.neAttribute.setType(this.tags[this.currIndex]);
		this.neAttribute.setToken(this.tokens[this.currIndex]);
		//System.out.println("token" + Integer.toString(currIndex) + ":" + tokens[currIndex]);
		//System.out.println("tag  " + Integer.toString(currIndex) + ":" + tags[currIndex]);
		//System.out.println(this.neAttribute.getToken());
		//System.out.println(this.neAttribute.getType());
		//System.out.println();
		this.currIndex++;
		return true;
	}
	
	@Override
	public void reset(CharSequence input) {
		if (input == null) {
			return;
		}

		String tokenInput = this.tagger.tagTweet(input.toString());
		if (tokenInput == null) {
			return;
		}

		String[] tagged = tokenInput.split(" ");
		this.tokens = new String[tagged.length];
		this.tags   = new NamedEntityType[tagged.length];
		
		for(int i=0; i<tagged.length; i++) {
			String[] tokenTag = tagged[i].split("/");
			String token = "";
			String tag = "";
			if (tokenTag.length >= 2) {
				token = tokenTag[0];
				tag = tokenTag[1];
			}
			this.tokens[i] = token;
			this.tags[i]   = new NamedEntityType(tag);
		}
		this.currIndex=0;
	}
}
