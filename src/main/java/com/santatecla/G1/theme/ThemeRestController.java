package com.santatecla.G1.theme;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;
import com.santatecla.G1.author.Author;
import com.santatecla.G1.book.Book;
import com.santatecla.G1.book.BookService;
import com.santatecla.G1.citation.Citation;

@RestController
@RequestMapping("/api")
public class ThemeRestController {
	interface ThemeDetailView extends Theme.BasicView, Theme.BooksView, Book.BasicView {
	}

	@Autowired
	private ThemeService themeService;

	@Autowired
	private BookService bookService;

	@JsonView(Theme.BasicView.class)
	@RequestMapping(value = "/theme", method = GET)
	public Collection<Theme> themes() {
		return themeService.findAll();
	}

	@JsonView(Theme.BasicView.class)
	@RequestMapping(value = "/theme-pageable", method = RequestMethod.GET)
	public List<Theme> themePageable(Pageable page) {
		return themeService.findAll(page).getContent();
	}

	@RequestMapping(value = "/theme2", method = POST)
	public ResponseEntity<Theme> theme(@RequestBody Theme theme) {
		if (themeService.findByNameIgnoreCase(theme.getName()) == null) {
			ArrayList<Book> books = new ArrayList<>();
			for (Book book : theme.getBook()) {

				books.add(bookService.findById(book.getId()));
			}

			books.forEach((b) -> {
				System.out.println(b.getTitle());
			});

			theme.setBooks(books);
			themeService.save(theme);
			return new ResponseEntity<>(theme, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(HttpStatus.IM_USED);
	}

	@JsonView(Theme.BasicView.class)
	@RequestMapping(value = "/theme-name-pageable", method = RequestMethod.GET)
	public List<String> themePageableGuest(Pageable page) {
		List<Theme> theme = themeService.findAll(page).getContent();
		List<String> themeName = new ArrayList<>();
		for (Theme t : theme) {
			themeName.add(t.getName());
		}
		return themeName;
	}

	@JsonView(ThemeDetailView.class)
	@RequestMapping(value = "/theme", method = POST)
	public Theme theme(Model model, @RequestBody Theme theme) {
		themeService.save(theme);
		System.out.println(theme.toString());
		model.addAttribute("text", "Theme Created");
		return theme;
	}

	@JsonView(ThemeDetailView.class)
	@RequestMapping(value = "/theme/{id}", method = GET)
	public ResponseEntity<Theme> theme(Model model, @PathVariable long id) {
		Theme theme = themeService.findById(id);
		if (theme != null) {
			List<Book> books = bookService.findByTheme(theme);
			List<Author> authors = new ArrayList<>();
			List<Citation> citation = new ArrayList<>();
			for (Book b : books) {
				authors.add(b.getAuthor());
				System.out.println(b.getTitle());
				List<Citation> aux = b.getCitations();
				for (Citation c : aux) {
					citation.add(c);
				}
			}
			model.addAttribute("authors", authors);
			model.addAttribute("books", books);
			model.addAttribute("theme", theme);
			model.addAttribute("citations", citation);
			return new ResponseEntity<>(theme, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@JsonView(ThemeDetailView.class)
	@RequestMapping(value = "/theme/{id}", method = PATCH)
	public ResponseEntity<Theme> updateTheme(Model model, @RequestBody Theme newTheme, @PathVariable long id) {
		Theme oldTheme = themeService.findById(id);
		if (oldTheme != null) {
			oldTheme.update(newTheme);
			themeService.save(oldTheme);
			model.addAttribute("text", "Tema editado de forma correcta");
			return new ResponseEntity<>(oldTheme, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@JsonView(ThemeDetailView.class)
	@RequestMapping(value = "/theme/{id}", method = DELETE)
	public ResponseEntity<Theme> deleteTheme(Model model, @PathVariable long id) {
		Theme theme = themeService.findById(id);
		if (theme != null) {
			model.addAttribute("theme", theme);
			model.addAttribute("text", "Tema eliminado de forma correcta");
			themeService.deleteById(id);
			return new ResponseEntity<>(theme, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
