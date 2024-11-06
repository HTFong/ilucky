package burundi.ilucky.model.dto;

import burundi.ilucky.constants.GlobalConstant;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    public String status = GlobalConstant.STATUS_200;
    public String message = GlobalConstant.MESSAGE_200;

    public Object data;
}