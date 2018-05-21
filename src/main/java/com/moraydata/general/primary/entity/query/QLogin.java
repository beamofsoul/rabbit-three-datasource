package com.moraydata.general.primary.entity.query;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.moraydata.general.primary.entity.Login;
import com.querydsl.core.types.Path;


/**
 * QLogin is a Querydsl query type for Login
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLogin extends EntityPathBase<Login> {

    private static final long serialVersionUID = 1092173241L;

    public static final QLogin login = new QLogin("login");

    public final QBaseAbstractEntity _super = new QBaseAbstractEntity(this);

    public final StringPath brand = createString("brand");

    public final StringPath browser = createString("browser");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath model = createString("model");

    public final StringPath operatingSystem = createString("operatingSystem");

    public final StringPath screenSize = createString("screenSize");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QLogin(String variable) {
        super(Login.class, forVariable(variable));
    }

    public QLogin(Path<? extends Login> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogin(PathMetadata metadata) {
        super(Login.class, metadata);
    }

}

