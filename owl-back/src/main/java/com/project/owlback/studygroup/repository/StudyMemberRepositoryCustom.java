package com.project.owlback.studygroup.repository;

import com.project.owlback.codereview.model.StudyMember;
import com.project.owlback.studygroup.dto.StudyGroupCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.owlback.codereview.model.QStudyMember.studyMember;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class StudyMemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Optional<StudyMember> getStudyMember(StudyGroupCondition condition) {
        Optional<StudyMember> member = Optional.ofNullable(queryFactory.selectFrom(studyMember)
                .where(studyIdAnduserIdEq(condition.getStudyId(), condition.getUserId()),
                        descriptionEq(condition.getDescription()))
                .fetchOne());
        return member;
    }

    private BooleanExpression studyIdAnduserIdEq(long studyId, long userId) {
        return (studyId != 0 && userId != 0) ?
                studyMember.studyGroup.id.eq(studyId).and(studyMember.user.id.eq(userId)) : null;
    }

    private BooleanExpression descriptionEq(String description) {
        return hasText(description) ? studyMember.studyMemberStatus.description.eq(description) : null;
    }
}
