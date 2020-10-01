/*
 * Copyright (c) 2020 - Tim Muehle und Moritz Wolter
 * 
 * Entwicklungsprojekt im Auftrag von Professorin K. Brabender und Herrn A. Koch
 * Entwickelt für das AID-Labor der Hochschule Bochum
 * 
 */

package bitchanger.gui.views;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.function.BiConsumer;

import bitchanger.gui.controls.AlphaNumKeys;
import bitchanger.gui.controls.SVGIcon;
import bitchanger.gui.controls.UnfocusedButton;
import bitchanger.gui.controls.ValueField;
import bitchanger.util.ArrayUtils;
import bitchanger.util.FXUtils;
import bitchanger.util.IconFactory;
import bitchanger.util.Resources;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

/** <!-- $LANGUAGE=DE -->
 * View, die die Basis Scene für eine Tabelle aus einer Spalte mit Labels, einer Spalte mit Textfeldern 
 * und einer Tastaturmatrix aus {@link AlphaNumKeys} erstellt.
 * <p>
 * Die Position der Labels und Textfelder werden über die Attribute {@link #labelColumn}, {@link #firstLabelRow},
 * {@link #tfColumn} und {@link #firstTFRow} festgelegt. Die Anzahl der Labels und Textfelder wird über die Arrays
 * {@link #labelTexts} und {@link #tfKeys} festgelegt. Alle Labels werden in der definierten Spalte untereinander 
 * in die Zeilen der GridPane {@link #center} positioniert. Analog gilt das selbe für die Textfelder.
 * <b> Alle Textfelder sind eine Instanz von {@link ValueField}. </b>
 * </p>
 * <p>
 * Zudem wird eine funktionsfähige Tastaturmatrix aus {@link AlphaNumKeys} in der Tabelle eingefügt. Die Position
 * des ersten Buttons dieser Matrix ist durch die Attribut {@link #firstKeyBtnColumn} und {@link #firstKeyBtnRow} + 1
 * definiert. Hat das Attribut {@link #useClearAndBackBtn} zum Zeitpunkt des Aufrufes der Methode {@link #createScenegraph()}
 * den Wert {@code true} werden in der Zeile oberhalb der Tastaturmatrix die Buttons {@link #clearBtnKey} und 
 * {@link #backspaceBtnKey} positioniert. Unabhängig davon werden diese beiden Buttons in jedem Fall vollständig initialisiert 
 * und zur Map {@link #getButtonMap()} hinzugefügt und können somit auch manuell in der GridPane positioniert werden.
 * <b> Alle Buttons werden als Instanz von {@link UnfocusedButton} angelegt. </b>
 * </p>
 * <p><b>
 * Die Textfelder wachsen in vertikale Richtung mit den Spalten der GridPane, die Buttons der Tastaturmatrix
 * wachsen sowohl mit den Spalten als auch mit den Zeilen der GridPane.
 * </b></p>
 * <p><b>
 * Wenn das Attribut {@link #setTFColumnSpan} zum Zeitpunkt des Aufrufes der Methode {@link #createScenegraph()} den Wert {@code true} hat,
 * werden die Textfelder über die Anzahl der Spalten verteilt, die die Tastaturmatrix einnimmt!
 * </b></p>
 * <p>
 * Der Scenegraph dieser View muss erst von einer Subklasse mit der Methode {@link #createScenegraph()} erstellt werden,
 * bevor diese View dargestellt werden kann.
 * </p>
 * 
 * @author Tim Mühle
 * 
 * @since Bitchanger 0.1.4
 * @version 0.1.6
 *
 */
// TODO JavaDoc EN
public class AlphaNumGridView extends ViewBase<BorderPane> {

//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Fields			   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	
// public	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE --> Array, das die Schlüsselwörter definiert, mit denen die Textfelder in der Map {@code tfMap} gespeichert werden */
	private String[] tfKeys;
	
	/** <!-- $LANGUAGE=DE --> Schlüsselwort, mit dem der Löschen-Button (AC) in der Map {@code btnMap} gespeichert wird */
	private String clearBtnKey;
	
	/** <!-- $LANGUAGE=DE --> Schlüsselwort, mit dem der Backspace-Button in der Map {@code btnMap} gespeichert wird */
	private String backspaceBtnKey;
	
