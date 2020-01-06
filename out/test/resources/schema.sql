create table if not exists message_board_client (
  client_id int auto_increment  primary key,
  client_name varchar(50) not null,
  client_type varchar(10) not null,
  client_description varchar(50),
  creation_ts timestamp,
  updated_ts timestamp,
unique key client_name (client_name)
);

create table if not exists message (
  message_id int auto_increment  primary key,
  creation_ts timestamp,
  updated_ts timestamp,
  text varchar(250),
  title varchar(50),
  message_client_id int,
foreign key (message_client_id) references message_board_client(client_id)
);