package fr.upem.jspell;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class DictionaryCreator {

	public static void main(String[] args) {
		TreeMap<String,WordDictionnary> dico = new TreeMap<> ();
		try(Scanner scanner = new Scanner(FileSystems.getDefault().getPath(args[0]))) {
			scanner.useDelimiter("[\\p{Punct}\\p{Space}]+");
			while(scanner.hasNext()) {
				String word = scanner.next().toLowerCase();
				if(!(dico.containsKey(word))){
					dico.put(word,new WordDictionnary(word));}
				else{
					WordDictionnary wd = dico.remove(word);
					wd.setFrequency(wd.getFrequency() + 1);
					dico.put(word,wd);}

			}
		}
		catch(IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
		
		Collection<WordDictionnary> result = dico.values();
		for(WordDictionnary wd : result){
			System.out.println(wd);
		}
	}

}

