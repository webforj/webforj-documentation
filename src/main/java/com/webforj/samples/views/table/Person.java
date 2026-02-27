package com.webforj.samples.views.table;

/**
 * Immutable data transfer object representing a person.
 * Uses Java record for immutable data modeling.
 */
public record Person(String name, int age, String city) {}
