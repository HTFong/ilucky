package burundi.ilucky.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "Username can not be a null or empty")
    private String username;
    @NotEmpty(message = "Password can not be a null or empty")

    private String password;
}
