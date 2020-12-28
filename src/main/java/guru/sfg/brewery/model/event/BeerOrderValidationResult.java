package guru.sfg.brewery.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderValidationResult implements Serializable {

    static final long serialVersionUID = 6703826490277911847L;

    private UUID orderId;
    private Boolean isValid;
}
