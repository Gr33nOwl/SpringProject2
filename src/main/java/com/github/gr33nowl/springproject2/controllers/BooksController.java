package com.github.gr33nowl.springproject2.controllers;


import com.github.gr33nowl.springproject2.models.Book;
import com.github.gr33nowl.springproject2.models.Person;
import com.github.gr33nowl.springproject2.services.BooksService;
import com.github.gr33nowl.springproject2.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BooksService booksService;

    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String index(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name ="books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean sortByYear,
                        Model model) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear));
        else
            model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findById(id));

        Person bookOwner = booksService.getBookOwner(id);
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable int id) {
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable int id, @ModelAttribute Person person) {
        booksService.assignBook(id, person);
        return "redirect:/books/" + id;
    }

}
