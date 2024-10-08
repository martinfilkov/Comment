package com.tinqinacademy.comment.persistence.repositories;

import com.tinqinacademy.comment.persistence.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByRoomId(UUID roomId);
}
