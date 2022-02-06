package com.project.msic.library.ui;

import com.project.msic.library.authentication.AccessControl;
import com.project.msic.library.authentication.AccessControlFactory;
import com.project.msic.library.message.Messages;
import com.project.msic.library.ui.about.AboutView;
import com.project.msic.library.ui.inventory.InventoryView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;

/**
 * The main layout. Contains the navigation menu.
 */
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
public class MainLayout extends AppLayout implements RouterLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2462384539565482518L;

	private final Button logoutButton;

	public MainLayout() {
		// Header of the menu (the navbar)
		// menu toggle
		final DrawerToggle drawerToggle = new DrawerToggle();
		drawerToggle.addClassName(Messages.MENU_TOGGLE_CLASS_TEXT);
		addToNavbar(drawerToggle);

		// image, logo
		final HorizontalLayout top = new HorizontalLayout();
		top.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		top.setClassName(Messages.MENU_HEADER_CLASS_TEXT);

		// Note! Image resource url is resolved here as it is dependent on the
		// execution mode (development or production) and browser ES level
		// support
		final String resolvedImage = VaadinService.getCurrent().resolveResource(Messages.LOGO_PATH);

		final Image image = new Image(resolvedImage, "");
		final Label title = new Label(Messages.TITLE_LABEL);
		top.add(image, title);
		top.add(title);
		addToNavbar(top);

		// Navigation items
		addToDrawer(createMenuLink(InventoryView.class, InventoryView.VIEW_NAME, VaadinIcon.EDIT.create()));

		addToDrawer(createMenuLink(AboutView.class, AboutView.VIEW_NAME, VaadinIcon.INFO_CIRCLE.create()));

		// Create logout button but don't add it yet; admin view might be added
		// in between (see #onAttach())
		logoutButton = createMenuButton(Messages.LOGOUT_TEXT, VaadinIcon.SIGN_OUT.create());
		logoutButton.addClickListener(e -> logout());
		logoutButton.getElement().setAttribute(Messages.TITLE_ATTRIBUTE_TEXT, Messages.LOGOUT_SHORTCUT_TEXT);
	}

	private void logout() {
		AccessControlFactory.getInstance().createAccessControl().signOut();
	}

	private RouterLink createMenuLink(Class<? extends Component> viewClass, String caption, Icon icon) {
		final RouterLink routerLink = new RouterLink(null, viewClass);
		routerLink.setClassName(Messages.MENU_LINK_CLASS_TEXT);
		routerLink.add(icon);
		routerLink.add(new Span(caption));
		icon.setSize("24px");
		return routerLink;
	}

	private Button createMenuButton(String caption, Icon icon) {
		final Button routerButton = new Button(caption);
		routerButton.setClassName(Messages.MENU_BUTTON_CLASS_TEXT);
		routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		routerButton.setIcon(icon);
		icon.setSize("24px");
		return routerButton;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);

		// User can quickly activate logout with Ctrl+L
		attachEvent.getUI().addShortcutListener(this::logout, Key.KEY_L, KeyModifier.CONTROL);

		// add the admin view menu item if user has admin role
		final AccessControl accessControl = AccessControlFactory.getInstance().createAccessControl();
		if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {

			// Create extra navigation target for admins
			registerAdminViewIfApplicable(accessControl);

			// The link can only be created now, because the RouterLink checks
			// that the target is valid.
			addToDrawer(createMenuLink(AdminView.class, AdminView.VIEW_NAME, VaadinIcon.DOCTOR.create()));
		}

		// Finally, add logout button for all users
		addToDrawer(logoutButton);
	}

	private void registerAdminViewIfApplicable(AccessControl accessControl) {
		// register the admin view dynamically only for any admin user logged in
		if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)
				&& !RouteConfiguration.forSessionScope().isRouteRegistered(AdminView.class)) {
			RouteConfiguration.forSessionScope().setRoute(AdminView.VIEW_NAME, AdminView.class, MainLayout.class);
			// as logout will purge the session route registry, no need to
			// unregister the view on logout
		}
	}
}
