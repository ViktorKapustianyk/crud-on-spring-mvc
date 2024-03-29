package ua.com.viktor.crudonspringmvc.controller;
import lombok.*;
import ua.com.viktor.crudonspringmvc.model.Status;
@Data
public class TaskInfo {
    private Integer id;
    private String description;
    private Status status;
}
