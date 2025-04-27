package app.aspect;

import app.Application;
import app.dto.BookDto;
import app.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("test")
public class CountingAspectTest {

    @Autowired
    private CountingAspect countingAspect;

    @Autowired
    private BookService bookService;

    @BeforeEach
    public void setup() {
        countingAspect.resetCount();
    }

    @Test
    public void testAspectCounterIncrements() {
        assertEquals(0, countingAspect.getCount());

        BookDto bookDto = bookService.findById(1L);
        assertNotNull(bookDto);

        assertEquals(2, countingAspect.getCount());
    }
} 