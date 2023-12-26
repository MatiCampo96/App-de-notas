package com.example.ensolversspringdatajpa.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.ensolversspringdatajpa.entities.Category;
import com.example.ensolversspringdatajpa.entities.Note;
import com.example.ensolversspringdatajpa.repository.CategoryRepository;
import com.example.ensolversspringdatajpa.repository.NoteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class WebController {

    //atributes
    private NoteRepository noteRepository;
    private CategoryRepository categoryRepository;

    private final Logger log = LoggerFactory.getLogger(RestController.class);

    //constructors

    public WebController(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }
    
    @GetMapping("/api/notes")
    public List<Note> findNotes(){
        return noteRepository.findAll();
    }

    @GetMapping("/api/notes/{id}")
    public ResponseEntity<Note> findNoteById(@PathVariable Long id){
        Optional<Note> noteOpt = noteRepository.findById(id);
        if(noteOpt.isPresent())
            return ResponseEntity.ok(noteOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/notes")
    public ResponseEntity<Note> create(@RequestBody Note note, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if (note.getId() != null) {

            log.warn("tryng to create a note with id");
            return ResponseEntity.badRequest().build();
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.ok(result);
    }

    @PutMapping("api/notes")
    public ResponseEntity<Note> update(@RequestBody Note note){
        if(note.getId() == null){
            log.warn("Tryng to update a non existent note");
            return ResponseEntity.badRequest().build();
        }
        if(!noteRepository.existsById(note.getId())){
            log.warn("trying to update a non existent note");
            return ResponseEntity.notFound().build();
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/notes/{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable Long id){
        if (!noteRepository.existsById(id)) {
            log.warn("Trying to delete a non existent note");
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/notes")
    public ResponseEntity<Note> deleteAllNotes(){
        log.info("REST Request for delete all notes");
        noteRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    //Categories
    @GetMapping("/api/categories")
    public List<Category> findCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/api/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id){
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if(categoryOpt.isPresent())
            return ResponseEntity.ok(categoryOpt.get());
        else
            return ResponseEntity.notFound().build();
    }
    

    @PostMapping("/api/categories")
    public ResponseEntity<Category> create(@RequestBody Category category, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if (category.getId() != null) {

            log.warn("tryng to create a category with id");
            return ResponseEntity.badRequest().build();
        }
        Category result = categoryRepository.save(category);
        return ResponseEntity.ok(result);
    }

    @PutMapping("api/categories")
    public ResponseEntity<Category> update(@RequestBody Category category){
        if(category.getId() == null){
            log.warn("Tryng to update a non existent category");
            return ResponseEntity.badRequest().build();
        }
        if(!categoryRepository.existsById(category.getId())){
            log.warn("trying to update a non existent category");
            return ResponseEntity.notFound().build();
        }
        Category result = categoryRepository.save(category);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/categories/{id}")
    public ResponseEntity<Note> deleteCategory(@PathVariable Long id){

        if (!categoryRepository.existsById(id)) {
            log.warn("Trying to delete a non existent category");
            return ResponseEntity.notFound().build();
        }

        categoryRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/categories")
    public ResponseEntity<Category> deleteAllCategories(){
        log.info("REST Request for delete all categories");
        categoryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}