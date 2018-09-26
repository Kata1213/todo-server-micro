package com.thoughtworks.training.todotodoservice.controller;

import com.thoughtworks.training.todotodoservice.exception.NotFoundException;
import com.thoughtworks.training.todotodoservice.model.ToDo;
import com.thoughtworks.training.todotodoservice.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping
    public Page<ToDo> getAllToDoItems(Pageable pageable,
                                      @RequestParam(value = "tag", required = false) List<String> tag,
                                      @RequestParam(value = "from", required = false) Date from,
                                      @RequestParam(value = "to", required = false) Date to) {
        return toDoService.getToDoList(pageable, Optional.ofNullable(tag), Optional.ofNullable(from), Optional.ofNullable(to));
    }

    @GetMapping(value = "/search/{name}")
    public Page<ToDo> getToDosByName(Pageable pageable, @PathVariable String name) {
        return toDoService.findToDosByNameContains(name, pageable);
    }

    @GetMapping(value = "/{id}")
    public ToDo getToDoById(@PathVariable Long id) throws NotFoundException {
        return toDoService.getToDoById(id);
    }

    @PostMapping
    public ToDo addToDo(@RequestBody ToDo toDo) {
        return toDoService.addToDo(toDo);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id) throws NotFoundException {
        toDoService.deleteById(id);
        return "delete successfully!";
    }

    @PutMapping
    public ToDo update(@RequestBody ToDo toDo) throws NotFoundException {
        return toDoService.update(toDo.getId(), toDo);
    }


}
