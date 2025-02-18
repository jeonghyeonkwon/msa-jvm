create table users (
    created_date datetime(6),
    last_modified_date datetime(6),
    users_id bigint not null,
    nick_name varchar(255),
    password varchar(255),
    user_name varchar(255),
    users_role enum ('ADMIN','BASIC'),
    primary key (users_id)
) engine=InnoDB