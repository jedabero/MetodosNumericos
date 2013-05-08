/**
 * 
 */
package resources;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * @author Jedabero
 *
 */
public class LangResource extends ResourceBundle {
	
	/**
	 * @param key
	 * @return the string of the given key
	 */
	public String s(String key){
		return getString(key);
	}
	
	
	
	@Override
	protected Object handleGetObject(String key) {
		// TODO handleGetObject
		return null;
	}

	@Override
	public Enumeration<String> getKeys() {
		// TODO getKeys (Enumeration)
		return null;
	}
	
	/**
	 * @param parent
	 */
	public LangResource(ResourceBundle parent){
		setParent(parent);
	}
	
}
