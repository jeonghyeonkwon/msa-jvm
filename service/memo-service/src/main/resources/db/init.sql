create table memo (
    created_date datetime(6),
    end_date datetime(6),
    id bigint not null,
    last_modified_date datetime(6),
    start_date datetime(6),
    content varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB