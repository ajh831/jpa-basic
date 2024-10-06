package jpabook.jpashop.converter;

import jakarta.persistence.Converter;
import jpabook.jpashop.domain.OrderStatus;

@Converter(autoApply = true)
public class OrderStatusConverter extends EnumConverter<OrderStatus> {
    public OrderStatusConverter() {
        super(OrderStatus.class);  // OrderStatus 클래스를 EnumConverter에 전달
    }
}