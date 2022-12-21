package com.project.owlback.codereview.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.project.owlback.codereview.model.QCodeHistory.codeHistory;

@Repository
@RequiredArgsConstructor
public class CodeHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public String getSubTitle(long codeReviewId, int versionNum){
        String subtitle = queryFactory.select(codeHistory.subTitle)
                .from(codeHistory)
                .where(codeHistory.codeReview.id.eq(codeReviewId),
                        codeHistory.versionNum.eq(versionNum)
                        )
                .fetch().get(0);
        return subtitle;
    }

}
