package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.brewery.model.BeerOrderDto;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
    void processValidationResult(UUID orderId, boolean isValid);
    void beerOrderAllocationPassed(BeerOrderDto beerOrder);
    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);
    void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);
    void cancelOrder(UUID orderId);
    void beerOrderPickedUp(UUID id);
}
