package com.talant.bootcamp.booksservice.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for book categories
 */	
public enum BookCategory {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    SCIENCE("Science"),
    TECHNOLOGY("Technology"),
    BUSINESS("Business"),
    SELF_HELP("Self-Help"),
    COOKING("Cooking"),
    TRAVEL("Travel"),
    CHILDREN("Children"),
    YOUNG_ADULT("Young Adult"),
    ACADEMIC("Academic"),
    REFERENCE("Reference"),
    OTHER("Other");
    
    private final String displayName;
    
    BookCategory(String displayName) {
        this.displayName = displayName;
    }
    
	/**
	 * Returns the display name of the book category.
	 *
	 * @return the display name
	 */
	@JsonValue
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
} 