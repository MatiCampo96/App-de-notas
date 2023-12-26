package com.example.ensolversspringdatajpa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ensolversspringdatajpa.entities.Category;
import com.example.ensolversspringdatajpa.entities.Note;
import com.example.ensolversspringdatajpa.repository.CategoryRepository;
import com.example.ensolversspringdatajpa.repository.NoteRepository;

@RestController
public class NoteCategoryController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/api/associate")
public ResponseEntity<String> associateNoteWithCategory(@RequestBody Map<String, Long> params) {
    Long noteId = params.get("noteId");
    Long categoryId = params.get("categoryId");

    Note note = noteRepository.findById(noteId).orElse(null);
    Category category = categoryRepository.findById(categoryId).orElse(null);

    if (note == null || category == null) {
        return ResponseEntity.badRequest().body("Note or Category not found");
    }

    note.getCategories().add(category);
    noteRepository.save(note);

    return ResponseEntity.ok("Association created successfully");
}
}
