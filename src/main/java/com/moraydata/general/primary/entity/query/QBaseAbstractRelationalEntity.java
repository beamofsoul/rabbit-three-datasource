package com.moraydata.general.primary.entity.query;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.moraydata.general.primary.entity.BaseAbstractRelationalEntity;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;


/**
 * QBaseAbstractRelationalEntity is a Querydsl query type for BaseAbstractRelationalEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseAbstractRelationalEntity extends EntityPathBase<BaseAbstractRelationalEntity> {

    private static final long serialVersionUID = 1404586576L;

    public static final QBaseAbstractRelationalEntity baseAbstractRelationalEntity = new QBaseAbstractRelationalEntity("baseAbstractRelationalEntity");

    public final QBaseAbstractEntity _super = new QBaseAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QBaseAbstractRelationalEntity(String variable) {
        super(BaseAbstractRelationalEntity.class, forVariable(variable));
    }

    public QBaseAbstractRelationalEntity(Path<? extends BaseAbstractRelationalEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseAbstractRelationalEntity(PathMetadata metadata) {
        super(BaseAbstractRelationalEntity.class, metadata);
    }

}

