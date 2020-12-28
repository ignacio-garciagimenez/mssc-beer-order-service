package guru.sfg.brewery.model.event;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBeerOrderEvent implements Serializable {

    static final long serialVersionUID = 6703826490277916847L;

    private BeerOrderDto beerOrderDto;

}
