package csonic.lab01.demo01.service;

import csonic.lab01.demo01.dto.OrderDto;

public interface OrderService {
    void send(final String messagePayload) ;

    void sendOrder(final OrderDto messagePayload) ;
}
