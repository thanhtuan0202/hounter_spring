package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.CustomerDTO.PostCostRes;
import com.hounter.backend.application.DTO.CustomerDTO.PostOfUserRes;
import com.hounter.backend.application.DTO.FeedbackDto.CreateFeedback;
import com.hounter.backend.application.DTO.FeedbackDto.FeedbackResponse;
import com.hounter.backend.application.DTO.PostDto.*;
import com.hounter.backend.business_logic.entities.*;
import com.hounter.backend.business_logic.interfaces.FeedbackService;
import com.hounter.backend.business_logic.interfaces.PostCostService;
import com.hounter.backend.business_logic.interfaces.PostImageService;
import com.hounter.backend.business_logic.interfaces.PostService;
import com.hounter.backend.business_logic.mapper.PostMapping;
import com.hounter.backend.data_access.repositories.CategoryRepository;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.data_access.repositories.PostRepository;
import com.hounter.backend.shared.enums.Status;
import com.hounter.backend.shared.exceptions.CategoryNotFoundException;
import com.hounter.backend.shared.exceptions.ForbiddenException;
import com.hounter.backend.shared.exceptions.PostNotFoundException;

import com.hounter.backend.shared.utils.FindPointMapbox;
import com.hounter.backend.shared.utils.FindPointsAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostImageService postImageService;

    @Autowired
    private PostCostService postCostService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FindPointMapbox findPointMapbox;
    @PersistenceContext
    protected EntityManager em;

    @Override
    public PostResponse getPostById(Long postId, boolean isUser) {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if(isUser && post.getStatus() == Status.waiting){
                return null;
            }
            PostResponse response = PostMapping.PostResponseMapping(post);
            response.setCost(this.postCostService.findByPost(post).getCost().getId());
            return response;
        }
        throw new PostNotFoundException("Cannot find post with id = " + postId);
    }

    private List<ShortPostResponse> mapListOfPost(List<Post> posts) {
        List<ShortPostResponse> responses = new ArrayList<ShortPostResponse>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                responses.add(PostMapping.getShortPostResponse(post, this.postCostService.findByPost(post)));
            }
            return responses;
        } else {
            return null;
        }
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            Status status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByStatus(status, pageable);
        return mapListOfPost(posts);
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Post> posts = this.postRepository.findAll(pageable);

        List<ShortPostResponse> responses = new ArrayList<ShortPostResponse>();
        if (!posts.isEmpty()) {
            List<Post> postList = posts.getContent();
            for (Post post : postList) {
                responses.add(PostMapping.getShortPostResponse(post,this.postCostService.findByPost(post)));
            }
            return responses;
        } else {
            return null;
        }
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            Customer customer) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByCustomer(customer, pageable);
        return mapListOfPost(posts);
    }

    @Override
    public List<ShortPostResponse> getAllPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            Customer customer, Status status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        List<Post> posts = this.postRepository.findByCustomerAndStatus(customer, status, pageable);
        return mapListOfPost(posts);
    }

    @Override
    public Post findPostById(Long postId){
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isPresent()){
            return post.get();
        }
        return null;
    }
    @Override
    public List<ShortPostResponse> filterPostForUser(Integer pageSize,Integer pageNo,
                                                     Customer customer,String category_name,String cost,
                                                     String startDate,String endDate, Status status){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Root<Post> post = cq.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();
        if(customer != null) predicates.add(cb.equal(post.get("customer"), customer));
        if (!Objects.equals(category_name, "")){
            Category category = this.categoryRepository.findByName(category_name);
            predicates.add(cb.equal(post.get("category"), category));
        }
        if(!Objects.equals(startDate, "")) {
            predicates.add(cb.greaterThanOrEqualTo(post.get("createAt"), LocalDate.parse(startDate, formatter)));
        }
        if(!Objects.equals(endDate, "")) {
            predicates.add(cb.lessThanOrEqualTo(post.get("createAt"), LocalDate.parse(endDate, formatter)));
        }
        if(status != null) predicates.add(cb.equal(post.get("status"), status));
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(post.get("createAt")));
        List<Post> posts = em.createQuery(cq).setMaxResults(pageSize)
                .setFirstResult(pageNo * pageSize)
                .getResultList();
        if (posts != null) {
            if(!Objects.equals(cost, "")){
                List<ShortPostResponse> responses = new ArrayList<>();
                for (Post item : posts){
                    if(this.postCostService.findByPost(item).getCost().getName().equals(cost)){
                        responses.add(PostMapping.getShortPostResponse(item,this.postCostService.findByPost(item)));
                    }
                }
                return responses;
            }
            return this.mapListOfPost(posts);
        }
        return null;
    }
    @Override
    public List<FeedbackResponse> getPostFeedback(Integer pageSize, Integer pageNo, Long postId) {
        return this.feedbackService.getFeedbackByPost(postId,pageSize, pageNo, "createAt", "desc");
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public PostResponse createPost(CreatePostDto createPostDTO, Long userId) throws Exception {
        Post post = PostMapping.createPostMapping(createPostDTO);
        Optional<Customer> op_customer = this.customerRepository.findById(userId);
        if (op_customer.isPresent()) {
            post.setCustomer(op_customer.get());
        } else {
            throw new Exception("No customer found!");
        }
        Category category = this.categoryRepository.findByName(createPostDTO.getCategory());
        if (category != null) {
            post.setCategory(category);
        } else {
            throw new CategoryNotFoundException("Cannot find category with name " + createPostDTO.getCategory());
        }
        FindPointsAddress.LatLng latLng = this.findPointMapbox.getAddressPoints(createPostDTO.getFullAddress());
        post.setLatitude(BigDecimal.valueOf(latLng.getLat()));
        post.setLongitude(BigDecimal.valueOf(latLng.getLng()));
        Post saved_post = this.postRepository.save(post);
        PostCost postCost = this.postCostService.enrollPostToCost(saved_post, createPostDTO.getCost(),
                createPostDTO.getDays());

        this.paymentService.savePaymentOfPost(postCost, userId);
        this.postImageService.storeImageOfPost(saved_post, createPostDTO.getImageUrls());
        return PostMapping.PostResponseMapping1(saved_post);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public PostResponse updatePost(Long postId, UpdatePostDTO updatePostDTO, Long userId) throws Exception {
        Optional<Post> op_post = this.postRepository.findById(postId);
        if (op_post.isPresent()) {
            Post post = op_post.get();
            if (!Objects.equals(post.getCustomer().getId(), userId)) {
                throw new ForbiddenException("Forbidden", HttpStatus.FORBIDDEN);
            } else {
                // update data for post
                post.setTitle(updatePostDTO.getTitle());
                post.setDescription(updatePostDTO.getDescription());
                post.setPrice(updatePostDTO.getPrice());
                post.setCity(updatePostDTO.getCity());
                post.setCounty(updatePostDTO.getCounty());
                post.setDistrict(updatePostDTO.getDistrict());
                post.setUpdateAt(LocalDate.now());
                post.setFullAdress(updatePostDTO.getFullAddress());
                Category category = this.categoryRepository.findByName(updatePostDTO.getCategory());
                if (category != null) {
                    post.setCategory(category);
                } else {
                    throw new CategoryNotFoundException("Cannot find category with name " + updatePostDTO.getCategory());
                }
                PostCost postCost = this.postCostService.findByPost(post);
                this.postCostService.updatePostCost(postCost, updatePostDTO.getCost(), updatePostDTO.getDays());
                Payment payment = this.paymentService.getPaymentByPostNum(postId);
                this.paymentService.savePaymentOfPost(payment, postCost);
                this.postImageService.storeImageOfPost(post, updatePostDTO.getAddImages());
                this.postImageService.deleteImageOfPost(post, updatePostDTO.getDeleteImages());
                FindPointsAddress.LatLng latLng = this.findPointMapbox.getAddressPoints(updatePostDTO.getFullAddress());
                post.setLatitude(BigDecimal.valueOf(latLng.getLat()));
                post.setLongitude(BigDecimal.valueOf(latLng.getLng()));
                Post saved_post = this.postRepository.save(post);
                return PostMapping.PostResponseMapping(saved_post);
            }
        } else {
            throw new PostNotFoundException("Cannot find post with id " + postId);
        }
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public PostResponse deletePost(Long postId, Long userId) {
        Optional<Post> op_post = this.postRepository.findById(postId);
        if (op_post.isPresent()) {
            Post post = op_post.get();
            if (!Objects.equals(post.getCustomer().getId(), userId)) {
                throw new ForbiddenException("Forbidden", HttpStatus.FORBIDDEN);
            } else {
                post.setStatus(Status.delete);
                Post saved = this.postRepository.save(post);
                return PostMapping.PostResponseMapping(saved);
            }
        } else {
            throw new PostNotFoundException("Cannot find post with id " + postId);
        }
    }

    @Override
    public FeedbackResponse createNewFeedback(CreateFeedback createFeedback, Long postId, Long userId) {
        return this.feedbackService.createFeedback(createFeedback,postId,userId);
    }

    @Override
    public List<ShortPostResponse> filterPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir,
            FilterPostDto filter) {
        Optional<Category> category = this.categoryRepository.findById(filter.getCategory());
        if (category.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);

        Root<Post> post = cq.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getCity() != null) {
            predicates.add(cb.equal(post.get("city"), filter.getCity()));
        }

        if (filter.getCounty() != null) {
            predicates.add(cb.equal(post.get("county"), filter.getCounty()));
        }
        if (filter.getDistrict() != null) {
            predicates.add(cb.equal(post.get("district"), filter.getDistrict()));
        }
        if (filter.getUpperPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(post.get("price"), filter.getUpperPrice()));
        }
        if (filter.getLowerPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(post.get("price"), filter.getLowerPrice()));
        }
        if (filter.getCategory() != null) {
            predicates.add(cb.equal(post.get("category"), category.get()));
        }
        if (filter.getStatus() != null) {
            predicates.add(cb.equal(post.get("status"), filter.getStatus()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(post.get("createAt")));
        List<Post> posts = em.createQuery(cq).setMaxResults(pageSize)
                .setFirstResult(pageNo * pageSize)
                .getResultList();
        if (posts != null) {
            return this.mapListOfPost(posts);
        }
        return null;
    }
    @Override
    public List<ShortPostResponse> searchOnMap(float latitude, float longitude, Integer pageSize, Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString("desc"), "create_at"));
        List<Post> postResult = this.postRepository.findPostNearYou(latitude,longitude,pageable);
        log.info("Enter search on map and return " + postResult.size() + " row");
        List<ShortPostResponse> responses = new ArrayList<ShortPostResponse>();
        for(Post post : postResult){
            responses.add(PostMapping.getShortPostResponse(post,this.postCostService.findByPost(post)));
        }
        return responses;
    }

    @Override
    public List<ShortPostResponse> searchPost(Integer pageSize, Integer pageNo, String sortBy, String sortDir, String q){
        return null;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public boolean changeStatusPost(Long postId, Long userId, ChangeStatusDto changeStatus, boolean isAdmin)
            throws Exception {
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isAdmin) {
                return true;
            }
            if (!Objects.equals(post.getCustomer().getId(), userId)) {
                throw new ForbiddenException("You are not allowed to change the status of a post with id " + postId,
                        HttpStatus.FORBIDDEN);
            } else {
                if (post.getStatus().equals(Status.delete)) {
                    return false;
                } else {
                    for (Status item : Status.values()) {
                        if (changeStatus.getStatus().equals(item.toString())) {
                            post.setStatus(item);
                            return true;
                        }
                    }
                }
            }

        } else {
            throw new PostNotFoundException("Cannot find post with id " + postId);
        }
        return false;
    }
    @Override
    @Transactional(rollbackFor = { Exception.class })
    public boolean changeStatus_Staff(Long postId, String status, Long staffId){
        Optional<Post> optionalPost = this.postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getStatus().equals(Status.waiting)) {
                PostCost postCost = this.postCostService.findByPost(post);
                if(status.equals(Status.active.toString())) {
                    post.setStatus(Status.active);
                    post.setUpdateAt(LocalDate.now());
                    post.setExpireAt(LocalDate.now().plusDays(postCost.getActiveDays()));
                }
                else if(status.equals(Status.delete.toString())){
                    post.setStatus(Status.delete);
                }
                else{
                    return false;
                }
                this.postRepository.save(post);
                return true;
            } else {
                return false;
            }
        } else {
            throw new PostNotFoundException("Cannot find post with id " + postId);
        }
    }

    @Override
    public PostOfUserRes getPostDetailOfUser(Long postId) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if(postOptional.isPresent()){
            Post post = postOptional.get();
            PostOfUserRes response = PostMapping.PostOfUserMapping(post);
            response.setCost(new PostCostRes(this.postCostService.findByPost(post).getCost().getId(),
                    this.postCostService.findByPost(post).getActiveDays()));
            return response;
        }
        return null;
    }

    @Override
    public void handlePostExpire(LocalDate date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);

        Root<Post> post = cq.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(post.get("status"), Status.active));
        predicates.add(cb.lessThanOrEqualTo(post.get("expireAt"), date));
        cq.where(predicates.toArray(new Predicate[0]));
        List<Post> posts = em.createQuery(cq).getResultList();
        for (Post postItem : posts) {
            postItem.setStatus(Status.inactive);
            postItem.setUpdateAt(LocalDate.now());
            this.postRepository.save(postItem);
        }
        log.info("Checking post expiration...done with " + posts.size() + " posts");
    }
}
