package com.project.msic.library.ui.about;

import com.project.msic.library.message.Messages;
import com.project.msic.library.ui.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Version;

@Route(value = "About", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8456755104525913258L;
	
	public static final String VIEW_NAME = "About";

	public AboutView() {
		add(VaadinIcon.INFO_CIRCLE.create());
		add(new Span(Messages.ABOUT_VIEW_TEXT + Version.getFullVersion() + "."));
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setAlignItems(Alignment.CENTER);

	}
}
