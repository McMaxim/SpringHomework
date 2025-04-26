package app.controllet;

import app.controller.BookController;
import app.dto.BookDto;
import app.exception.BookNotFoundException;
import app.model.BookEntity;
import app.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = BookController.class,
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class}
)
public class BookControllerMVCTest {
    private static final String basePath = "/api/books";
    private static final MediaType jsonContentType = MediaType.valueOf("application/json");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;


    @Test
    public void testGetBookSuccess() throws Exception {
        BookDto bookDto = new BookDto(1L, "test", "test", "test");
        when(bookService.findById(1L)).thenReturn(bookDto);

        mockMvc.perform(get(basePath + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.author").value("test"))
                .andExpect(jsonPath("$.publisher").value("test"));
        verify(bookService).findById(1L);
    }

    @Test
    public void testDeleteBookSuccess() throws Exception {
        BookDto bookDto = new BookDto(1L, "test", "test", "test");

        when(bookService.delete(1L)).thenReturn(bookDto);

        mockMvc.perform(delete(basePath + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.author").value("test"))
                .andExpect(jsonPath("$.publisher").value("test"));
        verify(bookService).delete(1L);
    }

    @Test
    public void testPatchBookFail() throws Exception {
        BookDto bookDto = BookDto.builder().title("test").build();
        when(bookService.update(bookDto)).thenThrow(BookNotFoundException.class);
        mockMvc.perform(patch(basePath + "/1")).andExpect(status().is4xxClientError());
    }
}