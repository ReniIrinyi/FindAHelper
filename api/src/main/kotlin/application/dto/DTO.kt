package application.dto

interface DTO <T> {
    /**
     * Converts this DTO to the domain model for use in business logic or persistence.
     */
    fun toModel():T

    /**
     * Creates a DTO from a domain model
     * Used for preparing data for output (e.g., API responses).
     */
    companion object{}
}