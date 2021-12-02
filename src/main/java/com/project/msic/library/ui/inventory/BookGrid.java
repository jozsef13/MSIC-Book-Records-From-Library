package com.project.msic.library.ui.inventory;

import java.util.Comparator;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Author;
import com.project.msic.library.model.Book;
import com.project.msic.library.model.Category;
import com.project.msic.library.model.Publisher;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;

/**
 * Grid of books, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class BookGrid extends Grid<Book> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2281039855556671554L;

	public BookGrid() {
		setSizeFull();
		addColumn(Book::getTitle).setHeader(Messages.BOOK_TITLE_COLUMN).setFlexGrow(20).setSortable(true).setKey(Messages.BOOKNAME);

		final String availabilityTemplate = "<iron-icon icon=\"vaadin:circle\" class-name=\"[[item.availability]]\"></iron-icon> [[item.availability]]";
		addColumn(TemplateRenderer.<Book>of(availabilityTemplate).withProperty(Messages.AVAILABILITY_ID_TEXT,
				book -> book.getAvailability().toString())).setHeader(Messages.AVAILABILITY_TEXT)
						.setComparator(Comparator.comparing(Book::getAvailability)).setFlexGrow(5)
						.setKey(Messages.AVAILABILITY_ID_TEXT);

		addColumn(this::formatCategory).setHeader(Messages.CATEGORY_TEXT).setFlexGrow(12).setKey(Messages.CATEGORY);
		addColumn(this::formatPublisher).setHeader(Messages.PUBLISHER_TEXT).setFlexGrow(12).setKey(Messages.PUBLISHER);
		addColumn(this::formatAuthor).setHeader(Messages.AUTHOR_TEXT).setFlexGrow(12).setKey(Messages.AUTHOR);

		// If the browser window size changes, check if all columns fit on
		// screen
		// (e.g. switching from portrait to landscape mode)
		UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> setColumnVisibility(e.getWidth()));
	}

	private void setColumnVisibility(int width) {
		if (width > 800) {
			getColumnByKey(Messages.BOOKNAME).setVisible(true);
			getColumnByKey(Messages.AVAILABILITY_ID_TEXT).setVisible(true);
			getColumnByKey(Messages.CATEGORY).setVisible(true);
			getColumnByKey(Messages.PUBLISHER).setVisible(true);
			getColumnByKey(Messages.AUTHOR).setVisible(true);
		} else if (width > 550) {
			getColumnByKey(Messages.BOOKNAME).setVisible(true);
			getColumnByKey(Messages.AVAILABILITY_ID_TEXT).setVisible(true);
			getColumnByKey(Messages.CATEGORY).setVisible(false);
			getColumnByKey(Messages.PUBLISHER).setVisible(false);
			getColumnByKey(Messages.CATEGORY).setVisible(true);
		} else {
			getColumnByKey(Messages.BOOKNAME).setVisible(true);
			getColumnByKey(Messages.AVAILABILITY_ID_TEXT).setVisible(true);
			getColumnByKey(Messages.CATEGORY).setVisible(false);
			getColumnByKey(Messages.PUBLISHER).setVisible(false);
			getColumnByKey(Messages.AUTHOR).setVisible(false);
		}
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		// fetch browser width
		UI.getCurrent().getInternals().setExtendedClientDetails(null);
		UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> setColumnVisibility(e.getBodyClientWidth()));
	}

	public Book getSelectedRow() {
		return asSingleSelect().getValue();
	}

	public void refresh(Book book) {
		getDataCommunicator().refresh(book);
	}

	private String formatCategory(Book book) {
		Category category = book.getCategory();
		if (category == null) {
			return "";
		}

		return category.getName();
	}

	private String formatAuthor(Book book) {
		Author author = book.getAuthor();
		if (author == null) {
			return "";
		}

		return author.getName();
	}

	private String formatPublisher(Book book) {
		Publisher publisher = book.getPublisher();
		if (publisher == null) {
			return "";
		}

		return publisher.getName();
	}
}
