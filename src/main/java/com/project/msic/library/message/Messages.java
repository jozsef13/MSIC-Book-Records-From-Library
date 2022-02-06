package com.project.msic.library.message;

/**
 * An abstract class that hold all the static variables/constants for the texts
 *
 */
public abstract class Messages {

	/**
	 * Private constructor that will hide the implicit public one. This is necessary
	 * because this is a utility class, which has collections of static members, and
	 * it is not meant to be instantiated
	 */
	private Messages() {
		throw new IllegalStateException("Messages utility class");
	}

	// AboutView messages
	public static final String ABOUT_VIEW_TEXT = " This application is a simulation of an online library management system. "
			+ "It is implemented using Java, Spring Boot and ";

	// AuthorView messages
	public static final String AUTHOR_SAVED_TEXT = "Author Saved.";
	public static final String EDIT_AUTHORS_TITLE = "Edit Authors";
	public static final String AUTHOR_DELETED_TEXT = "Author Deleted.";
	public static final String DELETE_AUTHOR_WARN_TEXT = "Are you sure you want to delete the author? Books with this author "
			+ "will not be deleted.";
	public static final String ADD_NEW_AUTHOR_TEXT = "Add New Author";

	// CategoryView messages
	public static final String EDIT_CATEGORIES_TEXT = "Edit Categories";
	public static final String CATEGORY_SAVED_MESSAGE = "Category Saved.";
	public static final String CATEGORY_DELETED_MESSAGE = "Category Deleted.";
	public static final String DELETE_CATEGORY_WARN_MESSAGE = "Are you sure you want to delete the category? Books in this "
			+ "category will not be deleted.";
	public static final String ADD_NEW_CATEGORY_MESSAGE = "Add New Category";

	// General messages
	public static final String CONFIRM_DIALOG_TITLE = "Please confirm";
	public static final String DELETE_MESSAGE = "Delete";
	public static final String CANCEL_BUTTON_MESSAGE = "Cancel";
	public static final String VIEW_NOT_FOUND_ERROR_MESSAGE = "The view could not be found.";

	// Inventory messages
	public static final String NULL_BOOK_MESSAGE = "Cannot provide an id for a null book.";
	public static final String FILTER_TEXT_CANNOT_BE_NULL_MESSAGE = "Filter text cannot be null.";
	public static final String CANCEL_BUTTON_TEXT = "Cancel";
	public static final String DISCARD_CHANGES_BUTTON_TEXT = "Discard changes";
	public static final String SAVE_BUTTON_TEXT = "Save";
	public static final String PUBLISHER_TEXT = "Publisher";
	public static final String AUTHOR_TEXT = "Author";
	public static final String CATEGORY_TEXT = "Category";
	public static final String AVAILABILITY_ID_TEXT = "availability";
	public static final String AVAILABILITY_TEXT = "Availability";
	public static final String TITLE_TEXT_LABEL = "Title";
	public static final String YEARS_TEXT_LABEL = "Year";
	public static final String PRODUCT_FORM_CONTENT_CLASS = "product-form-content";
	public static final String PRODUCT_FORM_CLASS = "product-form";
	public static final String BOOKNAME = "bookname";
	public static final String YEAR_COLUMN = "Year";
	public static final String BOOKYEAR = "bookyear";
	public static final String AUTHOR = "author";
	public static final String PUBLISHER = "publisher";
	public static final String CATEGORY = "category";
	public static final String BOOK_TITLE_COLUMN = "Book title";
	public static final String NEW_BOOK_BUTTON_TEXT = "New Book";
	public static final String FILTER_PLACEHOLDER_TEXT = "Filter name";
	public static final String BOOK_ID_NUMBER_FORMAT_ERROR_TEXT = "There was a problem with the ID of this book. "
			+ "If the problem persists, please contact the administrator.";
	public static final String ERROR_TITLE = "Error";

	// LoginScreen messages
	public static final String LOGIN_INFO_TEXT = "Log in as \"admin\" to have full access. Log in with any "
			+ "other username to have read-only access. For all " + "users, the password is same as the username.";
	public static final String LOGIN_INFORMATION_HEADER_TEXT = "Login Information";
	public static final String LOGIN_INFORMATION_CLASS_MESSAGE = "login-information";
	public static final String LOGIN_HINT_MESSAGE = "Hint: same as username";
	public static final String LOGIN_SCREEN_CLASS_MESSAGE = "login-screen";

	// PublisherView messages
	public static final String PUBLISHER_SAVED_MESSAGE = "Publisher Saved.";
	public static final String PUBLISHER_DELETED_MESSAGE = "Publisher Deleted.";
	public static final String PUBLISHER_DELETE_WARN_MESSAGE = "Are you sure you want to delete the publisher? Books with "
			+ "this publisher will not be deleted.";
	public static final String EDIT_PUBLISHERS_MESSAGE = "Edit Publishers";
	public static final String ADD_NEW_PUBLISHER_MESSAGE = "Add New Publisher";

	// AdminView messages
	public static final String ADMIN_VIEW_PUBLISHERS_MENU_LABEL = "Publishers";
	public static final String ADMIN_VIEW_AUTHORS_MENU_LABEL = "Authors";
	public static final String ADMIN_VIEW_CATEGORIES_MENU_LABEL = "Categories";

	// MainLayout messages
	public static final String MENU_BUTTON_CLASS_TEXT = "menu-button";
	public static final String MENU_LINK_CLASS_TEXT = "menu-link";
	public static final String LOGOUT_SHORTCUT_TEXT = "Logout (Ctrl+L)";
	public static final String TITLE_ATTRIBUTE_TEXT = "title";
	public static final String LOGOUT_TEXT = "Logout";
	public static final String TITLE_LABEL = "Craiova Library";
	public static final String LOGO_PATH = "img/table-logo.png";
	public static final String MENU_HEADER_CLASS_TEXT = "menu-header";
	public static final String MENU_TOGGLE_CLASS_TEXT = "menu-toggle";

	
}
