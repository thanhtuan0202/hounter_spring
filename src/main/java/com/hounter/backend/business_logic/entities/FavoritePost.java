package com.hounter.backend.business_logic.entities;

import com.hounter.backend.shared.part_entity.FavoritePostId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@IdClass(FavoritePostId.class)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "favorite_post")
public class FavoritePost {
    @Column(name = "create_at")
    private LocalDate createAt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;

}
