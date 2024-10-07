package jpabook.jpashop.converter;

import jakarta.persistence.Converter;
import jpabook.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.domain.OrderStatus;

@Converter(autoApply = true)
public class DeliveryStatusConverter extends EnumConverter<DeliveryStatus> {
    public DeliveryStatusConverter() {
        super(DeliveryStatus.class);  // OrderStatus 클래스를 EnumConverter에 전달
    }
}