package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.event.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAllocationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE)
    public void listenForAllocationResult(AllocateOrderResult allocateOrderResult) {
        log.debug("Handling result for allocation request orderId: " + allocateOrderResult.getBeerOrderDto().getId());

        BeerOrderDto beerOrderDto = allocateOrderResult.getBeerOrderDto();
        Boolean allocationError = allocateOrderResult.getAllocationError();
        Boolean pendingInventory = allocateOrderResult.getPendingInventory();

        if (allocationError) beerOrderManager.beerOrderAllocationFailed(beerOrderDto);
        else if (!pendingInventory) beerOrderManager.beerOrderAllocationPassed(beerOrderDto);
        else beerOrderManager.beerOrderAllocationPendingInventory(beerOrderDto);
    }
}
