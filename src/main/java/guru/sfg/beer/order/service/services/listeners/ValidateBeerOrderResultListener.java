package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.BeerOrderValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidateBeerOrderResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.BEER_ORDER_VALIDATION_RESULT_QUEUE)
    public void listenForValidationResult(BeerOrderValidationResult event) {
        log.debug("Processing validation result for order with id: " + event.getOrderId());

        beerOrderManager.processValidationResult(event.getOrderId(), event.getIsValid());
    }
}
