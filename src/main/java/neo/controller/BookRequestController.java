package neo.controller;

import neo.domain.BookRequest;
import neo.repository.BookRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookRequestController {
    @Autowired
    private BookRequestRepository bookRequestRepository;

    @GetMapping("/viewBookRequest")
    public String viewBookRequest() {
        return "viewBookRequest";
    }

    @GetMapping("/makeBookRequest")
    public String makeBookRequest() {
        return "makeBookRequest";
    }

    @RequestMapping("/listBookRequest")
    @ResponseBody
    public List<BookRequest> listBookRequest() {
        return bookRequestRepository.findAll();
    }

    @RequestMapping(value = "/saveBookRequest", method = RequestMethod.POST)
    @ResponseBody
    void saveBook(@RequestBody BookRequest bookRequest) {
        bookRequestRepository.save(bookRequest);
    }
}