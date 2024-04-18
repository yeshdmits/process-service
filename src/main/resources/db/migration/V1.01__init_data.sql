-- Create table
create table test_data (
                           id serial primary key,
                           value boolean default false
);

-- Insert data
insert into test_data (id, value) values (0, true);
