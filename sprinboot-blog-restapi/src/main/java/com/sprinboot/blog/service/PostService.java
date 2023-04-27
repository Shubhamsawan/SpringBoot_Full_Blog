package com.sprinboot.blog.service;

import com.sprinboot.blog.payload.PostDto;
import com.sprinboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    public void deletePostById( long id);
    List<PostDto> getPostByCategory(long categoryId);
}
