package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM photogram.Image WHERE userId IN (SELECT toUserId  FROM photogram.Subscribe WHERE fromUserId = :principalId) ORDER BY id DESC",
            nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

    @Query(value = "SELECT i.* FROM photogram.Image i INNER JOIN (SELECT imageId, COUNT(imageId) AS likeCount FROM photogram.Likes GROUP BY imageId) AS C ON i.id = C.imageId ORDER BY C.likeCount DESC", nativeQuery = true)
    List<Image> mPopular();
}
