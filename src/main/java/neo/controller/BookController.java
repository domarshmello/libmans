package neo.controller;

import neo.domain.Book;
import neo.domain.IsbnData;
import neo.repository.BookRepository;
import neo.util.IsbnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/viewBook")
    public String viewPage() {
        return "viewBook";
    }

    @GetMapping("/manageBook")
    public String manageBook() {
        return "manageBook";
    }

    @RequestMapping("/listBook")
    @ResponseBody
    public List<Book> listBook() {
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    @ResponseBody
    void saveBook(@RequestBody Book book) {
        bookRepository.save(book);
    }

    @RequestMapping(value = "/deleteBook/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    void deleteBook(@PathVariable("id") long id) {
        bookRepository.deleteById(id);
    }

    @RequestMapping(value = "/fetchIsbn/{isbn}", method = RequestMethod.GET)
    @ResponseBody
    IsbnData fetchIsbn(@PathVariable("isbn") String isbn) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return IsbnUtil.fetch(isbn);
    }
}