package ua.com.viktor.crudonspringmvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.viktor.crudonspringmvc.model.Task;
import ua.com.viktor.crudonspringmvc.repository.TaskRepository;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAllTasks(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return taskRepository.findAll(pageRequest);
    }
    public int getAllCount(){
        return taskRepository.getAllCount();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Optional<Task> findById(Integer taskId) {
        return taskRepository.findById(taskId);
    }

    public void deleteById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
}
