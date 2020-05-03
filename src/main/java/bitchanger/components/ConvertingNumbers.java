/*
 * Copyright (c)
 * 
 * Ersteller: Tim Mühle & Moritz Wolter
 * 
 */

package bitchanger.components;

/**
 * This class contains methods to convert numbers into different numeral systems.
 * 
 * @author Tim & Moritz
 *
 */

public class ConvertingNumbers {
	
	
	
	/**
	 * wandelt die übergebene Zahl zur spezifischen Basis in der String-Darstellung 
	 * in eine Zahl zur Basis 10 und gibt diese als {@code double} zurück
	 * @param base	die spezifische Basis des übergebenen wertes
	 * @param value	der Zahlenwert, der umgewandelt werden soll übergeben in der String-Darstellung
	 * @return	Darstellung des Wertes im Zehnersystem als {@code double} Zahl
	 * @throws 	NullPointerException	wenn der Parameter {@code wert} null ist.
	 * @throws 	NumberFormatException	wenn der Parameter {@code wert} keinen umwandelbaren ganzzahligen Anteil hat.
	 * @throws 	UnsupportedOperationException	wenn das erste Zeichen des Parameters {@code zahl} ein '-' ist, 
	 * 											da negative Zahlen hier nicht erlaubt sind
	 */
	public static double basisToDez(int base, String value) throws NullPointerException, NumberFormatException, UnsupportedOperationException {
		// Übergebene Zahl in ganzen Anteil und Nachkommastellen trennen
		value = value.toUpperCase();
		String ganzerWert = Zahl.separiereGanzenAnteil(value);
		String nachKomma = Zahl.separiereNachkomma(value);
		
		// Strings die ganzen und Nachkommateil zu der übergebenen Basis 
		// repräsentieren in double Zahlen zur Basis 10 umwandeln
		double ganzDez = basisToDezGanz(base, ganzerWert);
		double nachDez = basisToDezNachKomma(base, nachKomma);
		
		// Vor- und Nachkommateil addieren und Ergebnis zurückgeben
		return ganzDez + nachDez;
	}
	
	/**
	 * wandelt die übergebene Zahl zur spezifischen Basis in der String-Darstellung 
	 * in eine Zahl zur Basis 10 und gibt diese als in der String-Darstellung zurück
	 * @param base	die spezifische Basis des übergebenen wertes
	 * @param value	der Zahlenwert, der umgewandelt werden soll, übergeben in der String-Darstellung
	 * @return	Darstellung des Wertes im Zehnersystem als String-Darstellung
	 * @throws 	NullPointerException	wenn der Parameter {@code wert} null ist.
	 * @throws 	NumberFormatException	wenn der Parameter {@code wert} keinen umwandelbaren ganzzahligen Anteil hat.
	 * @throws 	UnsupportedOperationException	wenn das erste Zeichen des Parameters {@code wert} ein '-' ist, 
	 * 											da negative Zahlen hier nicht erlaubt sind
	 */
	public static String basisToDezString(int base, String value) throws NullPointerException, NumberFormatException, UnsupportedOperationException {
		return basisToDezString(base, value, Zahl.SEP_ENG);
	}
	
	/**
	 * wandelt die übergebene Zahl zur spezifischen Basis in der String-Darstellung 
	 * in eine Zahl zur Basis 10 und gibt diese als in der String-Darstellung zurück
	 * @param base	die spezifische Basis des übergebenen wertes
	 * @param value	der Zahlenwert, der umgewandelt werden soll, übergeben in der String-Darstellung
	 * @param separator der spezifische Separator, mit dem der ganze und der Nachkommateil getrennt werden
	 * @return	Darstellung des Wertes im Zehnersystem als String-Darstellung
	 * @throws 	NullPointerException	wenn der Parameter {@code wert} null ist.
	 * @throws 	NumberFormatException	wenn der Parameter {@code wert} keinen umwandelbaren ganzzahligen Anteil hat.
	 * @throws 	UnsupportedOperationException	wenn das erste Zeichen des Parameters {@code wert} ein '-' ist, 
	 * 											da negative Zahlen hier nicht erlaubt sind
	 */
	public static String basisToDezString(int base, String value, String separator) throws NullPointerException, NumberFormatException, UnsupportedOperationException {	
		// Übergebene Zahl in ganzen Anteil und Nachkommastellen trennen
		value = value.toUpperCase();
		String ganzerWert = Zahl.separiereGanzenAnteil(base,value);
		String nachKomma = Zahl.separiereNachkomma(base,value);
		
		// Strings die ganzen und Nachkommateil zu der übergebenen Basis 
		// repräsentieren in double Zahlen zur Basis 10 umwandeln
		double ganzDez = basisToDezGanz(base, ganzerWert);
		double nachDez = basisToDezNachKomma(base, nachKomma);
		
		// Überprüfen ob es Nachkommastellen gibt
		if(nachDez != 0.0) {
			// Ja -> Rückgabe mit Nachkommateil
			return String.valueOf((long)ganzDez) + separator + String.valueOf(nachDez).substring(2);
		} else {
			// Nein -> Rückgabe des ganzen Anteils
			return String.valueOf((long)ganzDez);
		}
		
	}


