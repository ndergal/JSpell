package fr.upem.jspell.tri;

/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class Hamming {

	/**Renvoit la distance de Hamming Gauche entre deux String.
	 * @param w1 Le premier mot à tester
	 * @param w2 Le deuxieme mot à tester
	 * @return int La distance de Hamming par la gauche entre les deux mots
	 */
	public static int getHammingGauche(String w1, String w2) {
		if( w1.length() > w2.length() ) {
			String tmp = w2;
			w2 = w1;
			w1 = tmp;
		}
		int distance = w2.length() - w1.length();
		int i;
		for( i = 0; i < w1.length(); i++ ) {
			if( w1.codePointAt(i) != w2.codePointAt(i) )
				distance++;
		}
		return distance;
	}

	/**Renvoit la distance de Hamming Droite entre deux String.
	 * @param w1 Le premier mot à tester
	 * @param w2 Le deuxieme mot à tester
	 * @return int La distance de Hamming par la droite entre les deux mots
	 */
	public static int getHammingDroite(String w1, String w2) {
		if( w1.length() > w2.length() ) {
			String tmp = w2;
			w2 = w1;
			w1 = tmp;
		}
		int distance = w2.length() - w1.length();
		int i;
		int a = w2.length()-1;
		for( i = w1.length()-1; i >= 0; i-- ) {
			if( w1.codePointAt(i) != w2.codePointAt(a) )
				distance++;
			a--;
		}
		return distance;
	}
}
