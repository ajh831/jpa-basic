package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @Embedded
    private Address address;

/*    실제로 아래의 양방향 코드는 사용하지 않는 것이 좋음
    필요한 경우 멤버를 조회하고 오더를 따로 조회하는 것이 맞음!!*/
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    private void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) && Objects.equals(getName(), member.getName()) && Objects.equals(getOrders(), member.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOrders());
    }
}
