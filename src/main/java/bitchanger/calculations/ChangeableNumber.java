/*
 * Copyright (c)
 * 
 * Ersteller: Tim Muehle und Moritz Wolter
 * 
 * Entwicklungsprojekt im Auftrag von Professorin K. Brabender und Herrn A. Koch
 * Entwickelt für das AID-Labor der Hochschule Bochum
 * 
 */

package bitchanger.calculations;

/**	<!-- $LANGUAGE=DE -->
 * Schnittstelle, die Methoden definiert, um eine Zahl aus einem beliebigen Zahlensystem
 * in beliebige andere Zahlensysteme umzuwandeln
 * 
 * @author Tim Mühle
 * 
 * @since Bitchanger 0.1.0
 * @version 0.1.4
 *
 */

/*	<!-- $LANGUAGE=EN -->
 * Interface that defines methods to convert numbers from any numeral system into another numeral system
 * 
 * @author Tim Muehle
 * 
 * @since Bitchanger 0.1.0
 * @version 0.1.4
 *
 */
public interface ChangeableNumber {

// Methoden zum setzen eines Wertes ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den Wert dieser {@code ChangeableNumber} auf den Wert eines hexadezimalen Strings
	 * 
	 * @param hexValue	Neuer Wert, den diese Klasse repräsentiert, als hexadezimaler String
	 * 
	 * @throws NullPointerException			wenn der Parameter {@code hexValue} {@code null} ist
	 * @throws NumberFormatException		wenn der Parameter {@code hexValue} keine Zahl zur Basis 16 ist
	 * @throws IllegalArgumentException		wenn {@code hexValue} ein leerer String ist
	 *
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Sets the value of this {@code ChangeableNumber} to the value of a hexadecimal string
	 * 
	 * @param hexValue	New Value that is represented as a hexadecimal string by this class
	 * 
	 * @throws NullPointerException			if the parameter {@code hexValue} is {@code null}
	 * @throws NumberFormatException		if the parameter {@code hexValue} is not a number of the hexadecimal system
	 * @throws IllegalArgumentException		if {@code hexValue} is an empty string
	 *
	 */
	public void setHex(String hexValue) throws NullPointerException, NumberFormatException, IllegalArgumentException;
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den Wert dieser {@code ChangeableNumber} auf den Wert eines dezimalen Strings
	 * 
	 * @param decValue	Neuer Wert, den diese Klasse repräsentiert, als dezimaler String
	 * 
	 * @throws NullPointerException			wenn der Parameter {@code decValue} {@code null} ist
	 * @throws NumberFormatException		wenn der Parameter {@code decValue} keine Zahl zur Basis 10 ist
	 * @throws IllegalArgumentException		wenn {@code decValue} ein leerer String ist
	 *
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Sets the value of this {@code ChangeableNumber} to the value of a decimal string
	 * 
	 * @param decValue	New value that is represented as a decimal string by this class
	 * 
	 * @throws NullPointerException			if the parameter {@code decValue} is {@code null}
	 * @throws NumberFormatException		if the parameter {@code decValue} is not a number of the decimal system
	 * @throws IllegalArgumentException		if {@code decValue} is an empty string
	 *
	 */
	public void setDec(String decValue) throws NullPointerException, NumberFormatException, IllegalArgumentException;
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den Wert dieser {@code ChangeableNumber} auf den Wert eines oktalen Strings
	 * 
	 * @param octValue	Neuer Wert, den diese Klasse repräsentiert, als oktaler String
	 * 
	 * @throws NullPointerException			wenn der Parameter {@code octValue} {@code null} ist
	 * @throws NumberFormatException		wenn der Parameter {@code octValue} keine Zahl zur Basis 8 ist
	 * @throws IllegalArgumentException		wenn {@code octValue} ein leerer String ist
	 *
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Sets the value of this {@code ChangeableNumber} to the value of an octal string
	 * 
	 * @param octValue	New value that is represented as an octal string by this class
	 * 
	 * @throws NullPointerException			if the parameter {@code octValue} is {@code null}
	 * @throws NumberFormatException		if the parameter {@code octValue} is not a number of the octal system
	 * @throws IllegalArgumentException		if {@code octValue} is an empty string
	 *
	 */
	public void setOct(String octValue) throws NullPointerException, NumberFormatException, IllegalArgumentException;
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den Wert dieser {@code ChangeableNumber} auf den Wert eines binären Strings
	 * 
	 * @param binValue	Neuer Wert, den diese Klasse repräsentiert, als binärer String
	 * 
	 * @throws NullPointerException			wenn der Parameter {@code binValue} {@code null} ist
	 * @throws NumberFormatException		wenn der Parameter {@code binValue} keine Zahl zur Basis 2 ist
	 * @throws IllegalArgumentException		wenn {@code binValue} ein leerer String ist
	 *
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Sets the value of this {@code ChangeableNumber} to the value of a binary string
	 * 
	 * @param binValue	New value that is represented as a binary string by this class
	 * 
	 * @throws NullPointerException			if the parameter {@code binValue} is {@code null}
	 * @throws NumberFormatException		if the parameter {@code binValue} is not a number of the binary system
	 * @throws IllegalArgumentException		if {@code binValue} is an empty string
	 *
	 */
	public void setBin(String binValue) throws NullPointerException, NumberFormatException, IllegalArgumentException;
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den Wert dieser {@code ChangeableNumber} auf einen Wert in einem beliebigen Zahlensystem
	 * 
	 * @param value			Neuer Wert, den diese Klasse repräsentiert, in der String-Darstellung
	 * @param baseOfValue	Basis des Zahlensystems von {@code value}
	 * 
	 * @throws NullPointerException			wenn der Parameter {@code value} {@code null} ist
	 * @throws NumberFormatException		wenn der Parameter {@code value} keine Zahl zur Basis 2 ist
	 * @throws IllegalArgumentException		wenn {@code value} ein leerer String ist oder wenn {@code baseOfValue} den Wertebereich [2, 36] verlässt &#160; - &#160; <b>see</b> {@link ConvertingNumbers#isValueToBase(int base, String value)}
	 *
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Sets the value of this {@code ChangeableNumber} to a value of any numeral system
	 * 
	 * @param value			New value that is represented as string by this class
	 * @param baseOfValue	Base of the numeral system of {@code value}
	 * 
	 * @throws NullPointerException			if the parameter {@code value} is {@code null}
	 * @throws NumberFormatException		if the parameter {@code value} is not a number of the binary system
	 * @throws IllegalArgumentException		if {@code value} is an empty string or if {@code baseOfValue} leaves the range of values [2, 36] &#160; - &#160; <b>see</b> {@link ConvertingNumbers#isValueToBase(int base, String value)}
	 *
	 */
	public void setValue(String value, int baseOfValue) throws NullPointerException, NumberFormatException, IllegalArgumentException;
	
	
// Umwandlung in String-Darstellungen  ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##

