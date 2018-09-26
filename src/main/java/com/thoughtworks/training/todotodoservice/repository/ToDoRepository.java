package com.thoughtworks.training.todotodoservice.repository;

import com.thoughtworks.training.todotodoservice.model.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    Page<ToDo> findByUserId(Long userId, Pageable pageable);

    Page<ToDo> findAllByUserIdAndTags_NameInAndDueDateIsBetween(Long id, List<String> s, Date date, Date date1, Pageable pageable);

    Page<ToDo> findAllByUserIdAndDueDateIsBetween(Long id, Date date, Date date1, Pageable pageable);

    Page<ToDo> findAllByUserIdAndTags_nameIn(Long id, List<String> tag, Pageable pageable);

    Page<ToDo> findAllByUserIdAndNameContains(Long id, String name, Pageable pageable);
}
