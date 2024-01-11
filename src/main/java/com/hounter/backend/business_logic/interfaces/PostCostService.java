package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.PostCost;

public interface PostCostService {
    public void enrollPostToCost(Long post_id, Long cost_id, Integer days);
    public PostCost enrollPostToCost(Post post, Long cost_id, Integer days);
    public PostCost getLastCostOfPost(Post post);
}
