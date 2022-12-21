package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyMemberStatus is a Querydsl query type for StudyMemberStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyMemberStatus extends EntityPathBase<StudyMemberStatus> {

    private static final long serialVersionUID = 1692275308L;

    public static final QStudyMemberStatus studyMemberStatus = new QStudyMemberStatus("studyMemberStatus");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QStudyMemberStatus(String variable) {
        super(StudyMemberStatus.class, forVariable(variable));
    }

    public QStudyMemberStatus(Path<? extends StudyMemberStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyMemberStatus(PathMetadata metadata) {
        super(StudyMemberStatus.class, metadata);
    }

}

