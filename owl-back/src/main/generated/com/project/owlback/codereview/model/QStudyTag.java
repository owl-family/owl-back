package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyTag is a Querydsl query type for StudyTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyTag extends EntityPathBase<StudyTag> {

    private static final long serialVersionUID = -2070679462L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyTag studyTag = new QStudyTag("studyTag");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QStudyGroup studyGroup;

    public final QTag tag;

    public QStudyTag(String variable) {
        this(StudyTag.class, forVariable(variable), INITS);
    }

    public QStudyTag(Path<? extends StudyTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyTag(PathMetadata metadata, PathInits inits) {
        this(StudyTag.class, metadata, inits);
    }

    public QStudyTag(Class<? extends StudyTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyGroup = inits.isInitialized("studyGroup") ? new QStudyGroup(forProperty("studyGroup"), inits.get("studyGroup")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

