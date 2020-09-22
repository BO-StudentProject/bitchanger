/*
 * Copyright (c) 2020 - Tim Muehle und Moritz Wolter
 * 
 * Entwicklungsprojekt im Auftrag von Professorin K. Brabender und Herrn A. Koch
 * Entwickelt für das AID-Labor der Hochschule Bochum
 * 
 */

package bitchanger.gui.views;

import java.util.ArrayDeque;

import bitchanger.gui.controller.CalculatorController;
import bitchanger.gui.controller.ControllableApplication;
import bitchanger.gui.controller.Controller;
import bitchanger.gui.controls.AlphaNumKeys;
import bitchanger.gui.controls.BaseSpinner;
import bitchanger.gui.controls.BasicMenuBar;
import bitchanger.gui.controls.CalculatorMenuBar;
import bitchanger.gui.controls.UnfocusedButton;
import bitchanger.util.FXUtils;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**	<!-- $LANGUAGE=DE -->
 * View, die die Scene für die Berechnungen von verschiedenen Zahlensystemen enthält.
 * <p><b>
 * Für diese View-Klasse wird der Controller {@link CalculatorController} registriert.
 * </b></p>
 * 
 * @author Tim Mühle
 * 
 * @since Bitchanger 0.1.6
 * @version 0.1.6
 * 
 * @see CalculatorController
 */
