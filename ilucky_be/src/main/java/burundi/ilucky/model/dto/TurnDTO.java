package burundi.ilucky.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TurnDTO {
    private long turnCost;
    private long perTurn;
    private long turnBuy;
}