	/**	<!-- $LANGUAGE=DE -->
	 * Gibt die String-Darstellung dieser {@code ChangeableNumber} als hexadezimalen String zurück.
	 * <p>
	 * Der String wird nicht als hexadezimal gekennzeichnet, es wird also <b>nicht</b> das Präfix {@code 0x} vorangestellt!
	 * </p>
	 * 
	 * @return hexadezimale String-Darstellung dieser {@code ChangeableNumber}
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Returns the string representation of this {@code ChangeableNumber} as a hexadecimal string.
	 * <p>
	 * The string is not labeled as hexadecimal, so the prefix is <b>not</b> leading!
	 * </p>
	 * 
	 * @return hexadecimal string representation of this {@code ChangeableNumber}
	 */
	public String toHexString();	// hexadezimale Darstellung dieser Zahl als String
	
	/**	<!-- $LANGUAGE=DE -->
	 * Gibt die String-Darstellung dieser {@code ChangeableNumber} als dezimalen String zurück.
	 * 
	 * @return dezimale String-Darstellung dieser {@code ChangeableNumber}
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Returns the string representation of this {@code ChangeableNumber} as a decimal string.
	 * 
	 * @return decimal string representation of this {@code ChangeableNumber}
	 */
	public String toDecString();	// dezimale Darstellung dieser Zahl als String
	
