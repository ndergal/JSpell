package fr.upem.jspell.tri;
/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class Levenshtein {
	
	/**Renvoit la plus petite valeur parmis trois passés en argument.
	 * @param i1 La premiere valeur a tester
	 * @param i2 La deuxieme valeur a tester
	 * @param i3 La troisieme valeur a tester
	 * @return int La valeur la plus petite parmi les trois
	 */
	private static int minTrois(int i1, int i2, int i3) {
		return i1 < i2 ? i1 < i3 ? i1 : i3 : i2 < i3 ? i2 : i3; 
	}
	
	/**Renvoit la distance de Levenshtein par rapport à deux String.
	 * @param w1 Le premier mot a tester
	 * @param w2 Le Deuxieme mot a tester
	 * @return int La distance de Levenshtein entre les deux mots
	 */
	public static int distance(String w1, String w2) {
		int n1 = w1.length();
		int n2 = w2.length();
		Integer[][] tab = new Integer[n1+1][n2+1];
		int i, j, c;
		
		for( i = 0; i <= n1; i++ )
			tab[i][0] = i;
		for( j = 0; j <= n2; j++ )
			tab[0][j] = j;
		for( i = 1; i <= n1; i++ ) {
			for( j = 1; j <= n2; j++ ) {
				c = ( w1.charAt(i-1) == w2.charAt(j-1) ) ? 0 : 1;
				tab[i][j] = minTrois(tab[i-1][j]+1, tab[i][j-1]+1, tab[i-1][j-1]+c);
			}
		}           

		return tab[n1][n2];
	}
}
