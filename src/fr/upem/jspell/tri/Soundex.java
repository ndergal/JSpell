package fr.upem.jspell.tri;
/**
 * @author DERGAL Nacer & LEROUX Gwenael
 *
 */
public class Soundex {
	
	/**Renvoit le Soundex d'un String.
	 * @param str mot à tester
	 * @return String Soundex du mot
	 */
	public static String getSoundex(String str){
		StringBuilder soundex = new StringBuilder();
		str = str.toUpperCase();
		soundex.append( str.charAt(0) );
		
		int i;
		for( i = 1; i < str.length(); i++ ) {
			if( soundex.length() >= 4 )
				break;
			
			if( !isCharToDelete( str.charAt(i) ) ) {
				if( Integer.toString(getGroupeSoundex( str.charAt(i) )).charAt(0) == soundex.charAt(soundex.length()-1) )
					continue;
				soundex.append( getGroupeSoundex( str.charAt(i) ) );
			}
		}
		
		while( soundex.length() != 4 )
			soundex.append('0');
		return soundex.toString();
	}
	
	/**Renvoit si le char passé en paramètre est à supprimer selon la methode Soundex.
	 * @param c Char à tester
	 * @return boolean S'il doit etre supprimer ou non selon Soundex
	 */
	public static boolean isCharToDelete(char c) {
		switch(c) {
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'G':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'V':
			case 'X':
			case 'Y':
				return false;
			default:
				return true;
		
		}
	}
	
	
	/**Renvoit le groupe Soudex du char passé en argument.
	 * @param c Char a tester
	 * @return int Numero du groupe de Soundex du char
	 */
	public static int getGroupeSoundex(char c) {
		switch(c) {
			case 'B':
			case 'P':
				return 1;
			case 'C':
			case 'K':
			case 'Q':
				return 2;
			case 'D':
			case 'T':
				return 3;
			case 'L':
				return 4;
			case 'M':
			case 'N':
				return 5;
			case 'R':
				return 6;
			case 'G':
			case 'J':
				return 7;
			case 'X':
			case 'Z':
			case 'S':
				return 8;
			case 'F':
			case 'V':
				return 9;
			default:
				return 0;
		}
	}
}