	/**	<!-- $LANGUAGE=DE -->
	 * Gibt die String-Darstellung dieser {@code ChangeableNumber} als oktalen String zurück.
	 * <p>
	 * Der String wird nicht als oktal gekennzeichnet, es wird also <b>kein</b> Präfix vorangestellt!
	 * </p>
	 * 
	 * @return oktale String-Darstellung dieser {@code ChangeableNumber}
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Returns the string representation of this {@code ChangeableNumber} as an octal string.
	 * <p>
	 * The string is not labeled as octal, so the prefix is <b>not</b> leading!
	 * </p>
	 * 
	 * @return octal string representation of this {@code ChangeableNumber}
	 */
	public String toOctString();	// oktale Darstellung dieser Zahl als String
	
	/**	<!-- $LANGUAGE=DE -->
	 * Gibt die String-Darstellung dieser {@code ChangeableNumber} als binären String zurück.
	 * <p>
	 * Der String wird nicht als binär gekennzeichnet, es wird also <b>nicht</b> das Präfix {@code 0b} vorangestellt!
	 * </p>
	 * 
	 * @return binäre String-Darstellung dieser {@code ChangeableNumber}
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Returns the string representation of this {@code ChangeableNumber} as a binary string.
	 * <p>
	 * The string is not labeled as binary, so the prefix is <b>not</b> leading!
	 * </p>
	 * 
	 * @return binary string representation of this {@code ChangeableNumber}
	 */
	public String toBinString();	// binaere Darstellung dieser Zahl als String
	
	/**	<!-- $LANGUAGE=DE -->
	 * Gibt die String-Darstellung dieser {@code ChangeableNumber} zu einer beliebigen Basis zurück.
	 * <p>
	 * Der String wird <b>nicht</b> durch ein Präfix gekennzeichnet!
	 * </p>
	 * 
	 * @param base	Basis des Zahlensystems, in dem diese {@code ChangeableNumber} dargestellt werden soll
	 * 
	 * @return	String-Darstellung dieser {@code ChangeableNumber} zur übergebenen Basis
	 * 
	 * @throws IllegalArgumentException		wenn {@code base} den Wertebereich [2, 36] verlässt &#160; - &#160; <b>see</b> {@link ConvertingNumbers#isValueToBase(int, String)}
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Returns the string representation of this {@code ChangeableNumber} to any base.
	 * <p>
	 * The String is <b>not</b> labeled by a prefix!
	 * </p>
	 * 
	 * @param base	Base of the numeral system, in which this {@code ChangeableNumber} is represented
	 * 
	 * @return	String representation of this {@code ChangeableNumber} to the committed base
	 * 
	 * @throws IllegalArgumentException		if {@code base} leaves the range of values [2, 36] &#160; - &#160; <b>see</b> {@link ConvertingNumbers#isValueToBase(int, String)}
	 */
	public String toBaseString(int base) throws IllegalArgumentException;	// Darstellung dieser Zahl als String zu der uebergebenen Basis base
	
// weitere Methoden	 ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##
	
	/**	<!-- $LANGUAGE=DE -->
	 * Setzt den eingeschlossenen Wert zurück. <b> Der vorherige Wert wird gelöscht! Wird nach dieser Methode
	 * eine der toString-Methoden aufgerufen, wird ein leerer String zurückgegeben. </b>
	 * <p>
	 * Mit einer der set-Methoden kann der eingeschlossene Wert wieder neu gesetzt werden.
	 * </p>
	 */
	
	/*	<!-- $LANGUAGE=EN -->
	 * Resets the wrapped value. <b> The previous value gets deleted! If a toString method is called
	 * after this method, an empty string will be returned.</b>
	 * <p>
	 * The wrapped value can be set again by using one of the set methods.
	 * </p>
	 */
	public void reset();
		

}
