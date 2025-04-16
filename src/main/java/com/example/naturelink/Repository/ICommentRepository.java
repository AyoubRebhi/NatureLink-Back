package com.example.naturelink.Repository;
import com.example.naturelink.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    public interface CommentRepository extends JpaRepository<Comment, Long> {
        @Modifying
        @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
        void deleteByPostId(@Param("postId") Long postId);
    }
    // Des méthodes personnalisées peuvent être ajoutées ici si nécessaire
}
