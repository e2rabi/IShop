insert into Authority (id,created_date, last_modified_date, version, permission) values ('a3af4887-f75c-4301-92e5-c3cff2653643','2022-09-10 18:09:52.944', '2022-09-10 18:09:52.944', 0, 'write_user');
insert into role (id,created_date, last_modified_date, version, name) values ('19347b83-1a78-4788-9bbc-b1861fe99050','2022-09-10 18:26:01.176','2022-09-10 18:26:01.251','0','ADMIN');
insert into role_authority (role_id, authority_id) values ('19347b83-1a78-4788-9bbc-b1861fe99050','a3af4887-f75c-4301-92e5-c3cff2653643');
insert into users (id,created_date, last_modified_date, version, account_non_expired, account_non_locked, address, credentials_non_expired, email, enabled, first_name, google2fa_secret, last_name, password, phone, use_google2fa, username) values ('6e57e08a-7fd9-4886-ac79-1934ab06d015', '2022-09-10 18:26:01.396', '2022-09-10 18:26:01.396', 0, 'TRUE', 'TRUE', '', 'TRUE', 'errabi.ayoube@gmail.com', 'TRUE', 'ayoub', '', 'errabi', '$2a$10$ihSEI6uwWAckp9WPA7nQZ.mTir6kuYAr7Wg.Epe3pSHhQnpHYNK3S','0607707989', 'FALSE', 'admin');
insert into user_role (user_id, role_id) values ('6e57e08a-7fd9-4886-ac79-1934ab06d015','19347b83-1a78-4788-9bbc-b1861fe99050');

