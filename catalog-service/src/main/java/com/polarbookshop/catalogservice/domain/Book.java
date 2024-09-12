package com.polarbookshop.catalogservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="book")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name="isbn", nullable=false, unique=true)
    String isbn;
    @Column(name="title", nullable=false)
    String title;
    @Column(name="title", nullable=false)
    String author;
    @Column(name="title", nullable=false)
    Double price;
}
