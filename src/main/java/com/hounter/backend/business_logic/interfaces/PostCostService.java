package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;

public interface PostCostService {
    void updatePostCost(PostCost postCost, Long costId, Integer days);
    void enrollPostToCost(Long post_id, Long cost_id, Integer days);
    PostCost enrollPostToCost(Post post, String cost_id, Integer days);
    PostCost findByPost(Post post);
}
