package com.hounter.backend.business_logic.entities;

import com.hounter.backend.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;




@Entity
@Table(name = "posts")
@Indexed
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    @FullTextField
    private String title;

    @FullTextField
    @Column(name = "description", nullable = false,length = 10000)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "area", nullable = false)
    private Integer area;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone",length = 11, nullable = false)
    private String customerPhone;

    @Column(name = "note", nullable = true)
    private String notes;

    @Column(name = "status", nullable = false)
    private Status status = Status.waiting;

    @Column(name = "latitude", precision = 17, scale = 15)
    @Digits(integer=2, fraction=15)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 18, scale = 15)
    @Digits(integer=3, fraction=15)
    private BigDecimal longitude;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "expire_at")
    private LocalDate expireAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "post")
    private Set<FavoritePost> favoritePosts;

    @OneToMany(mappedBy = "post")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "post")
    private Set<PostImage> postImages;

    @OneToOne
    @JoinColumn(name="address_id", referencedColumnName = "id", nullable = false)
    private Address address;
}
