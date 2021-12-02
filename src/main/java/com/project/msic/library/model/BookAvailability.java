package com.project.msic.library.model;

public enum BookAvailability {
	AVAILABLE("Available"), UNAVAILABLE("Unavailable");

	private final String name;

	private BookAvailability(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return name;
	}
}
