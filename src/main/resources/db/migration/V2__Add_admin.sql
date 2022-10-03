insert into usr (id, username, password, is_active)
values (1000, 'admin', '123', true);

insert into user_role (user_id, roles)
values (1000, 'USER'), (1000, 'ADMIN');
