package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.model.CodeReview;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

import static com.project.owlback.codereview.model.QCodeReview.codeReview;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CodeReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<CodeReview> SearchByCondition(CodeReviewSearchCondition condition, Pageable pageable) throws SQLException {
        List<CodeReview> list = queryFactory.selectFrom(codeReview)
                .where(titleEq(condition.getTitle()),
                        nicknameEq(condition.getWriter()),
                        languageEq(condition.getLanguage()),
                        studyGroupIdEq(condition.getStudyGroupId())
                )
                .orderBy(codeReview.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(list, pageable, list.size());
    }

    // title, nickname, language
    private BooleanExpression titleEq(String title) {
        return hasText(title) ? codeReview.title.contains(title) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? codeReview.writer.nickname.contains(nickname) : null;
    }

    private BooleanExpression languageEq(String language) {
        return hasText(language) ? codeReview.codeLanguage.description.contains(language) : null;
    }

    private BooleanExpression studyGroupIdEq(long id) {
        return id != 0 ? codeReview.studyGroup.id.eq(id) : null;

    }

}
