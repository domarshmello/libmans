package neo.controller;

import neo.domain.BookCategory;
import neo.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookCategoryController {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @GetMapping("/manageBookCategory")
    public String manageBookCategory() {
        return "manageBookCategory";
    }

    @RequestMapping("/listBookCategory")
    @ResponseBody
    public List<BookCategory> listBookCategory() {
        return bookCategoryRepository.findAll();
    }

    @RequestMapping(value = "/saveBookCategory", method = RequestMethod.POST)
    @ResponseBody
    void saveBook(@RequestBody BookCategory bookCategory) {
        bookCategoryRepository.save(bookCategory);
    }

    @RequestMapping(value = "/deleteBooCategoryk/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    void deleteBookCategory(@PathVariable("id") long id) {
        bookCategoryRepository.deleteById(id);
    }
}