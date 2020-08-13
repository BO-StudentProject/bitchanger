/*
 * Copyright (c) 2020 - Tim Muehle und Moritz Wolter
 * 
 * Entwicklungsprojekt im Auftrag von Professorin K. Brabender und Herrn A. Koch
 * Entwickelt für das AID-Labor der Hochschule Bochum
 * 
 */

package bitchanger.gui.views;

import bitchanger.gui.controller.Controller;
import bitchanger.gui.controller.ConverterController;
import bitchanger.gui.controls.BaseSpinner;
import javafx.scene.control.Spinner;

/**	<!-- $LANGUAGE=DE -->
 * View, die die Scene für die Umwandlung von Zahlensystemen enthält.
 * <p><b>
 * Für diese View-Klasse wird der Controller {@link ConverterController} registriert.
 * </b></p>
 * 
 * @author Tim Mühle
 * 
 * @since Bitchanger 0.1.0
 * @version 0.1.4
 * 
 * @see ConverterController
 */
public class ConverterView extends AlphaNumGridView {
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	public Constants   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die hexadezimale Darstellung in der Map {@code tfMap} gespeichert wird */
	public static final String TF_HEX_KEY = "hexTF";
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die dezimale Darstellung in der Map {@code tfMap} gespeichert wird */
	public static final String TF_DEC_KEY = "decTF";

	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die oktale Darstellung in der Map {@code tfMap} gespeichert wird */
	public static final String TF_OCT_KEY = "octTF";

	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die binäre Darstellung in der Map {@code tfMap} gespeichert wird */
	public static final String TF_BIN_KEY = "binTF";

	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die Darstellung zu einer wählbaren Basis in der Map {@code tfMap} gespeichert wird */
	public static final String TF_ANY_KEY = "anyTF";

	/** <!-- $LANGUAGE=DE -->	Array, das die Schlüsselwörter definiert, mit denen die Textfelder in der Map {@code tfMap} gespeichert werden */
	public static final String[] TF_KEYS = {TF_HEX_KEY , TF_DEC_KEY, TF_OCT_KEY, TF_BIN_KEY, TF_ANY_KEY};
	
	/** <!-- $LANGUAGE=DE -->	Array, das die Beschriftungen für die Labels vor den Textfeldern definiert */
	private static final String[] LABEL_TEXTS = {"HEX", "DEC", "OCT", "BIN"};
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Class initialization																										 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	static {
		// Controller Klasse zuordnen
		Controller.register(ConverterView.class, ConverterController.class);
	}
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Instances		   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Fields			   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die hexadezimale Darstellung in der Map {@code tfMap} gespeichert wird */
	public final String tfHexKey = TF_HEX_KEY;
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die dezimale Darstellung in der Map {@code tfMap} gespeichert wird */
	public final String tfDecKey = TF_DEC_KEY;
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die oktale Darstellung in der Map {@code tfMap} gespeichert wird */
	public final String tfOctKey = TF_OCT_KEY;
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die binäre Darstellung in der Map {@code tfMap} gespeichert wird */
	public final String tfBinKey = TF_BIN_KEY;
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld für die Darstellung zu einer wählbaren Basis in der Map {@code tfMap} gespeichert wird */
	public final String tfAnyKey = TF_ANY_KEY;
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit dem der Spinner für die beliebige Basis in der Map {@code nodeMap} gespeichert wird */
	public final String baseSpinnerKey;
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Constructors	   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue ConverterView mit vollständigem Scenegraphen und initialisiert die Funktionen
	 * der Bedienelemente mit einem {@link ConverterController}.
	 */
	public ConverterView() {
		super(0, 0, 0, 1, 6, 1, LABEL_TEXTS, TF_KEYS);
		
		this.baseSpinnerKey = "baseSpinner";
		
		buildScenegraph();
	}
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Methods   																													 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** {@inheritDoc} */
	@Override
	protected void createScenegraph() {
		super.createScenegraph();
		createAnyBase();
	}
	
	/** <!-- $LANGUAGE=DE -->
	 * Fügt einen {@link BaseSpinner} zur Auswahl einer beliebigen (gültigen) Basis zur GridPane
	 * {@link AlphaNumGridView#center} hinzu und speichert diesen in der Map {@code nodeMap}.
	 */
	private void createAnyBase() {
		// Spinner für die beliebige Basis
		Spinner<Integer> baseSpinner = new BaseSpinner();
		getNodeMap().put(baseSpinnerKey, baseSpinner);
		center.add(baseSpinner, 0, LABEL_TEXTS.length);
	}

}
