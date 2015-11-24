package twitter_nlp_wrapper;

import org.apache.lucene.util.Attribute;

public interface NamedEntityTypeAttribute extends Attribute, Cloneable {
	  /**
	   * Sets the type of the current token.
	   *
	   * @param type type of the current token.
	   */
	  void setType(NamedEntityType type);
	  
	  void setToken(String token);

	  /**
	   * Returns the type of the current token.
	   *
	   * @return type of the current token.
	   */
	  NamedEntityType getType();
	  
	  String getToken();

	Object clone();
}
