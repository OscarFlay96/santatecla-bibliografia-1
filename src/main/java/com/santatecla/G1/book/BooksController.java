package com.santatecla.G1.book;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BooksController {

	@Autowired
	private BookRepository repository;
	
	public Collection<Book> books(){
		return repository.findAll();
	}
	
	@RequestMapping("/book/{id}")
	public String Book(Model model, @PathVariable long id) {
		Optional<Book> book = repository.findById(id);
		System.out.println(book.toString());
		if (book!=null) {
			model.addAttribute("book", book.get());
		}
		return "booksPage";
	}
	
	@RequestMapping("/newBook")
	public String newBook(Model model, Book book) {
		//repository.save(book);
		return "booksPageEdit";
	}

	//@RequestMapping("/newBook/uploaded")
	//public String newBookUploaded(Model model,@RequestParam("title") String title,@RequestParam("date") String date,@RequestParam("editorial") String editorial, @RequestParam("urlEditorial") String urlEdit, @RequestParam("file") MultipartFile file) {
		//int imageId = com.santatecla.G1.ImageManagerController.getNewId();
		//Guardar imagen
		//com.santatecla.G1.ImageManagerController.handleFileUpload(model, file, imageId);
		//Crear y guardar book
		//Author a = new Author(name, bornDate, deathDate, imageId);
		//repository.save(a);
		//model.addAttribute(a);
		//return "/author/{id}";
	//}
}
