package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeScope is a Querydsl query type for CodeScope
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeScope extends EntityPathBase<CodeScope> {

    private static final long serialVersionUID = 1050132030L;

    public static final QCodeScope codeScope = new QCodeScope("codeScope");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QCodeScope(String variable) {
        super(CodeScope.class, forVariable(variable));
    }

    public QCodeScope(Path<? extends CodeScope> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeScope(PathMetadata metadata) {
        super(CodeScope.class, metadata);
    }

}

