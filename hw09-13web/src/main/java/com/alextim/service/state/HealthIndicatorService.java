package com.alextim.service.state;

import com.alextim.service.working.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthIndicatorService implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        long bookCount;
        try {
            bookCount = bookService.getCount();
        } catch (Exception e) {
            return Health.down(new RuntimeException("Database connect failed: " + e.getMessage())).build();
        }

        if(bookCount > 0)
            return Health.up().build();
        return Health.down(new RuntimeException("Library is empty")).build();
    }
}