	/**
	 * wandelt den übergebenen Wert vom Zehnersystem in einen Wert zur gewünschten Basis um
	 * @param base	neue Basis, in die umgewandelt werden soll
	 * @param dezValue der Wert, der umgewandelt werden soll in der String Darstellung
	 * @return umgewandelte Zahl zur neuen Basis in der String-Darstellung
	 * @throws 	NullPointerException	wenn der Parameter {@code dezWert} null ist.
	 * @throws 	NumberFormatException	wenn der Parameter {@code C} keinen umwandelbaren ganzzahligen Anteil hat.
	 * @throws 	UnsupportedOperationException	wenn das erste Zeichen des Parameters {@code dezWert} ein '-' ist, 
	 * 											da negative Zahlen hier nicht erlaubt sind
	 */
	public static String dezToBasis(int base, String dezValue) throws NullPointerException, NumberFormatException, UnsupportedOperationException {
		return dezToBasis(base, dezValue, Zahl.SEP_ENG);
	}
	
	/**
	 * wandelt den übergebenen Wert vom Zehnersystem in einen Wert zur gewünschten Basis um
	 * @param base	neue Basis, in die umgewandelt werden soll
	 * @param dezValue der Wert, der umgewandelt werden soll in der String Darstellung
	 * @param separator der spezifische Separator, mit dem der ganze und der Nachkommateil getrennt werden
	 * @return umgewandelte Zahl zur neuen Basis in der String-Darstellung
	 * @throws 	NullPointerException	wenn der Parameter {@code dezWert} null ist.
	 * @throws 	NumberFormatException	wenn der Parameter {@code C} keinen umwandelbaren ganzzahligen Anteil hat.
	 * @throws 	UnsupportedOperationException	wenn das erste Zeichen des Parameters {@code dezWert} ein '-' ist, 
	 * 											da negative Zahlen hier nicht erlaubt sind
	 */
	public static String dezToBasis(int base, String dezValue, String separator) throws NullPointerException, NumberFormatException, UnsupportedOperationException {
		// ganzen Anteil (Basis 10) separieren und in long umwandeln
		long ganzerWert = Long.parseLong(Zahl.separiereGanzenAnteil(dezValue));
		
		// Nachkommateil (Basis 10) separieren und in double umwandeln
		double nachKomma = Double.parseDouble("0."+Zahl.separiereNachkomma(dezValue));
		
		// in Zahl zur übergebenen Basis umwandeln und als String zurückgeben
		return dezToBasis(base, ganzerWert, nachKomma, separator);
	}


	/**
	 * wandelt den übergebenen Wert vom Zehnersystem in einen Wert zur gewünschten Basis um
	 * @param base neue Basis, in die umgewandelt werden soll
	 * @param integer ganzer Anteil im Zehnersystem der Zahl, die umgewandelt werden soll
	 * @param decimalPlace Nachkommateil im Zehnersystem der Zahl, die umgewandelt werden soll
	 * @return umgewandelte Zahl zur neuen Basis in der String-Darstellung
	 * @throws UnsupportedOperationException	wenn einer der Parameters {@code ganzerAnteil} oder {@code nachKomma} 
	 * 											kleiner als 0 sind, da negative Zahlen hier nicht erlaubt sind
	 */
	public static String dezToBasis(int base, long integer, double decimalPlace) throws UnsupportedOperationException{
		return dezToBasis(base, integer, decimalPlace, Zahl.SEP_ENG);
	}
	
