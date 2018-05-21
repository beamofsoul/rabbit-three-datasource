package com.moraydata.general.secondary.entity.query;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.moraydata.general.primary.entity.query.QBaseAbstractEntity;
import com.moraydata.general.secondary.entity.Database;
import com.querydsl.core.types.Path;


/**
 * QDatabase is a Querydsl query type for Database
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDatabase extends EntityPathBase<Database> {

    private static final long serialVersionUID = -1972412117L;

    public static final QDatabase database = new QDatabase("database");

    public final QBaseAbstractEntity _super = new QBaseAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jdbcUrl = createString("jdbcUrl");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QDatabase(String variable) {
        super(Database.class, forVariable(variable));
    }

    public QDatabase(Path<? extends Database> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDatabase(PathMetadata metadata) {
        super(Database.class, metadata);
    }

}

