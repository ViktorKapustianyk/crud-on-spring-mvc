package ua.com.viktor.crudonspringmvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.com.viktor.crudonspringmvc.model.Task;
public interface TaskRepository extends CrudRepository<Task, Integer> {

    Page<Task> findAll(Pageable pageable);

    @Query("SELECT count(t) FROM Task t")
    Integer getAllCount();
}
