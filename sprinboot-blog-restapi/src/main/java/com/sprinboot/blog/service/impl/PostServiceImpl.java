package com.sprinboot.blog.service.impl;

import com.sprinboot.blog.entity.Category;
import com.sprinboot.blog.entity.Post;
import com.sprinboot.blog.exception.ResourceNotFoundException;
import com.sprinboot.blog.payload.PostDto;
import com.sprinboot.blog.payload.PostResponse;
import com.sprinboot.blog.repository.CategoryRepository;
import com.sprinboot.blog.repository.PostRepository;
import com.sprinboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    //We add this beacause we neet to inject one class if
    // we need to inject more than one we will use autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }


    //Logic for insert new detail using opi
   /* {
        "title": "blog post 7",
            "description": "blog post description updated 7",
            "content": "blog content updated 7",
            "categoryId":2
    } */
    @Override
    public PostDto createPost(PostDto postDto) {

      Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        //Covert dto entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //convert entity to dto
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }


    //Logic for get detail stored in db using  opi
    @Override
/*    public List<PostDto> getAllPost(int pageNo, int pageSize) {*/
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
// Create page instance
        Pageable pagable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pagable);

        //get content for page object
        List<Post> listOfPost = posts.getContent();
        List<PostDto> content = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElemrnt(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    //Logic for get detail opi using id
    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));
        return mapToDto(post);
    }

    //Logic for update opi using id
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //insert updated post by id from db
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));

       Category category = categoryRepository.findById(postDto.getCategoryId())
                       .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    // logic for deleting detail using id
    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> mapToDto(post))
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);
        //convert entity to dto
       /* PostDto postDto= new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent()); */

        return postDto;

    }

    //covert dto to entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

        /* Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent()); */
        return post;
    }
}


//reuest to send in json
//{
//    "title" : "My Updated New One Post",
//        "description" : "Updateed Post for new Description",
//        "content" : "This is my Updated new post by put Api"
//}