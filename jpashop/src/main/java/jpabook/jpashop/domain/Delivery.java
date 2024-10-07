package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.converter.DeliveryStatusConverter;

@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELEIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    private String city;
    private String street;
    private String zipcode;

    @Convert(converter = DeliveryStatusConverter.class)
    private DeliveryStatus status;


}
