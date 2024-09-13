package com.polarbookshop.catalogservice.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Table(name="book")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Book{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id", nullable = false)
        Integer id;

        @Column(name="isbn", nullable = false,unique = true)
        String isbn;
        @Column(name="title", nullable = false)
        String title;
        @Column(name="author", nullable = false)
        String author;
        @Column(name="price", nullable = false)
        Double price;
        @CreatedDate
        Instant createdDate;
        @LastModifiedDate
        Instant lastModifiedDate;
        @Version
        Integer version;
}
