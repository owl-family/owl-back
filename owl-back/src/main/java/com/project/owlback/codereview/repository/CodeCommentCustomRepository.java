package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.QCodeComment;
import com.project.owlback.codereview.model.QCodeReview;
import com.project.owlback.codereview.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.owlback.codereview.model.QCodeComment.codeComment;

@Repository
@RequiredArgsConstructor
public class CodeCommentCustomRepository {
    private final JPAQueryFactory queryFactory;

    public Page<CodeComment> getMyComments(User user, String key, String word, Pageable pageable) {

        final List<CodeComment> list = queryFactory.selectFrom(codeComment)
                .where(codeComment.writer.eq(user), commentEq(key, word))
                .orderBy(codeComment.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(list, pageable, list.size());
    }

    public BooleanExpression commentEq(String key, String word) {
        return switch (key) {
            case "title" -> codeComment.codeHistory.codeReview.title.contains(word);
            case "writer" -> codeComment.codeHistory.codeReview.writer.nickname.contains(word);
            case "contents" -> codeComment.contents.contains(word);
            default -> throw new IllegalArgumentException();
        };
    }
}
