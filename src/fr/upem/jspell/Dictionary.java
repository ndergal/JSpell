package fr.upem.jspell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;

import fr.upem.jspell.tri.*;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class Dictionary {
	private final String path;
	private final String language;
	private int wordNb;
	private TreeMap<String,WordDictionnary> words;

	/** Constructeur de la classe Dictionary.
	 * @param path Chemin du Dictionnaire
	 * @param language Langue du Dictionnaire
	 */
	public Dictionary(String path,String language) {
		this.path = path;
		this.language = language;
		this.words = new TreeMap<String,WordDictionnary>();

		try(Scanner scanner = new Scanner(FileSystems.getDefault().getPath(path))) {
			scanner.useDelimiter("[\\p{Punct}\\p{Space}]+");
			while(scanner.hasNext()) {
				String word = scanner.next();
				int freq = Integer.parseInt(scanner.next());
				this.addWord(new WordDictionnary(word,freq));
			}
			scanner.close();
		}
		catch(IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

	/** Constructeur de la classe Dictionary.
	 * @param path Chemin du Dictionnaire
	 * 
	 */
	public Dictionary(String path) {
		this(path,"inconnu");
	}

	/** Retourne la TreeMap du Dictionary.
	 * @return TreeMap<String,WordDictionnary> - TreeMap des mots du Dictionnaire
	 */
	public TreeMap<String,WordDictionnary> getWords() {
		return words;
	}

	/** Ajoute un mot à la TreeMap du Dictionary.
	 * @param word Mot a ajouter dans le Dictionnaire
	 * 
	 */
	public void addWord(WordDictionnary word){
		this.words.put(word.getStr().toLowerCase(),word);
		this.wordNb+=1;
	}

	/** Renvoit le nombre de mots inconnus de la Collection reçue en paramètre.
	 * @param words Collections de mots à rechercher dans le Dictionnaire
	 * @return int le nombre de mots inconnus
	 */
	public int getPourcentageOfUnknownWord(Collection<Word> words) {
		int nbUnknownWord = 0;
		for(Word w : words) {
			if(!findWord(w))
				nbUnknownWord++;
		}
		return nbUnknownWord;
	}

	/** Renvoit une Collection contenant les suggestions triés par rapport au méthodes : Soundex,Levenshtein et Hamming.
	 * @param word mot à tester
	 * @return Collection<WordDictionnary> Collections de propositions pour corriger le mots reçu en paramètre
	 */
	public Collection<WordDictionnary> getSuggestions(Word word) {
		ArrayList<WordDictionnary> wordsSoundex = new ArrayList<WordDictionnary>();
		String soundexWord = Soundex.getSoundex(word.getStr());

		Collection<WordDictionnary> wordsHamming = new ArrayList<WordDictionnary>();
		int minDistanceHamming = word.getStr().length();
		int hG, hD;

		Collection<WordDictionnary> wordsLevenshtein = new ArrayList<WordDictionnary>();
		int minDistanceLevenshtein = word.getStr().length();
		int leven;

		for(WordDictionnary wordDic : words.values()) {
			/* Soundex */
			if(soundexWord.equals(Soundex.getSoundex(wordDic.getStr()))) {
				wordsSoundex.add(wordDic);
				continue;
			}

			/* Hamming */
			hG = Hamming.getHammingGauche(word.getStr(), wordDic.getStr());
			hD = Hamming.getHammingDroite(word.getStr(), wordDic.getStr());
			if(minDistanceHamming > hG || minDistanceHamming > hD) {
				wordsHamming.clear();
				minDistanceHamming = (hG < hD) ? hG : hD;
				wordsHamming.add(wordDic);
			}
			else if(minDistanceHamming == hG || minDistanceHamming == hD)
				wordsHamming.add(wordDic);


			/* Levenshtein */
			leven = Levenshtein.distance(word.getStr(), wordDic.getStr());
			if(minDistanceLevenshtein > leven) {
				wordsLevenshtein.clear();
				minDistanceLevenshtein = leven;
				wordsLevenshtein.add(wordDic);
			}
			else if(minDistanceLevenshtein == leven)
				wordsLevenshtein.add(wordDic);

		}
		wordsHamming.addAll(wordsLevenshtein);
		wordsSoundex.addAll(wordsHamming);
		Collections.sort(wordsSoundex, (w1, w2)->(w1.getFrequency() < w2.getFrequency())?0:1);
		Collection<WordDictionnary> wordsSuggestion = new ArrayList<WordDictionnary>();
		for(WordDictionnary wd : wordsSoundex) {
			if(!(wordsSuggestion.contains(wd))) {
				wordsSuggestion.add(wd);
				if(wordsSuggestion.size() >= 5)
					break;
			}
		}
		return wordsSuggestion;
	}

	/** Renvoit la langue du Dictionary.
	 * @return String renvoit la langue du Dictionnaire
	 */
	public String getLanguage() {
		return language;
	}

	/**Renvoit si le mot est contenu ou non.
	 * @param word mot à tester
	 * @return boolean Si le mots est dans le Dictionnaire ou non.
	 */
	public boolean findWord(Word word) {
		return words.containsKey(word.getStr().toLowerCase());
	}

	/**Renvoit le nombre de mots du Dictionary.
	 * @return int le nombre de mots contenu dans le Dictionnaire
	 */
	public int getWordNb() {
		return wordNb;
	}

	/** Ecris le Dictionary dans un fichier portant le nom de son path.
	 * 
	 */
	public void write(){
		try{
			Collection<WordDictionnary> collecdico = this.words.values();
			FileWriter fw = new FileWriter(path, false);
			BufferedWriter output = new BufferedWriter(fw);
			for(WordDictionnary wd: collecdico){
				output.write(wd.toString());
				output.write("\r\n"); 
				output.flush();
			}
			fw.close();
			output.close();
		}
		catch(IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Collection<WordDictionnary> result = words.values();
		for( WordDictionnary w : result ) {
			sb.append(w.toString());
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}
}
