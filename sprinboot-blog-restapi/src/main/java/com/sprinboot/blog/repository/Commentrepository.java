package com.sprinboot.blog.repository;

import com.sprinboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Commentrepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);
}
