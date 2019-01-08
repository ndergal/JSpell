package fr.upem.jspell;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class Word {
	private String str;
	
	public Word(String str) {
		this.str = str;
	}
	
	/**Renvoit la String contenu dans le Word.
	 * @return String Le mot.
	 */
	public String getStr() {
		return str;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o== this)
			return false;
		if( !(o instanceof Word) )
			return false;
		Word word = (Word) o;
		return str.equals(word.getStr());
	}
	
	@Override
	public String toString() {
		return str;
	}
}
