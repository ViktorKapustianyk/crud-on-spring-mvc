package ua.com.viktor.crudonspringmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ua.com.viktor.crudonspringmvc.dto.TaskDTO;
import ua.com.viktor.crudonspringmvc.model.Task;
import ua.com.viktor.crudonspringmvc.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/")
@SessionAttributes("task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllTasks(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
                              @RequestParam(value = "limit", required = false, defaultValue = "10") Integer pageSize, Model model) {

        int adjustedPageNumber = pageNumber > 0 ? pageNumber - 1 : 0;

        Page<Task> taskPage = taskService.getAllTasks(adjustedPageNumber, pageSize);
        List<TaskInfo> taskInfos = taskPage.getContent().stream().map(this::toTaskInfo).collect(Collectors.toList());

        model.addAttribute("tasks", taskInfos);

        int totalPages = taskPage.getTotalPages();
        model.addAttribute("total_pages", totalPages);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute("page_numbers", pageNumbers);

        return "tasks";
    }

    @GetMapping("/count")
    public Integer getAllCount() {
        return taskService.getAllCount();
    }

    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public String editTask(@PathVariable("taskId") Integer taskId, @RequestBody @Valid TaskDTO taskDTO, Model model) throws TaskNotFoundException {
        Task taskToEdit = taskService.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        if(taskDTO.getDescription() != null){
            taskToEdit.setDescription(taskDTO.getDescription());
        }
        if(taskDTO.getStatus() != null){
            taskToEdit.setStatus(taskDTO.getStatus());
        }
        taskService.save(taskToEdit);

        return getAllTasks(0, 10, model);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTask(@RequestBody @Valid TaskDTO taskDTO, Errors errors, Model model){
        if (errors.hasErrors()) {
            return getAllTasks(null, null, model);
        }

        Task task = modelMapper.map(taskDTO, Task.class);
        taskService.save(task);

        return getAllTasks(0,10, model);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTask(@PathVariable Integer taskId, Model model){
        try {
            taskService.deleteById(taskId);
        } catch (EmptyResultDataAccessException e){}

        return getAllTasks(0,10, model);
    }
    private TaskInfo toTaskInfo(Task task) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(task.getId());
        taskInfo.setDescription(task.getDescription());
        taskInfo.setStatus(task.getStatus());
        return taskInfo;
    }
}

