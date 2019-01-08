package fr.upem.jspell;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class SpellSelector {

	/** Renvoit si l'arguments est présent le tableau de String à un index pair allant de 0 à 4.
	 * @param str Tableau de String à tester
	 * @param test Mot à tester
	 * @return boolean S'il contient le mot ou non.
	 */
	public static boolean testArg(String[] str,String test) {
		for(int i=0;i<5;i+=2){
			if(str[i].equals(test))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		if(args.length!= 6 && ( !testArg(args,"-d") || !testArg(args,"-in") || !testArg(args,"-out")))
			throw new IllegalArgumentException("les arguments saisis ne sont pas bon veuillez saisir une commande du type : \"java fr.upem.jspell.SpellSelector -d customizedDict.txt -in annotatedText.txt -out correctedText.txt\" ");
		String nomDico=null,nomAnnoter=null,nomCorriger=null;

		for(int i=0;i<5;i+=2){
			if(args[i].equals("-d")){
				if(!(args[i+1].charAt(0) == '-')){
					nomDico = args[i+1];
				}
				else{
					throw new IllegalArgumentException("les arguments saisis ne sont pas bon veuillez saisir une commande du type : \"java fr.upem.jspell.SpellSelector -d customizedDict.txt -in annotatedText.txt -out correctedText.txt\" ");
				}	
			}
			if(args[i].equals("-in")){
				if(!(args[i+1].charAt(0) == '-')){
					nomAnnoter = args[i+1];
				}
				else{
					throw new IllegalArgumentException("les arguments saisis ne sont pas bon veuillez saisir une commande du type : \"java fr.upem.jspell.SpellSelector -d customizedDict.txt -in annotatedText.txt -out correctedText.txt\" ");
				}	
			}
			if(args[i].equals("-out")){
				if(!(args[i+1].charAt(0) == '-')){
					nomCorriger = args[i+1];
				}
				else{
					throw new IllegalArgumentException("les arguments saisis ne sont pas bon veuillez saisir une commande du type : \"java fr.upem.jspell.SpellSelector -d customizedDict.txt -in annotatedText.txt -out correctedText.txt\" ");
				}	
			}	
		}

		Dictionary dico = new Dictionary(nomDico);
		int nbdico = dico.getWordNb();
		ArrayList<String> texte = new ArrayList<>();
		HashMap<String,String> ignored = new HashMap<>();
		int nbMot=0;

		try(Scanner scanner = new Scanner(FileSystems.getDefault().getPath(nomAnnoter))){
			Scanner user = new Scanner(System.in);
			FileWriter fw = new FileWriter(nomCorriger, false);
			BufferedWriter output = new BufferedWriter(fw);
			scanner.useDelimiter("[\\p{Space}]+");
			while(scanner.hasNext()) {
				String word = scanner.next();
				if(!(word.contains("<spell>"))){
					texte.add(word);
					nbMot++;
					output.write(word+" "); 
					output.flush();
				}
				else{					
					word = word.subSequence(7, word.length()-9).toString();
					String[] partie = word.split(":");
					word = partie[0];
					if(ignored.containsKey(word)){
						texte.add(word);
						nbMot++;
						output.write(word+" ");
					}
					else{
						String[] partie2 = partie[1].split(",");
						if(nbMot <4){
							for(String s: texte){
								System.out.print(s + " ");
							}
						}
						else{
							System.out.print("... " + texte.get(nbMot-4) + " " + texte.get(nbMot-3) +" " + texte.get(nbMot-2) + " ");
						}
						System.out.println("**"+word+"** ...");	
						System.out.println("Proposition: ");
						for(int i=1;i<=partie2.length;i++){
							System.out.println(i + ". replace with \""+ partie2[i-1]+"\"");
						}
						System.out.println((partie2.length+1) + ". ignore the misspilled word \"" + word +"\"" );
						System.out.println((partie2.length+2) + ". ignore all the occurences of the misspilled word \"" + word +"\"" );
						System.out.println((partie2.length+3) + ". add the word \""+word+"\" in \""+ nomDico + "\"" );
						int userint=0;
						while(userint <= 0 || userint > (partie2.length+3) ){
							try{
								userint = Integer.parseInt(user.next()); 
							}
							catch(NumberFormatException nfe){
								userint = 10;
							}
						}

						if(userint < partie2.length){
							texte.add(partie2[userint-1]);
							nbMot++;
							output.write(partie2[userint-1] + " ");
							output.flush();
						}
						if(userint == partie2.length+1){
							texte.add(word);
							nbMot++;
							output.write(word+ " ");
							output.flush();
						}
						if(userint == partie2.length+2){
							ignored.put(word, word);
							texte.add(word);
							nbMot++;
							output.write(word + " ");
							output.flush();
						}
						if(userint == partie2.length+3){
							dico.addWord(new WordDictionnary(word));
							ignored.put(word, word);
							texte.add(word);
							nbMot++;
							output.write(word+ " ");
							output.flush();
						}
					}
				}
			}

			if(dico.getWordNb() != nbdico){
				dico.write();
			}
			scanner.close();
			output.close();
			fw.close();
			user.close();
		}
		catch(IOException ioe){
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
		












	}
}
