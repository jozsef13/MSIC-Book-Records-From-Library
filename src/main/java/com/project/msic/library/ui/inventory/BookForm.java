package com.project.msic.library.ui.inventory;

import java.util.Collection;

import com.project.msic.library.message.Messages;
import com.project.msic.library.model.Author;
import com.project.msic.library.model.Book;
import com.project.msic.library.model.BookAvailability;
import com.project.msic.library.model.Category;
import com.project.msic.library.model.Publisher;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * A form for editing and creating a single book.
 */
public class BookForm extends Div {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6800553244222216895L;

	private final VerticalLayout content;

	private final TextField title;
	private final TextField year;
	private final Select<BookAvailability> availability;
	private final Select<Category> category;
	private final Select<Author> author;
	private final Select<Publisher> publisher;
	private Button save;
	private Button discard;
	private Button cancel;
	private final Button delete;

	private final InventoryViewPresenter presenter;
	private final Binder<Book> binder;
	private Book currentBook;

	public BookForm(InventoryViewPresenter viewPresenter) {
		setClassName(Messages.PRODUCT_FORM_CLASS);

		content = new VerticalLayout();
		content.setSizeUndefined();
		content.addClassName(Messages.PRODUCT_FORM_CONTENT_CLASS);
		add(content);

		this.presenter = viewPresenter;

		title = new TextField(Messages.TITLE_TEXT_LABEL);
		title.setWidth("100%");
		title.setRequired(true);
		title.setValueChangeMode(ValueChangeMode.EAGER);
		content.add(title);
		
		year = new TextField(Messages.YEARS_TEXT_LABEL);
		year.setWidth("100%");
		year.setRequired(true);
		year.setValueChangeMode(ValueChangeMode.EAGER);
		content.add(year);

		availability = new Select<>();
		availability.setLabel(Messages.AVAILABILITY_TEXT);
		availability.setWidth("100%");
		availability.setItems(BookAvailability.values());
		content.add(availability);

		category = new Select<>();
		category.setLabel(Messages.CATEGORY_TEXT);
		category.setWidth("100%");
		content.add(category);

		author = new Select<>();
		author.setLabel(Messages.AUTHOR_TEXT);
		author.setWidth("100%");
		content.add(author);

		publisher = new Select<>();
		publisher.setLabel(Messages.PUBLISHER_TEXT);
		publisher.setWidth("100%");
		content.add(publisher);

		binder = new BeanValidationBinder<>(Book.class);
		binder.bindInstanceFields(this);

		binder.addStatusChangeListener(event -> {
			final boolean isValid = !event.hasValidationErrors();
			final boolean hasChanges = binder.hasChanges();
			save.setEnabled(hasChanges && isValid);
			discard.setEnabled(hasChanges);
		});

		save = new Button(Messages.SAVE_BUTTON_TEXT);
		save.setWidth("100%");
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		save.addClickListener(event -> {
			if (currentBook != null && binder.writeBeanIfValid(currentBook)) {
				presenter.saveBook(currentBook);
			}
		});
		save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

		discard = new Button(Messages.DISCARD_CHANGES_BUTTON_TEXT);
		discard.setWidth("100%");
		discard.addClickListener(event -> presenter.editBook(currentBook));

		cancel = new Button(Messages.CANCEL_BUTTON_TEXT);
		cancel.setWidth("100%");
		cancel.addClickListener(event -> presenter.cancelBook());
		cancel.addClickShortcut(Key.ESCAPE);
		getElement().addEventListener("keydown", event -> presenter.cancelBook()).setFilter("event.key == 'Escape'");

		delete = new Button(Messages.DELETE_MESSAGE);
		delete.setWidth("100%");
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
		delete.addClickListener(event -> {
			if (currentBook != null) {
				presenter.deleteBook(currentBook);
			}
		});

		content.add(save, discard, delete, cancel);
	}

	public void setCategories(Collection<Category> categories) {
		category.setItems(categories);
		category.setItemLabelGenerator(Category::getName);
	}

	public void setAuthors(Collection<Author> authors) {
		author.setItems(authors);
		author.setItemLabelGenerator(Author::getName);
	}

	public void setPublishers(Collection<Publisher> publishers) {
		publisher.setItems(publishers);
		publisher.setItemLabelGenerator(Publisher::getName);
	}

	public void editBook(Book book) {
		if (book == null) {
			book = new Book();
		}
		delete.setVisible(!book.isNewBook());
		currentBook = book;
		binder.readBean(book);
	}
}