	/**
	 * wandelt den übergebenen Wert vom Zehnersystem in einen Wert zur gewünschten Basis um
	 * @param base neue Basis, in die umgewandelt werden soll
	 * @param integer ganzer Anteil im Zehnersystem der Zahl, die umgewandelt werden soll
	 * @param decimalPlace Nachkommateil im Zehnersystem der Zahl, die umgewandelt werden soll
	 * @param separator der spezifische Separator, mit dem der ganze und der Nachkommateil getrennt werden
	 * @return umgewandelte Zahl zur neuen Basis in der String-Darstellung
	 * @throws UnsupportedOperationException	wenn einer der Parameters {@code ganzerAnteil} oder {@code nachKomma} 
	 * 											kleiner als 0 sind, da negative Zahlen hier nicht erlaubt sind
	 */
	public static String dezToBasis(int base, long integer, double decimalPlace, String separator) throws UnsupportedOperationException{
		String neueBasis = "";
		
		// überprüfen ob alle Zahlen positiv sind
		// negative Zahlen werden derzeit nicht unterstützt!
		if(integer < 0 || decimalPlace < 0) {
			throw new UnsupportedOperationException("Negative values are not supported");
		}
		
		// Ganzen Anteil umrechnen
		if(integer == 0L){
			neueBasis = "0";
		} else {
			neueBasis = wandelVonDezimalGanzIt(base, integer);
		}
		
		// Wenn vorhanden Nachkommateil umwandeln
		if(decimalPlace != 0){
			// Nachkommaanteil umwandeln und hinzufuegen
			neueBasis += wandelVonDezimalNachkommaIt(base, decimalPlace, 50, separator);
		}
		
		// umgewandelte Zahl in der neuen Basis als String zurückgeben
		return neueBasis;
	}
	
	private static double basisToDezNachKomma(int base, String decimalPlace) {
		// umwandeln von beliebiger Basis zum Zehnersystem mit dem Horner-Schema
		// dafür muss die Reihenfolge der Ziffern umgekehrt und eine 0 angehangen werden
		StringBuffer sb = new StringBuffer(decimalPlace);
		sb.reverse();
		sb.append(0);
		
		double summe = 0;
		
		for(int i = 0; i < sb.length(); i++) {
			summe *= 1.0/base;
			summe += berechneZiffer(sb.charAt(i));
		}
		
		return summe;
	}
	
	private static char berechneZiffer(int stelle){
		// gibt die Ziffer zur Stellenwertigkeit der übergebenen Stelle zurück
		// Die Ziffern 0 bis 9 entsprechen der Wertigkeit 0 bis 9
		// Die Buchstaben A bis Z entsprechen der Wertigkeit 10 bis 35
		// Ein Überlauf ist möglich, wenn Die Stellenwertigkeit größer als 35 ist und wird nicht abgefangen!
		if(stelle >= 10){
			return (char)('A' + stelle - 10);
		} else {
			return (char)('0' + stelle);
		}
	}
	
	private static int berechneZiffer(char zeichen) {
		// Berechnung der Wertigkeit, die das Zeichen repräsentiert
		// Die Ziffern 0 bis 9 entsprechen der Wertigkeit 0 bis 9
		// Die Buchstaben A bis Z entsprechen der Wertigkeit 10 bis 35
		if(zeichen >= '0' && zeichen <= '9') {
			return zeichen - '0';
		} else {
			return zeichen - 'A' + 10;
		}
	}
	
	private static String wandelVonDezimalGanzIt(int basis, long ganzerAnteil) {
		// String in dem das Ergebnis gespeichert wird initialisieren
		String ergebnisGanz = "";
		
		// Berechnen der Stellen vor dem Komma mit dem Quellenverfahren
		// Schleife durchlaufen, bis ganzerAnteil <= 0
		while(!(ganzerAnteil <= 0L)){	
			// Der Rest der ganzzahligen Division ist auch gleich dem Wert der jeweiligen Stelle
			int rest = (int) (ganzerAnteil % basis);
			
			// Zaehler fuer naechsten Durchlauf aktualisieren
			ganzerAnteil = ganzerAnteil / (long)basis;
			
			char ziffer = berechneZiffer(rest);
			
			/* Das letzte Ergebnis ergibt die erste Stelle, daher muss der 
			 * vorige String hinter der aktuellen Stelle stehen */
			ergebnisGanz = ziffer + ergebnisGanz;
			
		}
		
		return ergebnisGanz;
	}
		
	private static String wandelVonDezimalNachkommaIt(int basis, double nachKomma, int anzahlStellenMax, String separator) {
		// Berechnen der Stellen nach dem Komma mit Hilfe von Multiplikation mit der Basis
		
		if(anzahlStellenMax <= 0) {
			// Kein Nachkommaanteil
			return "";
		}
		
		// String in dem das Ergebnis gespeichert wird
		String ergebnisNachkomma = String.valueOf(separator);
		
		// Hilfsvariabel zum Abbrechen nach X Nachkommastellen
		int zaehl = 0;
		
		while(!(nachKomma % 1 == 0 || zaehl >= anzahlStellenMax)){
			zaehl++;	// Hilfsvariabel zum Abbrechen nach X Nachkommastellen
			double hilf = nachKomma * basis;
			int stelle = (int) hilf;
			nachKomma = hilf % 1;
			
			char ziffer = berechneZiffer(stelle);
			
			ergebnisNachkomma = ergebnisNachkomma + ziffer;
		}
		
		return ergebnisNachkomma;
	}
	

}