package ua.com.viktor.crudonspringmvc.dto;
import lombok.Data;
import ua.com.viktor.crudonspringmvc.model.Status;
@Data
public class TaskDTO {
    private String description;
    private Status status;
}
