package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeComment is a Querydsl query type for CodeComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeComment extends EntityPathBase<CodeComment> {

    private static final long serialVersionUID = -1113973815L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeComment codeComment = new QCodeComment("codeComment");

    public final QCodeHistory codeHistory;

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.Instant> createdDate = createDateTime("createdDate", java.time.Instant.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Integer> endLine = createNumber("endLine", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final DateTimePath<java.time.Instant> modifiedDate = createDateTime("modifiedDate", java.time.Instant.class);

    public final NumberPath<Integer> parent = createNumber("parent", Integer.class);

    public final NumberPath<Integer> startLine = createNumber("startLine", Integer.class);

    public final QUser writer;

    public QCodeComment(String variable) {
        this(CodeComment.class, forVariable(variable), INITS);
    }

    public QCodeComment(Path<? extends CodeComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeComment(PathMetadata metadata, PathInits inits) {
        this(CodeComment.class, metadata, inits);
    }

    public QCodeComment(Class<? extends CodeComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeHistory = inits.isInitialized("codeHistory") ? new QCodeHistory(forProperty("codeHistory"), inits.get("codeHistory")) : null;
        this.writer = inits.isInitialized("writer") ? new QUser(forProperty("writer")) : null;
    }

}

