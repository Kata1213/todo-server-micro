package com.thoughtworks.training.todotodoservice.service;

import com.thoughtworks.training.todotodoservice.model.Tag;
import com.thoughtworks.training.todotodoservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }
}
