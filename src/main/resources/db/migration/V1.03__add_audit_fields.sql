alter table task
    add column created_at timestamp,
    add column modified_at timestamp,
    add column created_by varchar(128),
    add column modified_by varchar(128);

alter table process
    add column created_at timestamp,
    add column modified_at timestamp,
    add column created_by varchar(128),
    add column modified_by varchar(128);

alter table document
    add column created_at timestamp,
    add column modified_at timestamp,
    add column created_by varchar(128),
    add column modified_by varchar(128);