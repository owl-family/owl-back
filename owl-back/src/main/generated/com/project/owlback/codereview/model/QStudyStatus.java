package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyStatus is a Querydsl query type for StudyStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyStatus extends EntityPathBase<StudyStatus> {

    private static final long serialVersionUID = 992274226L;

    public static final QStudyStatus studyStatus = new QStudyStatus("studyStatus");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QStudyStatus(String variable) {
        super(StudyStatus.class, forVariable(variable));
    }

    public QStudyStatus(Path<? extends StudyStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyStatus(PathMetadata metadata) {
        super(StudyStatus.class, metadata);
    }

}

