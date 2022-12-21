package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.model.CodeHistoryTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import static com.project.owlback.codereview.model.QCodeHistoryTag.codeHistoryTag;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CodeHistoryTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<CodeHistoryTag> findByTagContent(CodeReviewSearchCondition condition, Pageable pageable) throws SQLException {
        List<CodeHistoryTag> list = queryFactory.selectFrom(codeHistoryTag)
                .where(tagContentEq(condition.getTag()))
                .orderBy(codeHistoryTag.codeHistory.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(list, pageable, list.size());
    }

    public List<String> getTagContent(CodeReviewSearchCondition condition) throws SQLException {
        List<String> list = queryFactory.select(codeHistoryTag.tag.content)
                .from(codeHistoryTag)
                .where(tagContentEq(condition.getTag()))
                .orderBy(codeHistoryTag.count.desc())
                .fetch();

        return list;
    }

    public List<String> getTagContentbyCodeHistoryId(long codeHistoryId) throws SQLException {
        List<String> list = queryFactory.select(codeHistoryTag.tag.content)
                .from(codeHistoryTag)
                .where(codeReviewIdEq(codeHistoryId))
                .orderBy(codeHistoryTag.count.desc())
                .fetch();
        return list;
    }

    public List<String> getTagContentbyCodeReviewId(long codeReviewId, int versionNum) throws SQLException{
        List<String> list = queryFactory.select(codeHistoryTag.tag.content)
                .from(codeHistoryTag)
                .where(codeHistoryTag.codeHistory.codeReview.id.eq(codeReviewId),
                        codeHistoryTag.codeHistory.versionNum.eq(versionNum)
                        )
                .fetch();

        return list;
    }

    private BooleanExpression tagContentEq(String content) {
        return hasText(content) ? codeHistoryTag.tag.content.contains(content) : null;
    }

    private BooleanExpression codeReviewIdEq(long id) {
        return id != 0 ? codeHistoryTag.codeHistory.id.eq(id) : null;
    }
}
