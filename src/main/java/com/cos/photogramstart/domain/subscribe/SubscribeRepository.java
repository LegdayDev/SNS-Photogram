package com.cos.photogramstart.domain.subscribe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying // 데이터베이스에 변경을 주는 쿼리(INSERT,DELETE,UPDATE)에는 @Modifying 어노테이션이 필요하다
    @Query(value =
            "INSERT INTO subscribe(fromUserId, toUserId,createDate) VALUES(:fromUserId, :toUserId, now())",
            nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId); // 성공하면 1(변경된 행의 갯수) , 실패하면 -1 Return

    @Modifying
    @Query(value =
            "DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserId=:toUserId",
            nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId=:principalId AND toUserId=:pageUserId"
            ,nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId=:pageUserId"
            ,nativeQuery = true)
    int mSubscribeCount(int pageUserId);
}
