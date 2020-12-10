package guru.sfg.beer.order.service.statemachine.actions;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManagerImpl;
import guru.sfg.beer.order.service.web.mappers.BeerOrderMapper;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateBeerOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class ValidateBeerOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        UUID beerOrderId = UUID.class.cast(stateContext.getMessage().getHeaders()
                .get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER));
        BeerOrder beerOrder = beerOrderRepository.getOne(beerOrderId);
        BeerOrderDto dto = beerOrderMapper.beerOrderToDto(beerOrder);

        if (beerOrderId == null) return; //todo add some fallback logic

        ValidateBeerOrderEvent msg = ValidateBeerOrderEvent.builder()
                .beerOrderDto(dto)
                .build();

        jmsTemplate.convertAndSend(JmsConfig.INVENTORY_VALIDATION_QUEUE, msg);

        log.debug("Sent validation event to queue for order id:" + beerOrderId);

    }
}
