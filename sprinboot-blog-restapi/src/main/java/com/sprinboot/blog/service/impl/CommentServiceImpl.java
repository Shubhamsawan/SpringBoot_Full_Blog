package com.sprinboot.blog.service.impl;

import com.sprinboot.blog.entity.Comment;
import com.sprinboot.blog.entity.Post;
import com.sprinboot.blog.exception.BlogApiException;
import com.sprinboot.blog.exception.ResourceNotFoundException;
import com.sprinboot.blog.payload.CommentDto;
import com.sprinboot.blog.payload.PostDto;
import com.sprinboot.blog.repository.Commentrepository;
import com.sprinboot.blog.repository.PostRepository;
import com.sprinboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private Commentrepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(Commentrepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DB
        Comment newComment =  commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        //retriev e comment by postiD

        List<Comment> comments= commentRepository.findByPostId(postId);

        //convert list of comment entities to list of comment dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

//        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id",postId));

        //        retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment","id",commentId));

//        if (!comment.getPost().getId().equals(post.getId())){
//            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
//        }
        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentReq) {
        //        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id",postId));

        //        retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment","id",commentId));

        if(!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
            comment.setName(commentReq.getName());
            comment.setEmail(commentReq.getEmail());
            comment.setBody(commentReq.getBody());

            Comment updatedComment = commentRepository.save(comment);
            return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        //        retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id",postId));

        //        retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResourceNotFoundException("Comment","id",commentId));

        if(!Objects.equals(comment.getPost().getId(), post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);

//        Comment comment= new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