	/** <!-- $LANGUAGE=DE -->	Array, das die Beschriftungen für die Labels vor den Textfeldern definiert */
	private String[] labelTexts;
	
	
// protected	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	/** <!-- $LANGUAGE=DE -->
	 * {@code true}, wenn die Textfelder über die Anzahl der Spalten von der Tastatur verteilt werden sollen.
	 * Subklassen können dieses Attribut vor dem Aufruf der Methode {@link #createScenegraph()} auf {@code false}
	 * setzen, um die Textfelder nur in eine einzige Spalte zu legen.
	 */
	// TODO JavaDoc EN
	protected boolean setTFColumnSpan;
	
	/** <!-- $LANGUAGE=DE -->
	 * {@code true}, wenn die Buttons {@link #clearBtnKey} und {@link #backspaceBtnKey} in der Zeile
	 * {@link #firstKeyBtnRow} über der Tastaturmatrix in der GridPane positioniert werden sollen.
	 * Subklassen können dieses Attribut vor dem Aufruf der Methode {@link #createScenegraph()} auf {@code false}
	 * setzen, um die Buttons nicht in der GridPane zu positionieren.
	 * <p><b>
	 * Unabhängig von diesem Attribut werden die beiden Buttons vollständig initialisiert und in der Map
	 * {@link #getButtonMap()} eingefügt!
	 * </b></p>
	 */
	// TODO JavaDoc EN
	protected boolean useClearAndBackBtn;
	
	/** <!-- $LANGUAGE=DE --> Property für die maximale Höhe der Textfelder in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty tfMaxHeightProperty;

	/** <!-- $LANGUAGE=DE --> Property für die minimale Höhe der Textfelder in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty tfMinHeightProperty;

	/** <!-- $LANGUAGE=DE --> Property für die maximale Höhe der Buttons in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty btnMaxHeigthProperty;

	/** <!-- $LANGUAGE=DE --> Property für die minimale Höhe der Buttons in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty btnMinHeigthProperty;

	/** <!-- $LANGUAGE=DE --> Property für die maximale Breite der Buttons in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty btnMaxWidthProperty;

	/** <!-- $LANGUAGE=DE --> Property für die minimale Breite der Buttons in dieser View */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty btnMinWidthProperty;
	
	/** <!-- $LANGUAGE=DE --> Property für die Höhe der Zeilen zwischen den Textfeldern und Buttons */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty whiteSpaceHeigthProperty;

	/** <!-- $LANGUAGE=DE --> Property für die Breite der ersten Spalte mit den Labels enthält. Wird benötigt, um symmetrisch Weißraum auf der rechten Seite hinzuzufügen. */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty firstColumnWidthProperty;

	/** <!-- $LANGUAGE=DE --> Property für den Abstand am oberen Rand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty paddingTopProperty;

	/** <!-- $LANGUAGE=DE --> Property für den Abstand am rechten Rand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty paddingRigthProperty;

	/** <!-- $LANGUAGE=DE --> Property für den Abstand am unteren Rand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty paddingBottomProperty;

	/** <!-- $LANGUAGE=DE --> Property für den Abstand am linken Rand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty paddingLeftProperty;

	/** <!-- $LANGUAGE=DE --> Property für den Abstand der Buttons in der GridPane */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty btnSpacingProperty;

	/** <!-- $LANGUAGE=DE --> Property für den horizontalen Abstand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty hgapProperty;

	/** <!-- $LANGUAGE=DE --> Property für den vertikalen Abstand der GridPane im Center */
	// TODO JavaDoc EN
	protected final ReadOnlyDoubleProperty vgapProperty;

	/** <!-- $LANGUAGE=DE --> Konstante, die die erste Zeile der GridPane definiert, in der die Textfelder positioniert werden */
	// TODO JavaDoc EN
	protected final int firstTFRow;
	
	/** <!-- $LANGUAGE=DE --> Konstante, die die Spalte der GridPane definiert, in der die Textfelder positioniert werden */
	// TODO JavaDoc EN
	protected final int tfColumn;
	
	/** <!-- $LANGUAGE=DE --> Konstante, die die erste Zeile der GridPane definiert, in der die Labels positioniert werden */
	// TODO JavaDoc EN
	protected final int firstLabelRow;
	
	/** <!-- $LANGUAGE=DE --> Konstante, die die Spalte der GridPane definiert, in der die Labels positioniert werden */
	// TODO JavaDoc EN
	protected final int labelColumn;
	
	/** <!-- $LANGUAGE=DE --> Konstante, die die erste Zeile der GridPane definiert, in der die Tastatur-Buttons positioniert werden */
	// TODO JavaDoc EN
	protected final int firstKeyBtnRow;

	/** <!-- $LANGUAGE=DE --> Konstante, die die erste Spalte der GridPane definiert, in der die Tastatur-Buttons positioniert werden */
	// TODO JavaDoc EN
	protected final int firstKeyBtnColumn;

	/** <!-- $LANGUAGE=DE --> Tabelle im Center von {@code root}, in der alle Controls positioniert werden */
	// TODO JavaDoc EN
	protected final GridPane center;
	
	/** <!-- $LANGUAGE=DE --> Tabelle, in der alle Buttons positioniert werden */
	// TODO JavaDoc EN
	protected final GridPane buttonGrid;

	/** <!-- $LANGUAGE=DE --> Buttons, die als alpha-numerische Tastatur dienen, die für verschiedene Zahlensysteme ausgelegt ist. */
	// TODO JavaDoc EN
	protected final AlphaNumKeys alphaNum;
	
	
// private	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE --> Map, in der die angezeigten Texte oder Icons für die Buttons definiert werden. */
	// TODO JavaDoc EN
	private final HashMap<String, Object> btnTexts;
	
	/** <!-- $LANGUAGE=DE --> Property für die maximale Höhe der Buttons in dieser View */
	// TODO JavaDoc EN
	private final DoubleProperty maxHeightProperty;

	/** <!-- $LANGUAGE=DE --> Property für die minimale Höhe der Buttons in dieser View */
	// TODO JavaDoc EN
	private final DoubleProperty minHeightProperty;

	/** <!-- $LANGUAGE=DE --> Property für die maximale Breite der Buttons in dieser View */
	// TODO JavaDoc EN
	private final DoubleProperty maxWidthProperty;

	/** <!-- $LANGUAGE=DE --> Property für die minimale Breite der Buttons in dieser View */
	// TODO JavaDoc EN
	private final DoubleProperty minWidthProperty;
	
	

//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Constructors	   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue AlphaNumGridView, die keine Labels und keine Textfelder enthält.
	 * Der Scenegraph wird nicht automatisch erstellt.
	 * 
	 * @param firstKeyBtnRow	erste Zeile in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param firstKeyBtnColumn	erste Spalte in der GridPane, in der die Tastatur-Buttons positioniert werden
	 */
	// TODO JavaDoc EN
	public AlphaNumGridView(int firstKeyBtnRow, int firstKeyBtnColumn) {
		this(-1, -1, -1, -1, firstKeyBtnRow, firstKeyBtnColumn, null);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue AlphaNumGridView, die ihre Labels in der Spalte 0 ab Zeile 0 und ihre Textfelder in der Spalte 1 
	 * ab Zeile 0 positioniert. Der Scenegraph wird nicht automatisch erstellt.
	 * Die Labels in der Spalte 0 und die Textfelder in der Spalte 1 werden aus den Arrays {@code labelTexts} und 
	 * {@code tfKeys} generiert. Nach den Textfeldern wird automatisch eine Zeile frei gelassen, bevor die erste Zeile 
	 * mit den Buttons beginnt. Die Buttons beginnen in der Spalte 1.
	 * 
	 * @param labelTexts		Beschriftungen für die Labels vor den Textfeldern
	 * @param tfKeys			Schlüsselwörter, mit denen die generierten Textfelder in der Map {@code tfMap} gespeichert werden
	 */
	// TODO JavaDoc EN
	public AlphaNumGridView(String[] labelTexts, String... tfKeys) {
		this(0, 0, 0, 1, Math.max(labelTexts.length, tfKeys.length) + 1, 1, labelTexts, tfKeys);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue AlphaNumGridView, die ihre Labels, Textfelder und die Tastaturmatrix nach den übergebenen Parametern
	 * positioniert. Der Scenegraph wird nicht automatisch erstellt.
	 * Die Labels und die Textfelder werden aus den Arrays {@code labelTexts} und {@code tfKeys} generiert. 
	 * 
	 * @param firstLabelRow		erste Zeile in der GridPane, in der die Labels positioniert werden
	 * @param labelColumn		Spalte in der GridPane, in der die Labels positioniert werden
	 * @param firstTFRow		erste Zeile in der GridPane, in der die Textfelder positioniert werden
	 * @param tfColumn			Spalte in der GridPane, in der die Textfelder positioniert werden
	 * @param firstKeyBtnRow	erste Zeile in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param firstKeyBtnColumn	erste Spalte in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param labelTexts		Beschriftungen für die Labels vor den Textfeldern
	 * @param tfKeys			Schlüsselwörter, mit denen die generierten Textfelder in der Map {@code tfMap} gespeichert werden
	 */
	// TODO JavaDoc EN
	public AlphaNumGridView(int firstLabelRow, int labelColumn, int firstTFRow, int tfColumn, int firstKeyBtnRow, 
			int firstKeyBtnColumn, String[] labelTexts, String... tfKeys) {
		
		this(firstLabelRow, labelColumn, firstTFRow, tfColumn, firstKeyBtnRow, firstKeyBtnColumn, labelTexts, 
				tfKeys, 40, Double.MAX_VALUE, 45, 40, 70, 20, 6);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue AlphaNumGridView, die ihre Labels, Textfelder und die Tastaturmatrix nach den übergebenen Parametern
	 * positioniert. Der Scenegraph wird nicht automatisch erstellt.
	 * Die Labels und die Textfelder werden aus den Arrays {@code labelTexts} und {@code tfKeys} generiert.
	 * 
	 * @param firstLabelRow		erste Zeile in der GridPane, in der die Labels positioniert werden
	 * @param labelColumn		Spalte in der GridPane, in der die Labels positioniert werden
	 * @param firstTFRow		erste Zeile in der GridPane, in der die Textfelder positioniert werden
	 * @param tfColumn			Spalte in der GridPane, in der die Textfelder positioniert werden
	 * @param firstKeyBtnRow	erste Zeile in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param firstKeyBtnColumn	erste Spalte in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param labelTexts		Beschriftungen für die Labels vor den Textfeldern
	 * @param tfKeys			Schlüsselwörter, mit denen die generierten Textfelder in der Map {@code tfMap} gespeichert werden
	 * @param tfHeight			Höhe, die für alle Textfelder in den Properties {@link #tfMaxHeightProperty} und {@link #tfMinHeightProperty} eingestellt wird
	 * @param btnMaxSize		Größe, die für alle Buttons in den Properties {@link #btnMaxHeigthProperty} und {@link #btnMaxWidthProperty} eingestellt wird
	 * @param btnMinSize		Größe, die für alle Buttons in den Properties {@link #btnMinHeigthProperty} und {@link #btnMinWidthProperty} eingestellt wird
	 * @param whiteSpaceHeigth	Höhe, die für die Property {@link #whiteSpaceHeigthProperty} eingestellt wird
	 * @param firstColumnWidth	Breite, die für die Property {@link #firstColumnWidthProperty} eingestellt wird
	 * @param paddingTopRigthBottomLeft		Größe, die für die Properties {@link #paddingTopProperty}, {@link #paddingRigthProperty}, {@link #paddingBottomProperty} und {@link #paddingLeftProperty} eingestellt wird
	 * @param spacing			Größe, die für die Properties {@link #btnSpacingProperty}, {@link #hgapProperty} und {@link #vgapProperty} eingestellt wird
	 */
	// TODO JavaDoc EN
	public AlphaNumGridView(int firstLabelRow, int labelColumn, int firstTFRow, int tfColumn, int firstKeyBtnRow, 
			int firstKeyBtnColumn, String[] labelTexts, String[] tfKeys, double tfHeight, double btnMaxSize, double btnMinSize,
			double whiteSpaceHeigth, double firstColumnWidth, double paddingTopRigthBottomLeft, double spacing) {

		this(firstLabelRow, labelColumn, firstTFRow, tfColumn, firstKeyBtnRow, firstKeyBtnColumn, labelTexts, tfKeys, tfHeight, 
				tfHeight, btnMaxSize, btnMinSize, btnMaxSize, btnMinSize, whiteSpaceHeigth, firstColumnWidth, paddingTopRigthBottomLeft, 
				paddingTopRigthBottomLeft, paddingTopRigthBottomLeft, paddingTopRigthBottomLeft, spacing, spacing, spacing);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/**<!-- $LANGUAGE=DE -->
	 * Erzeugt eine neue AlphaNumGridView, die ihre Labels, Textfelder und die Tastaturmatrix nach den übergebenen Parametern
	 * positioniert. Der Scenegraph wird nicht automatisch erstellt.
	 * Die Labels und die Textfelder werden aus den Arrays {@code labelTexts} und {@code tfKeys} generiert. 
	 * 
	 * @param firstLabelRow		erste Zeile in der GridPane, in der die Labels positioniert werden
	 * @param labelColumn		Spalte in der GridPane, in der die Labels positioniert werden
	 * @param firstTFRow		erste Zeile in der GridPane, in der die Textfelder positioniert werden
	 * @param tfColumn			Spalte in der GridPane, in der die Textfelder positioniert werden
	 * @param firstKeyBtnRow	erste Zeile in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param firstKeyBtnColumn	erste Spalte in der GridPane, in der die Tastatur-Buttons positioniert werden
	 * @param labelTexts		Beschriftungen für die Labels vor den Textfeldern
	 * @param tfKeys			Schlüsselwörter, mit denen die generierten Textfelder in der Map {@code tfMap} gespeichert werden
	 * @param tfMaxHeight		Höhe, die für alle Textfelder in der Property {@link #tfMaxHeightProperty} eingestellt wird
	 * @param tfMinHeight		Höhe, die für alle Textfelder in der Property {@link #tfMinHeightProperty} eingestellt wird
	 * @param btnMaxHeigth		Höhe, die für alle Buttons in der Property {@link #btnMaxHeigthProperty} eingestellt wird
	 * @param btnMinHeigth		Höhe, die für alle Buttons in der Property {@link #btnMinHeigthProperty} eingestellt wird
	 * @param btnMaxWidth		Breite, die für alle Buttons in der Property {@link #btnMaxWidthProperty} eingestellt wird
	 * @param btnMinWidth		Breite, die für alle Buttons in der Property {@link #btnMinWidthProperty} eingestellt wird
	 * @param whiteSpaceHeigth	Höhe, die für die Property {@link #whiteSpaceHeigthProperty} eingestellt wird
	 * @param firstColumnWidth	Breite, die für die Property {@link #firstColumnWidthProperty} eingestellt wird
	 * @param paddingTop		Größe, die für die Property {@link #paddingTopProperty} eingestellt wird
	 * @param paddingRigth		Größe, die für die Property {@link #paddingRigthProperty} eingestellt wird
	 * @param paddingBottom		Größe, die für die Property {@link #paddingBottomProperty} eingestellt wird
	 * @param paddingLeft		Größe, die für die Property {@link #paddingLeftProperty} eingestellt wird
	 * @param btnSpacing		Größe, die für die Property {@link #btnSpacingProperty} eingestellt wird
	 * @param hgap				Größe, die für die Property {@link #hgapProperty} eingestellt wird
	 * @param vgap				Größe, die für die Property {@link #vgapProperty} eingestellt wird
	 */
	// TODO JavaDoc EN
	public AlphaNumGridView(int firstLabelRow, int labelColumn, int firstTFRow, int tfColumn, int firstKeyBtnRow, int firstKeyBtnColumn,
			String[] labelTexts, String[] tfKeys, double tfMaxHeight, double tfMinHeight, double btnMaxHeigth, double btnMinHeigth, 
			double btnMaxWidth, double btnMinWidth, double whiteSpaceHeigth, double firstColumnWidth, double paddingTop,
			double paddingRigth, double paddingBottom, double paddingLeft, double btnSpacing, double hgap, double vgap) {

		super(new BorderPane(), false);

		this.tfMaxHeightProperty = new SimpleDoubleProperty(tfMaxHeight);
		this.tfMinHeightProperty = new SimpleDoubleProperty(tfMinHeight);
		this.btnMaxHeigthProperty = new SimpleDoubleProperty(btnMaxHeigth);
		this.btnMinHeigthProperty = new SimpleDoubleProperty(btnMinHeigth);
		this.btnMaxWidthProperty = new SimpleDoubleProperty(btnMaxWidth);
		this.btnMinWidthProperty = new SimpleDoubleProperty(btnMinWidth);
		this.whiteSpaceHeigthProperty = new SimpleDoubleProperty(whiteSpaceHeigth);
		this.firstColumnWidthProperty = new SimpleDoubleProperty(firstColumnWidth);
		this.paddingTopProperty = new SimpleDoubleProperty(paddingTop);
		this.paddingRigthProperty = new SimpleDoubleProperty(paddingRigth);
		this.paddingBottomProperty = new SimpleDoubleProperty(paddingBottom);
		this.paddingLeftProperty = new SimpleDoubleProperty(paddingLeft);
		this.btnSpacingProperty = new SimpleDoubleProperty(btnSpacing);
		this.hgapProperty = new SimpleDoubleProperty(hgap);
		this.vgapProperty = new SimpleDoubleProperty(vgap);
		
		this.minHeightProperty = new SimpleDoubleProperty();
		this.maxHeightProperty = new SimpleDoubleProperty();
		this.minWidthProperty = new SimpleDoubleProperty();
		this.maxWidthProperty = new SimpleDoubleProperty();
		
		this.firstTFRow = (firstTFRow >= 0 && tfColumn >= 0) ? firstTFRow : -1;
		this.tfColumn = (firstTFRow >= 0 && tfColumn >= 0) ? tfColumn : -1;
		this.firstLabelRow = (firstLabelRow >= 0 && labelColumn >= 0) ? firstLabelRow : -1;
		this.labelColumn = (firstLabelRow >= 0 && labelColumn >= 0) ? labelColumn : -1;
		this.firstKeyBtnRow = (firstKeyBtnRow >= 0 && firstKeyBtnColumn >= 0) ? firstKeyBtnRow : -1;
		this.firstKeyBtnColumn = (firstKeyBtnRow >= 0 && firstKeyBtnColumn >= 0) ? firstKeyBtnColumn : -1;
		
		this.setTFColumnSpan = true;
		this.useClearAndBackBtn = true;

		boolean createTextFields = (tfKeys != null) && (tfColumn >= 0) && (firstTFRow >= 0);
		this.tfKeys = createTextFields ? tfKeys : new String[0];

		boolean createLabels = (labelTexts != null) && (labelColumn >= 0) && (firstLabelRow >= 0);
		this.labelTexts = createLabels ? labelTexts : new String[0];
		
		this.clearBtnKey = "clearBtn";
		this.backspaceBtnKey = "backspaceBtn";

		this.btnTexts = new HashMap<String, Object>();
		SVGIcon trashIcon = IconFactory.styleBindIcon(Resources.TRASH_X_ICON, Resources.TRASH_X_FILLED_ICON);
		this.btnTexts.put(clearBtnKey, trashIcon == null ? "AC" : trashIcon);
		SVGIcon backSpaceIcon = IconFactory.styleBindIcon(Resources.X_MARK_ARROW_LEFT_ICON, Resources.X_MARK_ARROW_LEFT_FILLED_ICON);
		this.btnTexts.put(backspaceBtnKey, backSpaceIcon == null ? "<--" : backSpaceIcon);

		this.center = new GridPane();
		this.buttonGrid = new GridPane();
		
		this.alphaNum = new AlphaNumKeys(1, 0, btnSpacingProperty, this.scene);
		
		observeCenter();
		observeSize();
	}
	
	

//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Getter and Setter																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	
	/** {@inheritDoc} */
	@Override
	public DoubleProperty maxHeigthProperty() {
		return this.maxHeightProperty;
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** {@inheritDoc} */
	@Override
	public DoubleProperty maxWidthProperty() {
		return this.maxWidthProperty;
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** {@inheritDoc} */
	@Override
	public DoubleProperty minHeigthProperty() {
		return this.minHeightProperty;
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	/** {@inheritDoc} */
	@Override
	public DoubleProperty minWidthProperty() {
		return this.minWidthProperty;
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Array, das die Schlüsselwörter definiert, mit denen die Textfelder in der Map {@code tfMap} gespeichert werden zurück.
	 * 
	 * @return	Array, das die Schlüsselwörter definiert, mit denen die Textfelder in der Map {@code tfMap} gespeichert werden
	 */
	// TODO JavaDoc EN
	public final String[] tfKeys() {
		return tfKeys;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Löschen-Button (AC) in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Löschen-Button (AC) in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String clearBtnKey() {
		return clearBtnKey;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Backspace-Button in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Backspace-Button in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String backspaceBtnKey() {
		return backspaceBtnKey;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Button zum Vorzeichenwechsel in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Vorzeichenwechsel in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String signBtnKey() {
		return AlphaNumKeys.SIGN_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Button, der die Null repräsentiert in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Button, der die Null repräsentiert in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String zeroBtnKey() {
		return AlphaNumKeys.ZERO_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Komma-Button in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Komma-Button in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String commaBtnKey() {
		return AlphaNumKeys.COMMA_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Button zum Umschalten des Tastaturlayouts in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Umschalten des Tastaturlayouts in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String keyboardBtnKey() {
		return AlphaNumKeys.KEYBOARD_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Button zum Weiterscrollen durch die Tastatur in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Weiterscrollen durch die Tastatur in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String nextBtnKey() {
		return AlphaNumKeys.NEXT_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Schlüsselwort, mit dem der Button zum Rückwärtsscrollen durch die Tastatur in der Map {@code btnMap} gespeichert wird zurück.
	 * 
	 * @return	Schlüsselwort, mit dem der Button zum Rückwärtsscrollen durch die Tastatur in der Map {@code btnMap} gespeichert wird
	 */
	// TODO JavaDoc EN
	public final String previousBtnKey() {
		return AlphaNumKeys.PREVIOUS_BTN_KEY;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Array, das die Schlüsselwörter für die Buchstaben-Buttons definiert zurück.
	 * 
	 * @return	Array, das die Schlüsselwörter für die Buchstaben-Buttons definiert
	 */
	// TODO JavaDoc EN
	public final String[] alphaKeys() {
		return AlphaNumKeys.ALPHA_KEYS;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Array, das die Schlüsselwörter für die Zahlen-Buttons definiert zurück.
	 * 
	 * @return	Array, das die Schlüsselwörter für die Zahlen-Buttons definiert
	 */
	// TODO JavaDoc EN
	public final String[] numKeys() {
		return AlphaNumKeys.NUM_KEYS;
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Gibt das Array, das die Beschriftungen für die Labels vor den Textfeldern definiert zurück.
	 * 
	 * @return	Array, das die Beschriftungen für die Labels vor den Textfeldern definiert
	 */
	// TODO JavaDoc EN
	public final String[] labelTexts() {
		return labelTexts;
	}

	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	Methods   																													 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
	
	/** <!-- $LANGUAGE=DE -->
	 * Erstellt den Scenegraphen und fügt diesen dem Wurzelknoten hinzu.
	 * Subklassen können diese Methode überschreiben, um nach der Erstellung des Scenegraphen in dieser
	 * Klasse weitere Bedienelemente hinzuzufügen.
	 */
	// TODO JavaDoc EN
	@Override
	protected void createScenegraph() {
		// Label erstellen und in die GridPane einfügen
		createLabels();

		// Textfelder erstellen und in die GridPane einfügen
		createTextFields();
		
		// buttonGrid formatieren
		formatButtonGrid();
		
		// Buttons erstellen und in die GridPane einfügen
		createButtonMatrix();

		// Constraints für Größe und Wachstum setzen
		setRowConstraints();
		addColumnConstraint(center, 0, ConstraintType.FIRST_COLUMN);

		// GridPane formatieren
		updatePadding(null, null, null);

		center.getStyleClass().add("grid-pane");

		root.setCenter(center);

		// Properties binden
		bindProperties();
	}

	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	private Methods   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	
	// TODO JavaDoc
	private void observeCenter() {
		center.getRowConstraints().addListener(new ListChangeListener<RowConstraints>() {
			@Override
			public void onChanged(Change<? extends RowConstraints> c) {
				DoubleExpression minRowHeigths = new SimpleDoubleProperty(0);
				DoubleExpression maxRowHeigths = new SimpleDoubleProperty(0);
				
				for(RowConstraints rc : center.getRowConstraints()) {
					minRowHeigths = minRowHeigths.add(new When(rc.minHeightProperty().greaterThan(0)).then(rc.minHeightProperty()).otherwise(0));
					maxRowHeigths = maxRowHeigths.add(new When(rc.maxHeightProperty().greaterThan(0)).then(rc.maxHeightProperty()).otherwise(Double.MAX_VALUE));
				}
				
				NumberBinding vgapWidthBinding = new When(vgapProperty.greaterThan(0)).then(vgapProperty.multiply(center.getRowCount())).otherwise(0);
				
				center.minHeightProperty().bind(minRowHeigths.add(vgapWidthBinding));
				center.maxHeightProperty().bind(maxRowHeigths.add(vgapWidthBinding));
			}
		});
		AlphaNumGridView v = this;
		center.getColumnConstraints().addListener(new ListChangeListener<ColumnConstraints>() {
			@Override
			public void onChanged(Change<? extends ColumnConstraints> c) {
				DoubleExpression minColumnWidth = new SimpleDoubleProperty(0);
				DoubleExpression maxColumnWidth = new SimpleDoubleProperty(0);
				
				for(ColumnConstraints cc : center.getColumnConstraints()) {
					minColumnWidth = minColumnWidth.add(new When(cc.minWidthProperty().greaterThan(0)).then(cc.minWidthProperty()).otherwise(0));
					maxColumnWidth = maxColumnWidth.add(new When(cc.maxWidthProperty().greaterThan(0)).then(cc.maxWidthProperty()).otherwise(Double.MAX_VALUE));
				}
				
				ColumnConstraints firstColumn = center.getColumnConstraints().get(0);
				ColumnConstraints lastColumn = center.getColumnConstraints().get(center.getColumnConstraints().size() - 1);
				
				BooleanExpression lastColumnEqualsFirst = firstColumn.minWidthProperty().isEqualTo(lastColumn.minWidthProperty()).and(firstColumn.maxWidthProperty().isEqualTo(lastColumn.maxWidthProperty()));
				
				
				NumberBinding firstColumnWidthBinding = new When(lastColumnEqualsFirst).then(0).otherwise(new When(firstColumnWidthProperty.greaterThan(0)).then(firstColumnWidthProperty).otherwise(0));
				NumberBinding hgapWidthBinding = new When(hgapProperty.greaterThan(0)).then(hgapProperty.multiply(center.getColumnCount())).otherwise(0);
				
				System.out.println(v + " " + lastColumnEqualsFirst.get() + firstColumnWidthBinding.doubleValue());

				center.minWidthProperty().bind(minColumnWidth.add(hgapWidthBinding).add(firstColumnWidthBinding));
				center.maxWidthProperty().bind(maxColumnWidth.add(hgapWidthBinding).add(firstColumnWidthBinding));
			}
		});
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void observeSize() {
		DoubleProperty topMinHeightProperty = new SimpleDoubleProperty(0);
		DoubleProperty topMaxHeightProperty = new SimpleDoubleProperty(0);
		DoubleProperty centerMinHeightProperty = new SimpleDoubleProperty(0);
		DoubleProperty centerMaxHeightProperty = new SimpleDoubleProperty(0);
		DoubleProperty bottomMinHeightProperty = new SimpleDoubleProperty(0);
		DoubleProperty bottomMaxHeightProperty = new SimpleDoubleProperty(0);
		
		DoubleProperty leftMinWidthProperty = new SimpleDoubleProperty(0);
		DoubleProperty leftMaxWidthProperty = new SimpleDoubleProperty(0);
		DoubleProperty centerMinWidthProperty = new SimpleDoubleProperty(0);
		DoubleProperty centerMaxWidthProperty = new SimpleDoubleProperty(0);
		DoubleProperty rightMinWidthProperty = new SimpleDoubleProperty(0);
		DoubleProperty rightMaxWidthProperty = new SimpleDoubleProperty(0);
		
		listenHeigthProperties(root.topProperty(), topMinHeightProperty, topMaxHeightProperty);
		listenHeigthProperties(root.centerProperty(), centerMinHeightProperty, centerMaxHeightProperty);
		listenHeigthProperties(root.bottomProperty(), bottomMinHeightProperty, bottomMaxHeightProperty);
		
		listenWidthProperties(root.leftProperty(), leftMinWidthProperty, leftMaxWidthProperty);
		listenWidthProperties(root.centerProperty(), centerMinWidthProperty, centerMaxWidthProperty);
		listenWidthProperties(root.rightProperty(), rightMinWidthProperty, rightMaxWidthProperty);
		
		this.minHeightProperty.bind(topMinHeightProperty.add(centerMinHeightProperty).add(bottomMinHeightProperty));
		this.maxHeightProperty.bind(topMaxHeightProperty.add(centerMaxHeightProperty).add(bottomMaxHeightProperty));
		this.minWidthProperty.bind(leftMinWidthProperty.add(centerMinWidthProperty).add(rightMinWidthProperty));
		this.maxWidthProperty.bind(leftMaxWidthProperty.add(centerMaxWidthProperty).add(rightMaxWidthProperty));
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void listenHeigthProperties(ObjectProperty<Node> childProperty, DoubleProperty minHeightProperty, DoubleProperty maxHeightProperty) {
		updateHeigthProperties(childProperty.get(), minHeightProperty, maxHeightProperty);
		
		childProperty.addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> observable, Node oldChild, Node newChild) {
				updateHeigthProperties(newChild, minHeightProperty, maxHeightProperty);
			}
		});
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void updateHeigthProperties(Node child, DoubleProperty minHeightProperty, DoubleProperty maxHeightProperty) {
		minHeightProperty.unbind();
		maxHeightProperty.unbind();

		if (child instanceof Region) {
			DoubleProperty topInset = new SimpleDoubleProperty(((Region) child).getPadding().getTop() > 0 ? ((Region) child).getPadding().getTop() : 0);
			DoubleProperty bottomInset = new SimpleDoubleProperty(((Region) child).getPadding().getBottom() > 0 ? ((Region) child).getPadding().getBottom() : 0);

			((Region) child).paddingProperty().addListener(new ChangeListener<Insets>() {
				@Override
				public void changed(ObservableValue<? extends Insets> observable, Insets oldValue, Insets newInsets) {
					topInset.set(newInsets.getTop() > 0 ? newInsets.getTop() : 0);
					bottomInset.set(newInsets.getBottom() > 0 ? newInsets.getBottom() : 0);
				}
			});
			
			NumberBinding minHeightBinding = new When(((Region) child).minHeightProperty().greaterThan(0)).then(((Region) child).minHeightProperty()).otherwise(0);
			minHeightProperty.bind(minHeightBinding.add(topInset).add(bottomInset));
			
			NumberBinding maxHeightBinding = new When(((Region) child).maxHeightProperty().greaterThan(0)).then(((Region) child).maxHeightProperty()).otherwise(Double.MAX_VALUE);
			maxHeightProperty.bind(maxHeightBinding.add(topInset).add(bottomInset));
		}
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void listenWidthProperties(ObjectProperty<Node> childProperty, DoubleProperty minWidthProperty, DoubleProperty maxWidthProperty) {
		updateWidthProperties(childProperty.get(), minWidthProperty, maxWidthProperty);
		
		childProperty.addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> observable, Node oldChild, Node newChild) {
				updateWidthProperties(newChild, minWidthProperty, maxWidthProperty);
			}
		});
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void updateWidthProperties(Node child, DoubleProperty minWidthProperty, DoubleProperty maxWidthProperty) {
		minWidthProperty.unbind();
		maxWidthProperty.unbind();

		if (child instanceof Region) {
			DoubleProperty leftInset = new SimpleDoubleProperty(((Region) child).getPadding().getLeft() > 0 ? ((Region) child).getPadding().getLeft() : 0);
			DoubleProperty rigthInset = new SimpleDoubleProperty(((Region) child).getPadding().getRight() > 0 ? ((Region) child).getPadding().getRight() : 0);

			((Region) child).paddingProperty().addListener(new ChangeListener<Insets>() {
				@Override
				public void changed(ObservableValue<? extends Insets> observable, Insets oldValue, Insets newInsets) {
					leftInset.set(newInsets.getLeft() > 0 ? newInsets.getLeft() : 0);
					rigthInset.set(newInsets.getRight() > 0 ? newInsets.getRight() : 0);
				}
			});
			
			NumberBinding minWidthBinding = new When(((Region) child).minWidthProperty().greaterThan(0)).then(((Region) child).minWidthProperty()).otherwise(0);
			minWidthProperty.bind(minWidthBinding.add(leftInset).add(rigthInset));
			
			NumberBinding maxWidthBinding = new When(((Region) child).maxWidthProperty().greaterThan(0)).then(((Region) child).maxWidthProperty()).otherwise(Double.MAX_VALUE);
			maxWidthProperty.bind(maxWidthBinding.add(leftInset).add(rigthInset));
		}
	}

	
	

// GridPane Center	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<
	
	/** <!-- $LANGUAGE=DE -->
	 * Setzt alle {@code RowConstraints} für center.
	 * <p>
	 * Es werden die Höhe für Zeilen mit Textfeldern, Buttons und Whitespace eingestellt. Zudem werden die Anordnung 
	 * und das Größenwachstum für die Zeilen der GridPane eingestellt.
	 * </p>
	 */
	// TODO JavaDoc EN
	private void setRowConstraints() {
		// RowConstraints für Zeilen mit Textfeldern
		for (int i = firstTFRow; i < firstTFRow + getTextFieldMap().size(); i++) {
			addRowConstraint(center, i, ConstraintType.TEXT_FIELD_ROW);
		}

		// Zeilen zwischen Textfeldern und Buttons
		int firstWhitespaceRow = Math.min(firstTFRow + getTextFieldMap().size(), firstKeyBtnRow + AlphaNumKeys.ROW_COUNT);
		int whitespaceEnd = Math.max(firstTFRow, firstKeyBtnRow);
		
		for (int i = firstWhitespaceRow; i < whitespaceEnd; i++) {
			addRowConstraint(center, i, ConstraintType.WHITESPACE_ROW);
		}
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	/** <!-- $LANGUAGE=DE -->
	 * Erstellt neue RowConstraints für die Zeile mit dem Index {@code rowIndex}, die an den ConstraintType angepasst sind.
	 * Der ConstraintType bestimmt, ob sich in der Zeile Textfelder, Buttons oder Weißraum befindet, um Valignment und Vgrow
	 * anzupassen und die Properties maxHeightProperty und minHeightProperty mit den passenden Properties dieser AlphaNumGridView
	 * bidirektional zu verbinden. Ist die übergebene GridPane das Attribut {@link #buttonGrid}, wird die Zeilenhöhe Prozentual angepasst.
	 * 
	 * @param grid		GridPane, der sie RowConstraints hinzugefügt werden
	 * @param rowIndex	Index der Zeile, für die neue RowConstraints hinzugefügt werden
	 * @param type		Typ der Zeile
	 */
	// TODO JavaDoc EN
	protected void addRowConstraint(GridPane grid, int rowIndex, ConstraintType type) {
		if(grid.equals(buttonGrid)) {
			addRowConstraint(grid, rowIndex, type, true);
			return;
		}
		
		addRowConstraint(grid, rowIndex, type, false);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	/** <!-- $LANGUAGE=DE -->
	 * Erstellt neue RowConstraints für die Zeile mit dem Index {@code rowIndex}, die an den ConstraintType angepasst sind.
	 * Der ConstraintType bestimmt, ob sich in der Zeile Textfelder, Buttons oder Weißraum befindet, um Valignment und Vgrow
	 * anzupassen und die Properties maxHeightProperty und minHeightProperty mit den passenden Properties dieser AlphaNumGridView
	 * bidirektional zu verbinden.
	 * 
	 * @param grid		GridPane, der sie RowConstraints hinzugefügt werden
	 * @param rowIndex	Index der Zeile, für die neue RowConstraints hinzugefügt werden
	 * @param type		Typ der Zeile
	 * @param usePercentHeigth	{@code true}, wenn die Zeilenhöhe gleichmäßig prozentual verteilt werden soll, sonst {@code false}
	 */
	// TODO JavaDoc EN
	protected void addRowConstraint(GridPane grid, int rowIndex, ConstraintType type, boolean usePercentHeigth) {
		if(rowIndex < 0) {
			return;
		}
		
		RowConstraints rowc = createRowConstraints(type);
		
		if (rowc == null) {
			return;
		}
		
		if(usePercentHeigth) rowc.setPercentHeight(100.0 / grid.getRowCount());
		
		addRowConstraint(grid, rowIndex, rowc);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erstellt neue RowConstraints, die an den ConstraintType angepasst sind.
	 * Der ConstraintType bestimmt, ob sich in der Zeile Textfelder, Buttons oder Weißraum befindet, um Valignment und Vgrow
	 * anzupassen und die Properties maxHeightProperty und minHeightProperty mit den passenden Properties dieser AlphaNumGridView
	 * zu verbinden.
	 * 
	 * @param type	Typ der Zeile
	 * 
	 * @return		neue RowConstraints passend zu dem ConstraintType
	 */
	// TODO JavaDoc EN
	protected RowConstraints createRowConstraints(ConstraintType type){
		RowConstraints rowc = new RowConstraints();
		rowc.setFillHeight(true);
		
		switch(type) {
		case TEXT_FIELD_ROW:
			rowc.maxHeightProperty().bind(tfMaxHeightProperty);
			rowc.minHeightProperty().bind(tfMinHeightProperty);
			break;
		case BUTTON_ROW:
			rowc.maxHeightProperty().bind(btnMaxHeigthProperty);
			rowc.minHeightProperty().bind(btnMinHeigthProperty);
			rowc.setValignment(VPos.CENTER);
			rowc.setVgrow(Priority.ALWAYS);
			break;
		case WHITESPACE_ROW:
			rowc.maxHeightProperty().bind(whiteSpaceHeigthProperty);
			rowc.minHeightProperty().bind(whiteSpaceHeigthProperty);
			break;
		case FIRST_COLUMN:	/* fall through	*/
		case BUTTON_COLUMN:	/* fall through	*/
		default:
			return null;
		}
		
		return rowc;
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Legt die übergebenen RowConstraints für die Zeile mit dem Index {@code rowIndex} fest.
	 * 
	 * @param grid		GridPane, der sie RowConstraints hinzugefügt werden
	 * @param rowIndex	Index der Zeile, für die neue RowConstraints hinzugefügt werden
	 * @param rowc		neue RowConstrains, die hinzugefügt werden
	 */
	// TODO JavaDoc EN
	protected void addRowConstraint(GridPane grid, int rowIndex, RowConstraints rowc) {
		if(rowIndex < 0) {
			return;
		}
		
		// Constraints auffüllen, wenn vorherige Constraints fehlen
		while(rowIndex > grid.getRowConstraints().size()) {
			grid.getRowConstraints().add(createRowConstraints(ConstraintType.WHITESPACE_ROW));
		}
		
		// Alte Constraints entfernen, um nicht alle Constraints zu verschieben
		try {
			grid.getRowConstraints().remove(rowIndex);
		} catch (Exception e) { /* ignore */ }
		
		// Constraints hinzufügen
		grid.getRowConstraints().add(rowIndex, rowc);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erstellt neue ColumnConstraints für die Spalte mit dem Index {@code columnIndex}, die an den ConstraintType angepasst sind.
	 * Der ConstraintType bestimmt, ob es sich um die erste Spalte oder um eine Spalte mit Buttons handelt, um Hgrow
	 * anzupassen und die Properties maxWidthProperty und minWidthProperty mit den passenden Properties dieser AlphaNumGridView
	 * bidirektional zu verbinden. Ist die übergebene GridPane das Attribut {@link #buttonGrid}, wird die Spaltenbreite Prozentual angepasst.
	 * 
	 * @param grid			GridPane, der sie ColumnConstraints hinzugefügt werden
	 * @param columnIndex	Index der Spalte, für die neue RowConstraints hinzugefügt werden
	 * @param type			Typ der Spalte
	 */
	// TODO JavaDoc EN
	protected void addColumnConstraint(GridPane grid, int columnIndex, ConstraintType type) {
		if(grid.equals(buttonGrid)) {
			addColumnConstraint(grid, columnIndex, type, true);
			return;
		}
		
		addColumnConstraint(grid, columnIndex, type, false);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Erstellt neue ColumnConstraints für die Spalte mit dem Index {@code columnIndex}, die an den ConstraintType angepasst sind.
	 * Der ConstraintType bestimmt, ob es sich um die erste Spalte oder um eine Spalte mit Buttons handelt, um Hgrow
	 * anzupassen und die Properties maxWidthProperty und minWidthProperty mit den passenden Properties dieser AlphaNumGridView
	 * bidirektional zu verbinden.
	 * 
	 * @param grid			GridPane, der sie ColumnConstraints hinzugefügt werden
	 * @param columnIndex	Index der Spalte, für die neue RowConstraints hinzugefügt werden
	 * @param type			Typ der Spalte
	 * @param usePercentWidth	{@code true}, wenn die Spaltenbreite gleichmäßig prozentual verteilt werden soll, sonst {@code false}
	 */
	// TODO JavaDoc EN
	protected void addColumnConstraint(GridPane grid, int columnIndex, ConstraintType type, boolean usePercentWidth) {
		if(columnIndex < 0) {
			return;
		}
		
		ColumnConstraints column = new ColumnConstraints();
		column.setFillWidth(true);
		column.setHalignment(HPos.CENTER);
		
		switch(type) {
		case FIRST_COLUMN:
			column.setHgrow(Priority.NEVER);
			column.maxWidthProperty().bind(firstColumnWidthProperty);
			column.minWidthProperty().bind(firstColumnWidthProperty);
			break;
		case BUTTON_COLUMN:
			column.setHgrow(Priority.ALWAYS);
			column.maxWidthProperty().bind(btnMaxWidthProperty);
			column.minWidthProperty().bind(btnMinWidthProperty);
			break;
		case TEXT_FIELD_ROW:	/* fall through	*/
		case BUTTON_ROW:		/* fall through	*/
		case WHITESPACE_ROW:	/* fall through	*/
		default:
			return;
		}
		
		addColumnConstraint(grid, columnIndex, column);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Legt die übergebenen ColumnConstraints für die Spalte mit dem Index {@code columnIndex} fest.
	 * 
	 * @param grid			GridPane, der sie ColumnConstraints hinzugefügt werden
	 * @param columnIndex	Index der Spalte, für die neue RowConstraints hinzugefügt werden
	 * @param column		ColumnConstranits, die hinzugefügt werden
	 */
	// TODO JavaDoc EN
	protected void addColumnConstraint(GridPane grid, int columnIndex, ColumnConstraints column) {
		if(columnIndex < 0) {
			return;
		}
		
		// Constraints auffüllen, wenn vorherige Constraints fehlen
		while(columnIndex > center.getColumnConstraints().size()) {
			grid.getColumnConstraints().add(new ColumnConstraints());
		}
		
		// Alte Constraints entfernen, um nicht alle Constraints zu verschieben
		try {
			grid.getColumnConstraints().remove(columnIndex);
		} catch (Exception e) { /* ignore */ }
		
		// Constraints hinzufügen
		grid.getColumnConstraints().add(columnIndex, column);
	}
	
	
	
// TextFields	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<

	/**<!-- $LANGUAGE=DE -->
	 * Erstellt alle Textfelder, für die es einen Schlüssel im Array {@link #tfKeys} gibt. Jedes Textfeld wird 
	 * als Instanz von {@link ValueField} der GridPane hinzugefügt und die Constraints entsprechend gesetzt. 
	 * Alle Textfelder werden mit den Schlüsselwörtern aus {@link #tfKeys} in der Textfeld-Map gespeichert.
	 */
	// TODO JavaDoc EN
	private void createTextFields() {
		for (int row = firstTFRow; row < firstTFRow + tfKeys.length; row++) {
			TextField tf = new ValueField();

			// Constraints für GridPane setzen
			int columnSpan = setTFColumnSpan ? AlphaNumKeys.COLUMN_COUNT : 1;
			GridPane.setConstraints(tf, tfColumn, row, columnSpan, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.ALWAYS);

			// Textfeld der GridPane hinzufügen
			center.getChildren().add(tf);

			// Textfeld mit Schlüssel in Map speichern
			getTextFieldMap().put(tfKeys[row - firstTFRow], tf);
		}
	}

	
// Labels	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<

	/** <!-- $LANGUAGE=DE -->
	 * Erstellt alle Labels aus den Strings im Array {@link #labelTexts} und positioniert diese in der GridPane.
	 */
	// TODO JavaDoc EN
	private void createLabels() {
		ArrayDeque<Node> labels = new ArrayDeque<>();

		for (int i = firstLabelRow; i < firstLabelRow + labelTexts.length; i++) {
			labels.add(new Label(labelTexts[i - firstLabelRow]));
		}

		FXUtils.setGridConstraints(labelColumn, firstLabelRow, 1, 0, labels);
		center.getChildren().addAll(labels);
	}

	
// Buttons	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<
	
	// TODO JavaDoc
	private void formatButtonGrid() {
		updateButtonConstraints();
		
		GridPane.setConstraints(buttonGrid, firstKeyBtnColumn, firstKeyBtnRow, AlphaNumKeys.COLUMN_COUNT, 5, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		GridPane.setFillHeight(buttonGrid, true);
		GridPane.setFillWidth(buttonGrid, true);
		
		buttonGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		buttonGrid.hgapProperty().bind(hgapProperty);
		buttonGrid.vgapProperty().bind(vgapProperty);
		
		center.getChildren().add(buttonGrid);
	}

//	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void updateButtonConstraints() {
		buttonGrid.getChildren().addListener(new ListChangeListener<Node>() {
			private int lastRowCount = 0;
			private int lastColumnCount = 0;
			
			@Override
			public void onChanged(Change<? extends Node> c) {
				if (buttonGrid.getRowCount() != lastRowCount) {
					updateRowConstraints();
					lastRowCount = buttonGrid.getRowCount();
				}
				
				if (buttonGrid.getColumnCount() != lastColumnCount) {
					updateColumnConstraints();
					lastColumnCount = buttonGrid.getColumnCount();
				}
			}
		});
	}

//	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void updateRowConstraints() {
		buttonGrid.getRowConstraints().clear();
		
		for (int row = 0; row < buttonGrid.getRowCount(); row++) {
			addRowConstraint(buttonGrid, row, ConstraintType.BUTTON_ROW);
			addRowConstraint(center, row + GridPane.getRowIndex(buttonGrid), ConstraintType.BUTTON_ROW);
		}
		
		GridPane.setRowSpan(buttonGrid, buttonGrid.getRowCount());
	}

//	 *	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	// TODO JavaDoc
	private void updateColumnConstraints() {
		buttonGrid.getColumnConstraints().clear();
		
		for (int column = 0; column < buttonGrid.getColumnCount(); column++) {
			addColumnConstraint(buttonGrid, column, ConstraintType.BUTTON_COLUMN);
			addColumnConstraint(center, column + GridPane.getColumnIndex(buttonGrid), ConstraintType.BUTTON_COLUMN);
		}
		
		GridPane.setColumnSpan(buttonGrid, buttonGrid.getColumnCount());
		
		for(TextField tf : getTextFieldMap().values()) {
			GridPane.setColumnSpan(tf, buttonGrid.getColumnCount());
		}
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
		
	/** <!-- $LANGUAGE=DE -->
	 * Erstellt alle benötigten Buttons und positioniert diese in der GridPane.
	 * 
	 * @see #createButtons()
	 * @see AlphaNumKeys
	 */
	// TODO JavaDoc EN
	private void createButtonMatrix() {
		// Liste mit allen Buttons in richtiger Reihenfolge (oben-links nach rechts-unten in der Tabelle!)
		ArrayDeque<Button> buttonList = createButtons();
		formatButtons();

		if (useClearAndBackBtn) {
			// Constraints für Position in der Tabelle setzen und in die Tabelle legen
			FXUtils.setGridConstraints(0, 0, AlphaNumKeys.COLUMN_COUNT, 2, buttonList, buttonGrid::add);
		}
		
		// Tastaturfeld in die Tabelle legen (Constraints werden bereits in AlphaNumKeys gesetzt!)
		getButtonMap().putAll(alphaNum.getButtonMap());
		buttonGrid.getChildren().addAll(alphaNum.getButtonMatrix());
		
		
		// Maximale Größe der Buttons setzen
		FXUtils.setMaxSizes(buttonList, Double.MAX_VALUE);
		FXUtils.setMaxSizes(alphaNum.getButtonMatrix(), Double.MAX_VALUE);
	}
	
// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*
	
	/** <!-- $LANGUAGE=DE -->
	 * Erzeugt die Buttons {@link #clearBtnKey} und {@link #backspaceBtnKey} und gibt diese in einer ArrayList zurück.
	 * Alle Buttons werden auch in der Map für Buttons abgelegt.
	 * <p><b>
	 * Alle Buttons werden als Instanz von {@link UnfocusedButton} angelegt.
	 * </b></p>
	 * 
	 * @return Liste der erstellten Buttons
	 */
	// TODO JavaDoc EN
	private ArrayDeque<Button> createButtons() {
		// Liste mit allen Buttons in richtiger Reihenfolge (oben-links nach rechts-unten in der Tabelle!)
		ArrayDeque<Button> buttons = new ArrayDeque<>(2);

		for (String btnKey : ArrayUtils.arrayOf(clearBtnKey, backspaceBtnKey)) {
			UnfocusedButton b = new UnfocusedButton();
			
			if (this.btnTexts.get(btnKey) instanceof String) {
				b.setText((String) this.btnTexts.get(btnKey));
			} 
			else if (this.btnTexts.get(btnKey) instanceof Node) {
				b.setGraphic((Node) this.btnTexts.get(btnKey));
			}

			// Buttons mit Schlüssel in Map speichern
			getButtonMap().put(btnKey, b);

			// angelegte Buttons im Array speichern und zurückgeben
			buttons.add(b);
		}
		
		return buttons;
	}
	
	
	// TODO JavaDoc
	private void formatButtons() {
		// BackspaceButton muss auf zwei Spalten verteilt werden!
		GridPane.setColumnSpan(getButtonMap().get(backspaceBtnKey), 2);
		
		// Skalierungsfaktor für das Icon im Reset-Button anpassen
		((UnfocusedButton)getButtonMap().get(clearBtnKey)).setGraphicScaleFactor(0.023);
	}


// Properties	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<	<<

	/** <!-- $LANGUAGE=DE -->
	 * Bindet die Properties dieser AlphaNumGridView bidirektional an korrespondierende Properties der enthaltenen
	 * Objekte. Setzt zudem auch Listener, um die Paddings der GridPane bei Änderung der Padding-Properties anzupassen.
	 * 
	 * @see #updatePadding(ObservableValue, Number, Number)
	 */
	// TODO JavaDoc EN
	private void bindProperties() {
		getButtonMap().forEach(new BiConsumer<String, Button>() {
			@Override
			public void accept(String key, Button b) {
				if (!key.equals(AlphaNumKeys.NEXT_BTN_KEY) && !key.equals(AlphaNumKeys.PREVIOUS_BTN_KEY)) {
					b.maxHeightProperty().bind(btnMaxHeigthProperty);
					b.minHeightProperty().bind(btnMinHeigthProperty);
					b.maxWidthProperty().bind(btnMaxWidthProperty);
					b.minWidthProperty().bind(btnMinWidthProperty);
				}
			}
		});

		getTextFieldMap().forEach(new BiConsumer<String, TextField>() {
			@Override
			public void accept(String key, TextField tf) {
				tf.maxHeightProperty().bind(tfMaxHeightProperty);
				tf.minHeightProperty().bind(tfMinHeightProperty);
			}
		});
		
		if(hgapProperty instanceof DoubleProperty) {
			center.hgapProperty().bindBidirectional((DoubleProperty) hgapProperty);
		} else {
			center.hgapProperty().bind(hgapProperty);
		}
		
		if(vgapProperty instanceof DoubleProperty) {
			center.vgapProperty().bindBidirectional((DoubleProperty) vgapProperty);
		} else {
			center.vgapProperty().bind(vgapProperty);
		}
		
		try { ((DoubleProperty) btnSpacingProperty).bindBidirectional((DoubleProperty) hgapProperty); } catch(Exception e) { /* ignore */ }
		
		try { ((DoubleProperty) btnSpacingProperty).bindBidirectional((DoubleProperty)vgapProperty); } catch(Exception e) { /* ignore */ }
		
		paddingTopProperty.addListener(this::updatePadding);
		paddingRigthProperty.addListener(this::updatePadding);
		paddingBottomProperty.addListener(this::updatePadding);
		paddingLeftProperty.addListener(this::updatePadding);
	}

// 	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*	*

	/** <!-- $LANGUAGE=DE -->
	 * Methode, die als Referenz für einen ChangeListener eingesetzt werden kann, um die Paddings der GridPane zu aktualisieren.
	 * 
	 * @param observable	{@code ObservableValue}, der sich ändert
	 * @param oldValue		alter Wert
	 * @param newValue		neuer Wert
	 */
	// TODO JavaDoc EN
	private void updatePadding(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		center.setPadding(new Insets(paddingTopProperty.get(), paddingRigthProperty.get() + firstColumnWidthProperty.get(),
						paddingBottomProperty.get(), paddingLeftProperty.get()));
	}

	
	
//	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##
//  #																																 #
// 	#	nested Classes   																											 #
//  #																																 #
//  ##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##	##

	
	/** <!-- $LANGUAGE=DE -->
	 * Aufzählung von möglichen Typen für Constraints einer GridPane
	 * 
	 * @author Tim
	 *
	 * @since Bitchanger 0.1.4
	 * @version 0.1.4
	 */
	// TODO JavaDoc EN
	protected static enum ConstraintType{
		/** <!-- $LANGUAGE=DE -->	Konstante für eine Zeile, die Textfelder enthält */
		// TODO JavaDoc EN
		TEXT_FIELD_ROW,
		
		/** <!-- $LANGUAGE=DE -->	Konstante für eine Zeile, die Buttons enthält */
		// TODO JavaDoc EN
		BUTTON_ROW,
		
		/** <!-- $LANGUAGE=DE -->	Konstante für eine Zeile, die Weißraum enthält */
		// TODO JavaDoc EN
		WHITESPACE_ROW,
		
		/** <!-- $LANGUAGE=DE -->	Konstante für die erste Spalte in einer GridPane */
		// TODO JavaDoc EN
		FIRST_COLUMN,
		
		/** <!-- $LANGUAGE=DE -->	Konstante für eine Spalte, die Buttons enthält */
		// TODO JavaDoc EN
		BUTTON_COLUMN;
	}
	
	
}


