package org.waynebutterworth.bau;

/**
 * Byte Array Utilities, aka BAU
 * A collection of methods to make working with byte arrays easier
 * @author wbutterworth
 *
 */
public class BAU {
	/**
	 * Convert a string of hex characters to a byte array. Assumes that each pair of chars is a single byte. White space and ":"'s (eg, from coping data from Wireshark)
	 * are removed from the input string.
	 * TODO check that we have an even number of chars, and raise an exception if we dont.
	 * @param s the string to convert
	 * @return the byte array
	 */
	public static byte[] hexStringToByteArray(String s) {
		/* strip white space */
		s = s.replaceAll("\\W","");
		/* and ':'s which we get when pasting from Wireshark */
		s = s.replaceAll(":", "");
		
		int len = s.length();
		byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
 	 * Covert a byte array into a string of 2 digit Hex numbers representing each individual byte.
 	 * Each hex number is separated by space in the output for readability. 
 	 * @param b the array of bytes
 	 * @return a string representation of the array
 	 */
	public static String byteArrayToHexString( byte[] b ) {
		return byteArrayToHexString( b, 1 );
	}
	
	/**
	 * Convert a byte array to a hex string of 2 digit Hex numbers representing each individual byte. 
	 * Pairs of bytes are separated by spaces, eg "1A2B 3C4D 5E6F".
	 * @param b the array of bytes
 	 * @return a string representation of the array
	 */
	public static String byteArrayToHexString2( byte[] b){
		return byteArrayToHexString(b," ",1,2,true);
	}
	 
	/**
 	 * Covert a byte array into a string of 2 digit Hex numbers representing each individual byte.
 	 * Each hex number is separated by 'numSpaces' spaces in the output for readability. 
 	 * The number of spaces can be adjusted to fit in with other output. 
 	 * @param b the array of bytes
 	 * @param numSpaces The number of spaces to print between each hex number.
 	 * @return a string representation of the array
 	 */
	public static String byteArrayToHexString( byte[] b, int numSpaces ){
		return byteArrayToHexString( b, " ", numSpaces, 1, false);
	}
	
	/**
 	 * Covert a byte array into a string of 2 digit Hex numbers representing each individual byte.
 	 * Each hex number is separated by 1 'padChar' in the output for readability. 
 	 * @param b the array of bytes
 	 * @param padChar The character between each hex number.
 	 * @return a string representation of the array
 	 */
	public static String byteArrayToHexString( byte[] b, String padChar ) {
		return byteArrayToHexString( b, padChar, 1, 1, false);
	}
	
	
	
	public static String byteArrayToHexString(byte[] b, String padChar, int padSize, int groupSize, boolean upper) {
		if ( b == null ) {
			return "";
		}
		
		int msb, lsb;
		String result = "";
		
		if ( padChar == null ) padChar = " ";
		if ( groupSize == 0 ) groupSize = 1;
		
		String padding = "";
		for ( int j = 0; j < padSize; j++ ) {
			padding = padding + padChar;
		}
		
		int sizeOfCurrGrp = 0;
		for( int i = 0; i < b.length; i++) {
			msb = ( b[i] >> 4 ) & 15;
			lsb = b[i] & 15;
			
			char ch1 = Character.forDigit(msb, 16);
			char ch2 = Character.forDigit(lsb, 16);
			if ( upper ) {
				ch1 = Character.toUpperCase(ch1);
				ch2 = Character.toUpperCase(ch2);
			}
			result = result + ch1 + ch2;
			sizeOfCurrGrp++;
			
			if ( sizeOfCurrGrp == groupSize ) {
				result = result + padding;
				sizeOfCurrGrp = 0;
			}
		}
		return result;
	}
	
	/**
	 * Convert a byte array into a string of the ASCII characters it represents. Each character in the output string is spaced to 4 spaces. Non printing characters
	 * are represent by their 2 or 3 character abbreviation, eg "SPC" for space.
	 * @param b the byte array
	 * @return the string
	 */
	public static String byteArrayToString( byte[] b){
		//int msb, lsb;
		String result = "";
		
		for( int i = 0; i < b.length; i++) {
			//msb = ( b[i] >> 4 ) & 15;
			//lsb = b[i] & 15;
			
			String ch = "";
			String pad = "";
			byte temp = b[i];
			if ( temp < 33 ) {
				switch(temp) {
					case 0: ch = "NUL";
							pad = " ";
							break;
					case 1: ch = "SOH";
							pad = " ";
							break;
					case 2: ch = "STX";
							pad = " ";
							break;
					case 3: ch = "ETX";
							pad = " ";
							break;
					case 4: ch = "EOT";
							pad = " ";
							break;
					case 5: ch = "ENQ";
							pad = " ";
							break;
					case 6: ch = "ACK";
							pad = " ";
							break;
					case 7: ch = "BEL";
							pad = " ";
							break;
					case 8: ch = "BS";
							pad = "  ";
							break;
					case 9: ch = "HT";
							pad = "  ";
							break;
					case 10: ch = "LF";
							pad = "  ";
							break;
					case 11: ch = "VT";
							pad = " ";
							break;
					case 12: ch = "FF";
							pad = "  ";
							break;
					case 13: ch = "CR";
							pad = "  ";
							break;
					case 14: ch = "SO";
							pad = "  ";
							break;
					case 15: ch = "SI";
							pad = "  ";
							break;
					case 16: ch = "DLE";
							pad = " ";
							break;
					case 17: ch = "DC1";
							pad = " ";
							break;
					case 18: ch = "DC2";
							pad = " ";
							break;
					case 19: ch = "DC3";
							pad = " ";
							break;
					case 20: ch = "DC4";
							pad = " ";
							break;
					case 21: ch = "NAK";
							pad = " ";
							break;
					case 22: ch = "SYN";
							pad = " ";
							break;
					case 23: ch = "ETB";
							pad = " ";
							break;
					case 24: ch = "CAN";
							pad = " ";
							break;
					case 25: ch = "EM";
							pad = "  ";
							break;
					case 26: ch = "SUB";
							pad = " ";
							break;
					case 27: ch = "ESC";
							pad = " ";
							break;
					case 28: ch = "FS";
							pad = "  ";
							break;
					case 29: ch = "GS";
							pad = "  ";
							break;
					case 30: ch = "RS";
							pad = "  ";
							break;
					case 31: ch = "US";
							pad = "  ";
							break;
					case 32: ch = "SPC";
							pad = " ";
							break;
				}
			} else if ( temp == 127 ) {
				ch = "DEL";
				pad = " ";
			} else {
				ch = String.valueOf((char)temp);
				pad = "   ";
			}
			result = result + pad + ch;
			
		}
		return result;
	}
	
	public static byte[] XOR(byte[] key1Bytes, byte[] key2Bytes) {
		if ( key1Bytes.length != key2Bytes.length) return null;
		
		byte[] result = new byte[key1Bytes.length];
		
		for( int i = 0; i < key1Bytes.length; i++ ) {
			result[i] = (byte) (key1Bytes[i]^key2Bytes[i]);
		}
		
		return result;
	}
}
