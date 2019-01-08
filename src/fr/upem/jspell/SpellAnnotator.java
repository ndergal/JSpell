package fr.upem.jspell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class SpellAnnotator {
	private Collection<Word> words;
	private Dictionary dictionnary;
	private int size;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SpellAnnotator sa = new SpellAnnotator(sc);
		ArrayList<Dictionary> dic = new ArrayList<Dictionary>();
		String[] tmp;
		Collection<WordDictionnary> sugges;
		HashMap<String,Collection<WordDictionnary>> inconnu = new HashMap<>();
		for(String word : args) {
			tmp = word.split(":");
			dic.add(new Dictionary(tmp[1], tmp[0]));
		}
		sa.setLanguage(dic);
		for(Word word : sa.getWords()) {
			if(sa.getDictionnary().findWord(word)){
				System.out.print(word + " ");
			} else {
				if(inconnu.containsKey(word.getStr())){
					sugges = inconnu.get(word.getStr());
				}
				else{
					sugges = sa.getDictionnary().getSuggestions(word);
					inconnu.put(word.getStr(), sugges);
				}
				StringBuilder str = new StringBuilder();
				str.append("<spell>").append(word).append(":");
				for(WordDictionnary wordD : sugges)
					str.append(wordD.getStr()).append(",");
				str.setLength(str.length()-1);
				str.append("</spell>");
				System.out.println(str);
			}
		}

	}

	/** Constructeur du SpellAnnotator
	 * @param scanner Scanner à parser pour remplir le SpellAnnotator
	 */
	public SpellAnnotator(Scanner scanner) {
		words = new ArrayList<Word>();
		size = 0;
		scanner.useDelimiter("[\\p{Punct}\\p{Space}]+");
		while(scanner.hasNext()) {
			String word = scanner.next();
			addWord(new Word(word));
		}
	}

	/**Renvoit la langue du dictionnaire correspondant le plus au SpellAnnotator.
	 * @param lstDicts ArrayList de Dictionnaire à tester
	 * @return String  le nom du Dictionnaire qui corresponds le plus au SpellAnnotator.
	 */
	public String setLanguage(ArrayList<Dictionary> lstDicts) {
		int maxNbUnknownWord = getSize();
		int nbUnknownWord;

		for(Dictionary dic : lstDicts) {
			nbUnknownWord = dic.getPourcentageOfUnknownWord(words);
			if( nbUnknownWord < maxNbUnknownWord ) {
				maxNbUnknownWord = nbUnknownWord;
				dictionnary = dic;
			}
		}
		return dictionnary.getLanguage();
	}

	/**Renvoit la Collection<words> du SpellAnnotator
	 * @return Collection<Word> La Collection de mot contenu dans la SpellAnnotator.
	 */
	public Collection<Word> getWords() {
		return words;
	}

	/**Renvoit le Dictionary du SpellAnnotator
	 * @return Dictionary Le Dictionnaire du SpellAnnotator.
	 */
	public Dictionary getDictionnary() {
		return dictionnary;
	}
	
	
	/** Ajoute un word à la Collection<word> du SpellAnnotator.
	 * @param word Mot à ajouter dans la Collection du SpellAnnotator.
	 */
	public void addWord(Word word) {
		words.add(word);
		size++;
	}

	
	/**Renvoit si la Collection<word> du SpellAnnotator contient le Word.
	 * @param word Mot à rechercher dans le SpellAnnotator.
	 * @return boolean Si le mot est contenu ou non.
	 */
	public boolean findWord(WordDictionnary word) {
		return words.contains(word);
	}

	/**Renvoit le nombre de mots que contient le SpellAnnotator.
	 * @return int Le nombre de mot que contient le SpellAnnotator.
	 */
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for( Word w : words ) {
			sb.append(w.toString());
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}
}
