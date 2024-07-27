package com.tinqinacademy.comment.persistence.repositories;

import com.tinqinacademy.comment.persistence.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
