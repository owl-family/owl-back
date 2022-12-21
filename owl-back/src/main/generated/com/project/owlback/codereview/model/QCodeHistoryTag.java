package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeHistoryTag is a Querydsl query type for CodeHistoryTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeHistoryTag extends EntityPathBase<CodeHistoryTag> {

    private static final long serialVersionUID = 1870335164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeHistoryTag codeHistoryTag = new QCodeHistoryTag("codeHistoryTag");

    public final QCodeHistory codeHistory;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QTag tag;

    public QCodeHistoryTag(String variable) {
        this(CodeHistoryTag.class, forVariable(variable), INITS);
    }

    public QCodeHistoryTag(Path<? extends CodeHistoryTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeHistoryTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeHistoryTag(PathMetadata metadata, PathInits inits) {
        this(CodeHistoryTag.class, metadata, inits);
    }

    public QCodeHistoryTag(Class<? extends CodeHistoryTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeHistory = inits.isInitialized("codeHistory") ? new QCodeHistory(forProperty("codeHistory"), inits.get("codeHistory")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

