package com.hounter.backend.shared.part_entity;

import com.hounter.backend.business_logic.entities.Customer;
import com.hounter.backend.business_logic.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class FavoritePostId implements Serializable {
    private Customer customer;
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritePostId that = (FavoritePostId) o;
        return Objects.equals(customer, that.customer) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, post);
    }
}
