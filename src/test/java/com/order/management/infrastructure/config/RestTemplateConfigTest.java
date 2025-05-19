package com.order.management.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RestTemplateConfigTest {

    @Test
    void testRestTemplateCreation() {

        RestTemplateConfig config = new RestTemplateConfig();
        RestTemplate restTemplate = config.restTemplate();
        assertNotNull(restTemplate, "RestTemplate should not be null");
    }

    @Test
    void testRetryTemplateCreation() {

        RestTemplateConfig config = new RestTemplateConfig();
        RetryTemplate retryTemplate = config.retryTemplate();
        assertNotNull(retryTemplate, "RetryTemplate should not be null");
    }

    @Test
    void testRetryTemplateExecutionWithFailure() {

        RestTemplateConfig config = new RestTemplateConfig();
        RetryTemplate retryTemplate = config.retryTemplate();
        final int[] attemptCount = {0};

        Exception exception = assertThrows(RuntimeException.class, () -> {
            retryTemplate.execute((RetryCallback<Object, RuntimeException>) context -> {
                attemptCount[0]++;
                throw new RuntimeException("Test exception");
            });
        });

        assertEquals("Test exception", exception.getMessage());
        assertEquals(3, attemptCount[0], "Should have attempted 3 times based on configuration");
    }

    @Test
    void testRetryTemplateExecutionWithSuccess() {

        RestTemplateConfig config = new RestTemplateConfig();
        RetryTemplate retryTemplate = config.retryTemplate();
        final int[] attemptCount = {0};

        String result = retryTemplate.execute((RetryCallback<String, RuntimeException>) context -> {
            attemptCount[0]++;
            return "Success";
        });

        assertEquals("Success", result);
        assertEquals(1, attemptCount[0], "Should have attempted only once");
    }

    @Test
    void testRetryTemplateExecutionWithEventualSuccess() {

        RestTemplateConfig config = new RestTemplateConfig();
        RetryTemplate retryTemplate = config.retryTemplate();
        final int[] attemptCount = {0};

        String result = retryTemplate.execute((RetryCallback<String, RuntimeException>) context -> {
            attemptCount[0]++;
            if (attemptCount[0] < 3) {
                throw new RuntimeException("Temporary failure");
            }
            return "Success after retries";
        });

        assertEquals("Success after retries", result);
        assertEquals(3, attemptCount[0], "Should have succeeded on the 3rd attempt");
    }

    @Test
    void testRetryTemplateBackoffPeriod() {

        RestTemplateConfig config = new RestTemplateConfig();
        RetryTemplate retryTemplate = config.retryTemplate();
        final int[] attemptCount = {0};
        final long[] executionTimes = new long[2];

        assertThrows(RuntimeException.class, () -> {
            retryTemplate.execute((RetryCallback<Object, RuntimeException>) context -> {
                attemptCount[0]++;
                executionTimes[attemptCount[0] - 1] = System.currentTimeMillis();
                if (attemptCount[0] <= 2) {
                    throw new RuntimeException("Temporary failure");
                }
                return null;
            });
        });

        long timeDifference = executionTimes[1] - executionTimes[0];
        assertTrue(timeDifference >= 900, "Backoff period should be approximately 1000ms");
    }
}