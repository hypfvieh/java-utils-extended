package com.github.hypfvieh.util.fx;

import java.io.InputStream;

import com.github.hypfvieh.util.fx.fonts.IWebFontCode;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Utility to work with different (Web)fonts.
 *
 * @author hypfvieh
 *
 */
@SuppressWarnings("restriction")
public class FxFontUtil {

	private FxFontUtil() {

	}

	/**
	 * Load given font (ttf) from stream.
	 * This is only another way to load fonts for JavaFX applications.
	 * You can also use {@link Font#loadFont(InputStream, double)}.
	 * @param _font
	 * @param _size
	 */
	public static void loadFont(InputStream _font, double _size) {
		Font.loadFont(_font, _size);
	}

	/**
	 * Creates a new {@link Label} with the given 'iconCode' (e.g. "\uf000") and the given font size.
	 * @param _iconCode
	 * @param _size
	 * @return label
	 */
	public static Label createIconLabel(String _iconCode, double _size) {
		Label label = new Label(_iconCode);
    	label.setStyle("-fx-font-size: "+ _size + "px;");
    	return label;
	}

	/**
	 * Creates a new {@link Label} with the given 'icon' and the given font size.
	 * @param _iconCode
	 * @param _size
	 * @return label
	 */
	public static Label createIconLabel(IWebFontCode _iconCode, double _size) {
    	Label label = createIconLabel(_iconCode.getCharacterAsString(), _size);
    	label.setStyle(label.getStyle() + "-fx-font-family: " + _iconCode.getFontFamily() + ";");
    	label.getStyle();
		return label;
	}
}
