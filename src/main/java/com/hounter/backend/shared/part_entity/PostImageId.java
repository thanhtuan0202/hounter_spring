package com.hounter.backend.shared.part_entity;

import com.hounter.backend.business_logic.entities.Post;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class PostImageId implements Serializable {
    private String imageUrl;
    private Post post;

    public PostImageId(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostImageId that = (PostImageId) o;
        return Objects.equals(imageUrl, that.imageUrl) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, post);
    }
}
