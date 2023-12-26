package com.example.ensolversspringdatajpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ensolversspringdatajpa.entities.Note;
import com.example.ensolversspringdatajpa.repository.CategoryRepository;
import com.example.ensolversspringdatajpa.repository.NoteRepository;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void deleteNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null) {
            note.getCategories().clear();
            noteRepository.delete(note);
        }
    }
}

