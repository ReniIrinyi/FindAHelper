package application.dto

interface DTO <T> {
    /**
     * Converts this DTO to the domain model (`ToiletModel`) for use in business logic or persistence.
     */
    fun toModel():T

    /**
     * Creates a `ToiletDTO` from a domain model (`ToiletModel`).
     * Used for preparing data for output (e.g., API responses).
     */
    companion object{}
}