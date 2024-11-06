package burundi.ilucky.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String apiPath;

    private String status;

    private String message;

    private Date errorTime;

}
