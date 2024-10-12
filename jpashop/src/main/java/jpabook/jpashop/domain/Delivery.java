package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.converter.DeliveryStatusConverter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "DELEIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Convert(converter = DeliveryStatusConverter.class)
    private DeliveryStatus status;

}
