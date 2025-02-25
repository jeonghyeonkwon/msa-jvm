create table board
(
    board_id           bigint not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    users_users_id     bigint,
    content            varchar(255),
    title              varchar(255),
    board_status       enum ('DELETED','HIDDEN','NORMAL'),
    primary key (board_id)
) engine=InnoDB

create table comment
(
    board_board_id     bigint,
    comment_id         bigint not null,
    created_date       datetime(6),
    last_modified_date datetime(6),
    users_users_id     bigint,
    content            varchar(255),
    primary key (comment_id)
) engine=InnoDB

create table users
(
    created_date       datetime(6),
    last_modified_date datetime(6),
    users_id           bigint not null,
    username           varchar(255),
    primary key (users_id)
) engine=InnoDB
