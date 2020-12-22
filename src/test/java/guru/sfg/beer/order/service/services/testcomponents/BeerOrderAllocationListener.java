package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request) {

        BeerOrderDto beerOrderDto = request.getBeerOrderDto();
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, AllocateOrderResult.builder()
                .beerOrderDto(beerOrderDto)
                .allocationError(false)
                .pendingInventory(false)
                .build());
    }
}
