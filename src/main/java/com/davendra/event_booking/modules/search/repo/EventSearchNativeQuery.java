package com.davendra.event_booking.modules.search.repo;

/**
 * Native SQL fragments for event search (PostgreSQL).
 */
public final class EventSearchNativeQuery {

    public static final String FROM_AND_WHERE = """
            FROM events e
            LEFT JOIN venues v ON v.id = e.venue_id
            WHERE (e.active = true or e.active is null)
              AND (CAST(:title AS text) IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', CAST(:title AS text), '%')))
              AND (CAST(:language AS text) IS NULL OR LOWER(e.language) LIKE LOWER(CONCAT('%', CAST(:language AS text), '%')))
              AND (CAST(:genre AS text) IS NULL OR LOWER(e.genre) LIKE LOWER(CONCAT('%', CAST(:genre AS text), '%')))
              AND (CAST(:location AS text) IS NULL OR (
                    LOWER(v.city) LIKE LOWER(CONCAT('%', CAST(:location AS text), '%'))
                 OR LOWER(v.state) LIKE LOWER(CONCAT('%', CAST(:location AS text), '%'))
                 OR LOWER(v.country) LIKE LOWER(CONCAT('%', CAST(:location AS text), '%'))
              ))
              AND (CAST(:venue AS text) IS NULL OR LOWER(v.name) LIKE LOWER(CONCAT('%', CAST(:venue AS text), '%')))
              AND (CAST(:searchDate AS date) IS NULL OR EXISTS (
                    SELECT 1 FROM shows s
                    WHERE s.event_id = e.id
                      AND s.start_time >= CAST(:searchDate AS timestamp)
                      AND s.start_time < CAST(:searchDate AS timestamp) + INTERVAL '1 day'
              ))
            """;

    public static final String SELECT_EVENTS = "SELECT e.* " + FROM_AND_WHERE;

    public static final String COUNT_EVENTS = "SELECT COUNT(DISTINCT e.id) " + FROM_AND_WHERE;

    private EventSearchNativeQuery() {
    }
}
