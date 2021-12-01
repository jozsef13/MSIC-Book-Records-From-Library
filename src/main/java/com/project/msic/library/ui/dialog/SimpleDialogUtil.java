package com.project.msic.library.ui.dialog;

public class SimpleDialogUtil {

	public static ConfirmDialog confirmDialog(String caption, String text, String confirmButtonText,
			Runnable confirmListener) {
		return new ConfirmDialog(caption, text, confirmButtonText, confirmListener);
	}

	public static ErrorDialog errorDialog(String caption, String text) {
		return new ErrorDialog(caption, text);
	}

}
