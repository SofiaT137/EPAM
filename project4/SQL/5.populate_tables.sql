USE `elective1`;

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
'$2a$10$mNc80lxraCcUXntSek9bvO6N4llqK39JBbHvjeHo82UTYT09W8G2O', /** BCrypt password "Administrator1"*/
'1'
);

INSERT INTO user (5
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
'$2a$10$OhdhZnzbopvWpGmJs73OeeRPQ/w6btFfujqcpKcgwHgZ78QsFcrjm', /** BCrypt password "ValeriyMatatov65"*/
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
'$2a$10$FOmXMfDZQRkoAYcVru3Z9uVOnwTdj0IvLKDoEw7SEb2UmKBzc1njG', /** BCrypt password "DmitryFilipenko92"*/
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

INSERT INTO account (
`account_id`,
`role_id`,
`login`,
`password`,
`is_active`)
VALUES (
'4',
'3',
'SergeyGivoglot93',
'$2a$10$HO3a131TXVLpoPXKGRNeS.rXZyJzc/cEI/HgYUacW.yM.PigktIcC', /** BCrypt password "SergeyGivoglot93"*/
'1'
);

INSERT INTO user (
`user_id`,
`account_id`,
`university_group_id`,
`first_name`,
`last_name`)
VALUES (
'4',
'4',
'3',
'Сергей',
'Живоглод'
);

  /** Finished course */

INSERT INTO course (
`course_id`,
`name`,
`start_date`,
`end_date`)
VALUES (
'1',
'С++',
'2021-11-01',
'2021-11-16'
);

/** Course mentor append */

INSERT INTO user_has_course (
`user_id`,
`course_id`) VALUES (
'2',
'1'
);

/** Course participant append */

INSERT INTO user_has_course (
`user_id`,
`course_id`) VALUES (
'3',
'1'
);

/** Course participant append */

INSERT INTO user_has_course (
`user_id`,
`course_id`) VALUES (
'4',
'1'
);

  /** Current course */

INSERT INTO course (
`course_id`,
`name`,
`start_date`,
`end_date`)
VALUES (
'2',
'Python',
'2021-12-01',
'2022-03-14'
);

/** Course mentor append */

INSERT INTO user_has_course (
`user_id`,
`course_id`) VALUES (
'2',
'2'
);

/** Course participant append */

INSERT INTO user_has_course (
`user_id`,
`course_id`) VALUES (
'3',
'2'
);

/** Review append*/

INSERT INTO review (
`review_id`,
`user_id`,
`course_id`,
`grade`,
`review`) VALUES (
'1',
'4',
'1',
'9',
'Отлично. Так держать!'
);



