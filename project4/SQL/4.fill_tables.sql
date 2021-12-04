USE `elective1`;

INSERT INTO role (
`role_id`,
`name`
) VALUES (
 '1',
'Admin'
);

INSERT INTO role (
`role_id`,
`name`
) VALUES (
 '2',
'Teacher'
);

INSERT INTO role (
`role_id`,
`name`
) VALUES (
 '3',
'Student'
);

INSERT INTO university_group (
`university_group_id`,
 `name`)
 VALUES (
 '1',
 'Admin'
 );

INSERT INTO university_group (
`university_group_id`,
`name`)
VALUES (
'2',
'Teacher'
);

INSERT INTO university_group (
`university_group_id`,
`name`)
VALUES (
'3',
'01042'
);

INSERT INTO university_group (
`university_group_id`,
`name`)
VALUES (
'4',
'01033'
);

INSERT INTO university_group (
`university_group_id`,
`name`)
VALUES (
'5',
'01051'
);

INSERT INTO account (
`account_id`,
`role_id`,
`login`,
`password`,
`is_active`)
VALUES (
'1',
'1',
'Admin',
'$2a$10$mNc80lxraCcUXntSek9bvO6N4llqK39JBbHvjeHo82UTYT09W8G2O', /*BCrypt password "Administrator1"*/
'1'
);

INSERT INTO user (
`user_id`,
`account_id`,
`university_group_id`,
`first_name`,
`last_name`)
 VALUES (
 '1',
 '1',
 '1',
 'Administrator',
 'Administrator'
 );

INSERT INTO account (
`account_id`,
`role_id`,
`login`,
`password`,
`is_active`)
VALUES (
'2',
'2',
'ValeriyMatatov',
'$2a$10$OhdhZnzbopvWpGmJs73OeeRPQ/w6btFfujqcpKcgwHgZ78QsFcrjm', /*BCrypt password "ValeriyMatatov65"*/
'1'
);

INSERT INTO user (
`user_id`,
`account_id`,
`university_group_id`,
`first_name`,
`last_name`)
 VALUES (
 '2',
 '2',
 '2',
 'Валерий',
 'Мататов'
 );

INSERT INTO account (
`account_id`,
`role_id`,
`login`,
`password`,
`is_active`)
VALUES (
'3',
'3',
'DmitryFilipenko',
'$2a$10$FOmXMfDZQRkoAYcVru3Z9uVOnwTdj0IvLKDoEw7SEb2UmKBzc1njG', /*BCrypt password "DmitryFilipenko92"*/
'1'
);

INSERT INTO user (
`user_id`,
`account_id`,
`university_group_id`,
`first_name`,
`last_name`)
 VALUES (
 '3',
 '3',
 '3',
 'Дмитрий',
 'Филипенко'
 );
