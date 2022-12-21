package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeReviewTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import static com.project.owlback.codereview.model.QCodeComment.codeComment;
import static com.project.owlback.codereview.model.QCodeReviewTag.codeReviewTag;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CodeReviewTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<CodeReviewTag> findByTagContent(CodeReviewSearchCondition condition, Pageable pageable) throws SQLException {
        List<CodeReviewTag> list = queryFactory.selectFrom(codeReviewTag)
                .where(tagContentEq(condition.getTag()))
                .orderBy(codeReviewTag.codeReview.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(list, pageable, list.size());
    }

    public List<String> getTagContent(CodeReviewSearchCondition condition) throws SQLException {
        List<String> list = queryFactory.select(codeReviewTag.tag.content)
                .from(codeReviewTag)
                .where(tagContentEq(condition.getTag()))
                .orderBy(codeReviewTag.count.desc())
                .fetch();

        return list;
    }

    public List<String> getTagContentbyCodeReviewId(CodeReviewSearchCondition condition) throws SQLException {
        List<String> list = queryFactory.select(codeReviewTag.tag.content)
                .from(codeReviewTag)
                .where(codeReviewIdEq(condition.getCodeReviewId()))
                .orderBy(codeReviewTag.count.desc())
                .fetch();
        return list;
    }

    private BooleanExpression tagContentEq(String content) {
        return hasText(content) ? codeReviewTag.tag.content.contains(content) : null;
    }

    private BooleanExpression codeReviewIdEq(long id) {
        return id != 0 ? codeReviewTag.codeReview.id.eq(id) : null;
    }
}
