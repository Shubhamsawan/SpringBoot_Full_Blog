package com.sprinboot.blog.service;

import com.sprinboot.blog.payload.CategoryDto;
import com.sprinboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(Long postId, long commentId, CommentDto commentReq);

    void deleteComment(long postId, long commentId);


}
