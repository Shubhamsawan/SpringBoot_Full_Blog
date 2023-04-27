package com.sprinboot.blog.controller;

import com.sprinboot.blog.payload.CommentDto;
import com.sprinboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8080/swagger-ui/index.html

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // url http://localhost:8080/api/posts/2/comments
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid  @PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    //url http://localhost:8080/api/posts/2/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable (value = "postId") long postId){

        return commentService.getCommentByPostId(postId);
    }

    //url http://localhost:8080/api/posts/2/comments/2

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // url: http://localhost:8080/api/posts/1/comments/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment( @PathVariable (value = "postId") long postId,
                                                    @PathVariable(value = "id") long commentId,
                                                     @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    //url : http://localhost:8080/api/posts/1/comments/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable (value = "postId") long postId,
                                                @PathVariable(value = "id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted sucessfully", HttpStatus.OK);
    }

    // Before posting data we need to do login using follwind step
//1. access url http://localhost:8080/api/auth/login
//2. {
//        "usernameOrEmail":"admin@gmail.com",
//        "password":"admin"
//        }
//3. then copy : accesstoken
//4. Then go to api page -> Authorization -> Type: Bearer Token -> copy the token -> then data will send


    //Response
//    {
//        "name": " Ram Sawant",
//            "email": " Gam@gmail.com",
//            "body": "Fad informative post"
//    }
}