//TODO JavaDoc
public class CalculatorView extends AlphaNumGridView {
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	public Constants   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE -->	Schlüsselwort, mit das Textfeld in der Map {@code tfMap} gespeichert wird */
	// TODO JavaDoc EN
	private static final String TF_KEY = "textField";
	
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	class initialization																										 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	static {
		// Controller Klasse zuordnen
		Controller.register(CalculatorView.class, CalculatorController.class);
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
	
	
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Constructors	   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

//	/** <!-- $LANGUAGE=DE -->
//	 * Erzeugt eine neue CalculatorView mit vollständigem Scenegraphen und initialisiert die Funktionen
//	 * der Bedienelemente mit einem {@link CalculatorController}.
//	 */
	// TODO JavaDoc
	public CalculatorView() {
		super(0, 0, 0, 1, 6, 1, null, TF_KEY);
		
		buildScenegraph();
	}
	
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Getter and Setter																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	
	public ArrayDeque<Node> getLogicNodes(){
		ArrayDeque<Node> list = new ArrayDeque<>();
		
		list.add(this.getButtonMap().get(andBtnKey()));
		list.add(this.getButtonMap().get(orBtnKey()));
		list.add(this.getButtonMap().get(notBtnKey()));
		list.add(this.getNodeMap().get(bitLengthKey()));
		list.add(this.getButtonMap().get(nandBtnKey()));
		list.add(this.getButtonMap().get(norBtnKey()));
		list.add(this.getButtonMap().get(xorBtnKey()));
		list.add(this.getButtonMap().get(shiftLeftBtnKey()));
		list.add(this.getButtonMap().get(shiftRightBtnKey()));
		
		return list;
	}
	
	
	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem das Textfeld in der Map {@code tfMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem das Textfeld in der Map {@code tfMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String tfKey() {
		return TF_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button zum Dividieren in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Dividieren in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String divideBtnKey() {
		return "divide-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button zum Multiplizieren in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Multiplizieren in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String multiplyBtnKey() {
		return "multiply-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button zum Subtrahieren in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Subtrahieren in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String subtractBtnKey() {
		return "sub-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button zum Addieren in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Addieren in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String addBtnKey() {
		return "add-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Modulo-Button in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Modulo-Button in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String moduloBtnKey() {
		return "mod-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Gleichheits-Button in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Gleichheits-Button in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String equalsBtnKey() {
		return "equals-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für das Hexadezimalsystem in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für das Hexadezimalsystem in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String hexBtnKey() {
		return "hex-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für das Dezimalsystem in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für das Dezimalsystem in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String decBtnKey() {
		return "dec-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für das Oktalsystem in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für das Oktalsystem in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String octBtnKey() {
		return "oct-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für das Binärsystem in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für das Binärsystem in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String binBtnKey() {
		return "bin-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der BaseSpinner für das beliebige Zahlensystem in der Map {@code nodeMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der BaseSpinner für das beliebige Zahlensystem in der Map {@code nodeMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String baseSpinnerKey() {
		return "base-spinner";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische UND-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische UND-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String andBtnKey() {
		return "logic-and-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische ODER-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische ODER-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String orBtnKey() {
		return "logic-or-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische NICHT-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische NICHT-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String notBtnKey() {
		return "logic-not-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische NAND-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische NAND-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String nandBtnKey() {
		return "logic-nand-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische NOR-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische NOR-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String norBtnKey() {
		return "logic-nor-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für die logische Exklusiv-Oder-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für die logische Exklusiv-Oder-Verknüpfung in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String xorBtnKey() {
		return "logic-xor-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für Rechtsshift in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für Rechtsshift in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String shiftLeftBtnKey() {
		return "shift-left-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem der Button für Linksshift in der Map {@code btnMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem der Button für Linksshift in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String shiftRightBtnKey() {
		return "shift-right-btn";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem das Label zur Anzeige der Basis in der Map {@code nodeMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem das Label zur Anzeige der Basis in der Map {@code nodeMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String baseLabelKey() {
		return "base-label";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem das Ergebnis-Label in der Map {@code nodeMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem das Ergebnis-Label in der Map {@code nodeMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String resultLabelKey() {
		return "result-label";
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort zurück, mit dem die ComboBox für die Anzahl der Bits in der Map {@code nodeMap} gespeichert wird
	 * 
	 * @return	Schlüsselwort, mit dem die ComboBox für die Anzahl der Bits in der Map {@code nodeMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String bitLengthKey() {
		return "bit-length-combo-box";
	}
	
	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Methods   																													 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	
	/** {@inheritDoc} */
	@Override
	public BasicMenuBar generateMenuBar(ControllableApplication controllableApp) {
		try {
			return new CalculatorMenuBar(controllableApp);
		} catch (NullPointerException e) {
			return generateMenuBar();
		}
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** {@inheritDoc} */
	@Override
	public BasicMenuBar generateMenuBar() {
		return new CalculatorMenuBar();
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** {@inheritDoc} */
	@Override
	protected void createScenegraph() {
		super.createScenegraph();
		
		GridPane.setColumnSpan(this.getTextFieldMap().get(TF_KEY), GridPane.getColumnSpan(this.getTextFieldMap().get(TF_KEY)) + 2);
		
		createArithmeticOperators();
		createBaseButtons();
		createBitOperators();
		createLabels();
		
		addColumnConstraint(6, ConstraintType.BUTTON_COLUMN);
		addColumnConstraint(7, ConstraintType.BUTTON_COLUMN);
		addRowConstraint(firstTFRow + 1, ConstraintType.TEXT_FIELD_ROW);
		addRowConstraint(firstKeyBtnRow - 1, ConstraintType.BUTTON_ROW);
	}


	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	private Methods   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	
	private void createArithmeticOperators() {
		createArithmeticButtons();
		formatArithmeticButtons();
		
		String[] arithmeticBtnKeys = {backspaceBtnKey(), divideBtnKey(), clearBtnKey(), multiplyBtnKey(), moduloBtnKey(),
				subtractBtnKey(), equalsBtnKey(), addBtnKey()};
		
		ArrayDeque<Button> arithmeticButtons = new ArrayDeque<>();
		
		for (String key : arithmeticBtnKeys) {
			arithmeticButtons.add(this.getButtonMap().get(key));
		}
		
		FXUtils.setGridConstraints(this.firstKeyBtnColumn + AlphaNumKeys.COLUMN_COUNT, this.firstKeyBtnRow, 2, 0, arithmeticButtons);
		FXUtils.setMaxSizes(arithmeticButtons, Double.MAX_VALUE);
		
		// Backspace-Button und Clear-Button entfernen, um alle anderen Buttons zum Scenegraph hinzuzufügen
		arithmeticButtons.remove(this.getButtonMap().get(backspaceBtnKey()));
		arithmeticButtons.remove(this.getButtonMap().get(clearBtnKey()));
		
		center.getChildren().addAll(arithmeticButtons);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	private void createArithmeticButtons() {
		UnfocusedButton divBtn = new UnfocusedButton("/");
		UnfocusedButton multBtn = new UnfocusedButton("*");
		UnfocusedButton subBtn = new UnfocusedButton("-");
		UnfocusedButton addBtn = new UnfocusedButton("+");
		UnfocusedButton modBtn = new UnfocusedButton("%");
		UnfocusedButton equalsBtn = new UnfocusedButton("=");
		
		this.getButtonMap().put(divideBtnKey(), divBtn);
		this.getButtonMap().put(multiplyBtnKey(), multBtn);
		this.getButtonMap().put(subtractBtnKey(), subBtn);
		this.getButtonMap().put(addBtnKey(), addBtn);
		this.getButtonMap().put(moduloBtnKey(), modBtn);
		this.getButtonMap().put(equalsBtnKey(), equalsBtn);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	private void formatArithmeticButtons() {
		GridPane.setRowSpan(this.getButtonMap().get(equalsBtnKey()), 2);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	private void createBaseButtons() {
		ArrayDeque<Node> baseNodes = new ArrayDeque<>();
		
		String[] texts = {"HEX", "DEC", "OCT", "BIN"};
		String[] keys = {hexBtnKey(), decBtnKey(), octBtnKey(), binBtnKey()};
		
		for (int i = 0; i < keys.length; i++) {
			UnfocusedButton b = new UnfocusedButton(texts[i]);
			this.getButtonMap().put(keys[i], b);
			baseNodes.add(b);
			b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}
		
		BaseSpinner anyBase = new BaseSpinner();
		anyBase.setMaxHeight(Double.MAX_VALUE);
		this.getNodeMap().put(baseSpinnerKey(), anyBase);
		baseNodes.add(anyBase);
		
		FXUtils.setGridConstraints(0, firstKeyBtnRow, 1, 0, baseNodes);
		center.getChildren().addAll(baseNodes);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	private void createBitOperators() {
		ComboBox<String> bitLength = new ComboBox<>();
		bitLength.getItems().addAll("8-Bit", "16-Bit", "32-Bit", "64-Bit");
		bitLength.getSelectionModel().select(0);
		GridPane.setColumnSpan(bitLength, 2);
		this.getNodeMap().put(bitLengthKey(), bitLength);
		
		String[] texts = {"AND", "OR", "NOT", "NAND", "NOR", "XOR", "<<", ">>"};
		String[] keys = {andBtnKey(), orBtnKey(), notBtnKey(), nandBtnKey(), norBtnKey(), xorBtnKey(), shiftLeftBtnKey(), shiftRightBtnKey()};
		
		for (int i = 0; i < keys.length; i++) {
			UnfocusedButton b = new UnfocusedButton(texts[i]);
			this.getButtonMap().put(keys[i], b);
		}
		
		ArrayDeque<Node> logicNodes = getLogicNodes();
		
		FXUtils.setGridConstraints(1, firstKeyBtnRow - 1, AlphaNumKeys.COLUMN_COUNT, 0, logicNodes);
		FXUtils.setMaxSizes(logicNodes, Double.MAX_VALUE);
		center.getChildren().addAll(logicNodes);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	private void createLabels() {
		Label result = new Label();
		Label baseLabel = new Label();
		
		this.getNodeMap().put(resultLabelKey(), result);
		this.getNodeMap().put(baseLabelKey(), baseLabel);
		
		GridPane.setColumnSpan(result, GridPane.getColumnSpan(this.getTextFieldMap().get(TF_KEY)));
		GridPane.setFillWidth(result, false);
		GridPane.setHalignment(result, HPos.RIGHT);
		
		result.setId("result-label");
		baseLabel.setId("base-label");
		
		ArrayDeque<Label> labels = new ArrayDeque<>();
		labels.add(result);
		labels.add(baseLabel);
		
		FXUtils.setGridConstraints(tfColumn, firstTFRow + 1, GridPane.getColumnSpan(result) + 1, 0, labels);
		FXUtils.setMaxSizes(labels, Double.MAX_VALUE);
		
		center.getChildren().addAll(labels);
	}
	
}












