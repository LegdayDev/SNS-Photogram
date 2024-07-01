package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubScribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    @Transactional
    public void 구독하기(int fromUserId, int toUserId){
        try{
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독하였습니다.");
        }

    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }


    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
        // 쿼리 세팅
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id , u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 FROM photogram.Subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) as subscribeState, "); // ? : principalId
        sb.append("if((CASE u.id WHEN ? THEN 1 else 0 end),1,0) as equalUserState "); // ? : principalId
        sb.append("FROM photogram.User u INNER JOIN photogram.Subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // ? : pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2,principalId)
                .setParameter(3,pageUserId);

        // 쿼리 실행(QLRM 라이브러리 필요)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);//한건은 uniqueResult() 사용

        return subscribeDtos;
    }
}
