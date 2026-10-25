package com.yogesh.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads JSON test data from the classpath for data-driven tests. Keeping data out of the
 * test code lets non-authors extend coverage by editing JSON, and keeps scenarios readable.
 */
public final class TestDataReader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TestDataReader() {
    }

    /** Returns each element of a top-level JSON array as an {@code Object[]} row for a TestNG DataProvider. */
    public static Object[][] readArray(String classpathResource, String... fields) {
        try (InputStream in = TestDataReader.class.getClassLoader()
                .getResourceAsStream(classpathResource)) {
            if (in == null) {
                throw new IllegalStateException("Test data not found: " + classpathResource);
            }
            JsonNode root = MAPPER.readTree(in);
            List<Object[]> rows = new ArrayList<>();
            for (JsonNode node : root) {
                Object[] row = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    row[i] = node.get(fields[i]).asInt();
                }
                rows.add(row);
            }
            return rows.toArray(new Object[0][]);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read test data: " + classpathResource, e);
        }
    }
}
