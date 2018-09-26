package com.thoughtworks.training.todotodoservice.service;

import com.google.common.primitives.Booleans;
import com.thoughtworks.training.todotodoservice.exception.NotFoundException;
import com.thoughtworks.training.todotodoservice.model.Tag;
import com.thoughtworks.training.todotodoservice.model.ToDo;
import com.thoughtworks.training.todotodoservice.model.User;
import com.thoughtworks.training.todotodoservice.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    TagService tagService;

    public Page<ToDo> getToDoList(Pageable pageable, Optional<List<String>> tag, Optional<Date> startDate, Optional<Date> endDate) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Booleans.countTrue(startDate.isPresent(), endDate.isPresent()) == 1) {
            //throw new BadRequestException("startDate and endDate appear in same time");
            return null;
        } else {
            if (startDate.isPresent() && tag.isPresent()) {
                return toDoRepository.findAllByUserIdAndTags_NameInAndDueDateIsBetween(user.getId(), tag.get(), startDate.get(), endDate.get(), pageable);
            } else if (startDate.isPresent()) {
                return toDoRepository.findAllByUserIdAndDueDateIsBetween(user.getId(), startDate.get(), endDate.get(), pageable);
            } else if (tag.isPresent()) {
                return toDoRepository.findAllByUserIdAndTags_nameIn(user.getId(), tag.get(), pageable);
            } else {
                return toDoRepository.findByUserId(user.getId(), pageable);
            }
        }
    }

    public ToDo getToDoById(Long id) throws NotFoundException {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ToDo toDo = toDoRepository.findOne(id);
        if (toDo != null && user1.getId().equals(toDo.getUserId())) {
            return toDoRepository.findOne(id);
        }
        throw new NotFoundException();
    }


    public ToDo addToDo(ToDo toDo) {
        for (Tag tag : toDo.getTags()) {
            if (tagService.findByName(tag.getName()) == null) {
                tag.setId(tagService.save(tag).getId());
            } else {
                tag.setId(tagService.findByName(tag.getName()).getId());
            }
        }
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user1 != null && user1.getId() != null)
            toDo.setUserId(user1.getId());
        return toDoRepository.save(toDo);
    }

    public void deleteById(Long id) throws NotFoundException {
        if (toDoRepository.findOne(id) != null)
            toDoRepository.delete(id);
        else
            throw new NotFoundException();
    }

    public ToDo update(Long id, ToDo toDo) throws NotFoundException {
        if (toDoRepository.findOne(id) != null) {
            for (Tag tag : toDo.getTags()) {
                if (tagService.findByName(tag.getName()) == null) {
                    tag.setId(tagService.save(tag).getId());
                } else {
                    tag.setId(tagService.findByName(tag.getName()).getId());
                }
            }
            toDo.setUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
            toDoRepository.save(toDo);
            return toDo;
        } else {
            throw new NotFoundException();
        }

    }


    public Page<ToDo> findAllByPage(Pageable toDoPageable) {
        return toDoRepository.findAll(toDoPageable);
    }


    public Page<ToDo> findToDosByNameContains(String name, Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return toDoRepository.findAllByUserIdAndNameContains(user.getId(), name, pageable);
    }
}
