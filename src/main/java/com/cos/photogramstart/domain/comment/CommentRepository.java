package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Modifying
    @Query(value = "INSERT INTO Comment(content,imageId,userId,createDate) VALUES(:content, :imageId,:userId,now())",
            nativeQuery = true)
    int mSave(String content,int imageId, int userId);

}
