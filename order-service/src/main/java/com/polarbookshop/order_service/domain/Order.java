package com.polarbookshop.order_service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table(name="orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Order{
    @Id
    Integer id;
    String bookIsbn;
    String bookName;
    Double bookPrice;
    Integer quantity;
    OrderStatus status = OrderStatus.REJECTED;
    @CreatedBy
    Instant createdDate;
    @LastModifiedDate
    Instant lastModifiedDate;
    @Version
    Integer version;
}
