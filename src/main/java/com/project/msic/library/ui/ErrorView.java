package com.project.msic.library.ui;

import javax.servlet.http.HttpServletResponse;

import com.project.msic.library.message.Messages;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.ParentLayout;

/**
 * View shown when trying to navigate to a view that does not exist using
 */
@ParentLayout(MainLayout.class)
public class ErrorView extends VerticalLayout implements HasErrorParameter<NotFoundException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1092143965398957206L;

	private Span explanation;

	public ErrorView() {
		H1 header = new H1(Messages.VIEW_NOT_FOUND_ERROR_MESSAGE);
		add(header);

		explanation = new Span();
		add(explanation);
	}

	@Override
	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
		explanation.setText("Could not navigate to '" + event.getLocation().getPath() + "'.");
		return HttpServletResponse.SC_NOT_FOUND;
	}
}
