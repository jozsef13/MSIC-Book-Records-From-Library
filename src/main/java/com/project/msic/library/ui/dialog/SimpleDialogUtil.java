package com.project.msic.library.ui.dialog;

/**
 * @author Simon Jozsef-Gabriel
 * Utility class for creating Dialogs like Confirm or Error dialogs
 */
public class SimpleDialogUtil {

	/**
	 * Private constructor that will hide the implicit public one. This is necessary
	 * because this is a utility class, and it is not meant to be instantiated
	 */
	private SimpleDialogUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static ConfirmDialog confirmDialog(String caption, String text, String confirmButtonText,
			Runnable confirmListener) {
		return new ConfirmDialog(caption, text, confirmButtonText, confirmListener);
	}

	public static ErrorDialog errorDialog(String caption, String text) {
		return new ErrorDialog(caption, text);
	}

}
