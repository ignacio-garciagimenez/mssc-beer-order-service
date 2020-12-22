package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.brewery.model.events.BeerOrderValidationResult;
import guru.sfg.brewery.model.events.ValidateBeerOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_BEER_ORDER_QUEUE)
    public void listen(Message msg) {

        ValidateBeerOrderEvent request = (ValidateBeerOrderEvent) msg.getPayload();


        jmsTemplate.convertAndSend(JmsConfig.BEER_ORDER_VALIDATION_RESULT_QUEUE,
                BeerOrderValidationResult.builder()
                        .isValid(true)
                        .orderId(request.getBeerOrderDto().getId())
                        .build());

    }
}
