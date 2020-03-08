package neo.controller;

import neo.domain.Book;
import neo.domain.Rental;
import neo.domain.User;
import neo.repository.BookRepository;
import neo.repository.RentalRepository;
import neo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RentalController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/manageRental")
    public String manageRental() {
        return "manageRental";
    }

    @GetMapping("/rentalHistory")
    public String rentalHistory() {
        return "rentalHistory";
    }

    @RequestMapping("/listRental")
    @ResponseBody
    public List<Rental> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .anyMatch(item -> "ROLE_ADMIN".equals(item.getAuthority()))) {
            return rentalRepository.findAll();
        } else {
            User user = userRepository.findByUsername(authentication.getName());
            return rentalRepository.findByUser_Id(user.getId());
        }
    }

    @RequestMapping("/listRental/{userId}")
    @ResponseBody
    public List<Rental> listByUser(@PathVariable("userId") long userId,
                                   @RequestParam(value = "status", required = false) String status) {
        if (status == null) {
            return rentalRepository.findByUser_Id(userId);
        } else {
            return rentalRepository.findByUser_IdAndStatus(userId, status);
        }
    }

    @RequestMapping(value = "/makeRental", method = RequestMethod.POST)
    @ResponseBody
    public void rentBook(@RequestBody Rental rental) {
        rental.setStatus("未归还");
        rental.setRentDate(simpleDateFormat.format(new Date()));
        rentalRepository.save(rental);

        Book book = bookRepository.findById(rental.getBook().getId()).get();
        book.setRemainingQuantity(book.getRemainingQuantity() - 1);
        bookRepository.save(book);
    }

    @RequestMapping(value = "/returnRental/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void returnBook(@PathVariable("id") long id) {
        Rental rental = rentalRepository.findById(id).get();
        rental.setStatus("已归还");
        rental.setReturnDate(simpleDateFormat.format(new Date()));
        rentalRepository.save(rental);

        Book book = bookRepository.findById(rental.getBook().getId()).get();
        book.setRemainingQuantity(book.getRemainingQuantity() + 1);
        bookRepository.save(book);
    }
}