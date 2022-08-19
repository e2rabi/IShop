create table category(id uuid not null,
    description varchar(255),
    name varchar(255),
    parent_category_id uuid,
    primary key (id)
 );
