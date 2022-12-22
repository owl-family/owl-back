package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition; 
import com.project.owlback.codereview.model.CodeComment;
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

@Repository
@RequiredArgsConstructor
public class CodeCommentRepositoryCustom {
    private final JPAQueryFactory qeuryFactory;

    public Page<CodeComment> getCodeComment(CodeReviewSearchCondition condition, Pageable pageable) throws SQLException {
        List<CodeComment> list = qeuryFactory.selectFrom(codeComment)
                .where(codeHistoryIdEq(condition.getCodeHistoryId()),
                        startLineEq(condition.getStartLine()))
                .orderBy(codeComment.parent.asc())
                .orderBy(codeComment.createdDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(list, pageable, list.size());
    }

    private BooleanExpression codeHistoryIdEq(long codeHistoryId) {
        return codeHistoryId != 0 ? codeComment.codeHistory.id.eq(codeHistoryId) : null;
    }

    private BooleanExpression startLineEq(int startLine) {
        return startLine != 0 ? codeComment.startLine.eq(Math.toIntExact(startLine)) : null;
    }
}
