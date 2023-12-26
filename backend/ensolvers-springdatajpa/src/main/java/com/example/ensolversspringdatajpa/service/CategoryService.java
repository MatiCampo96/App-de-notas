package com.example.ensolversspringdatajpa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ensolversspringdatajpa.entities.Category;
import com.example.ensolversspringdatajpa.repository.CategoryRepository;
import com.example.ensolversspringdatajpa.repository.NoteRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoteRepository noteRepository;

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.getNotes().clear();
            categoryRepository.delete(category);
        }
    }
}