*********************************************************************
*****			JSpell - version 1.0			*****
*********************************************************************

Dernière révision : Jeudi 9 Juin

Utilisation :
	- java fr.upem.jspell.DictionaryCreator directory
		- Affiche un dictionnaire trié créé à partir du fichier d'entrée contenant un mot et son nombre d'occurrences par ligne
		- directory = chemin et nom du fichier à utiliser

	- java fr.upem.jspell.SpellAnnotator language1:dictionary1 language2:dictionary2 ...
		- Affiche les 5 meilleures suggestions de corrections pour les mots mals orthographiés écrits sur l'entrée standard
		- language = langue du dictionnaire
		- dictionary = fichier du dictionnaire à utiliser

	- java fr.upem.jspell.SpellSelector -d customizedDict.txt -in annotatedText.txt -out correctedText.txt
		- Propose à l'utilisateur de corriger les mots mals orthographiés avec les suggestions du fichier annotedText.txt
		- -d = précise que l'argument suivant est le nom du dictionnaire
		- customizedDict.txt = nom du dictionnaire
		- -in = précise que l'argument suivant est le nom du fichier annoté
		- annotatedText.txt = nom du fichier annoté
		- -d = précise que l'argument suivant est le nom du fichier corrigé
		- correctedText.txt = nom du fichier corrigé

Fichiers du programme : 
	- Dictionary.java
	- DictionaryCreator.java
	- Hamming.java
	- Levenshtein.java
	- Soundex.java
	- SpellAnnotator.java
	- SpellSelector.java
	- Word.java
	- WordDictionary.java

Développé par :
	- Leroux Gwenaël
	- Nacer Dergal
