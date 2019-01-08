package fr.upem.jspell;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class WordDictionnary extends Word {
	private int frequency;
	
	/**Constructeur de la classe WordDictionnary.
	 * @param str Le mot
	 * @param frequency La Frequence du mot
	 */
	public WordDictionnary(String str, int frequency) {
		super(str);
		this.frequency = frequency;
	}
	
	/**Constructeur de la classe WordDictionnary.
	 * @param str Le mot
	 *
	 */
	public WordDictionnary(String str) {
		this(str, 1);
	}
	
	/** Renvoit la frequence du WordDictionnary.
	 * @return int La frequence du mot
	 */
	public int getFrequency() {
		return frequency;
	}
	
	/**Modifie la frequence du WordDictionnary.
	 * @param frequency La nouvelle frequence à mettre
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(o== this)
			return false;
		if( !(o instanceof WordDictionnary) )
			return false;
		WordDictionnary word = (WordDictionnary) o;
		return super.equals(word);
	}
	 
	@Override
	public String toString() {
		return super.toString() + "	" + frequency;
	} 
}
