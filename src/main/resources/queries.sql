-- 1. Nazwy oraz liczba poszczególnych dla typów użytkowników, rosnąco po liczbie:
SELECT roles.name, COUNT(*) AS count
FROM roles
         LEFT JOIN users ON id_role = roles.id
GROUP BY roles.name
ORDER BY count;

-- 2. Nazwy, liczba oraz % całości dla poszczególnych typów użytkowników, roznąco po liczbie:
SELECT r.name AS role, COUNT(u.id) AS amount,
	CONCAT(CAST(COUNT(u.id)::float8*100/(
		SELECT COUNT(id)::float8 
			FROM 
				users 
			WHERE
				is_active = true
	) AS int), '%')
	AS percentage
	FROM 
		users u
	INNER JOIN roles r
		ON u.id_role = r.id
	WHERE
		u.is_active = true
GROUP BY r.name
ORDER BY amount;

-- 3. Nazwy, liczba aktywnych, liczba nieaktywnych, liczba aktywnych + niekatywnych dla poszczególnych typów użytkowników, roznąco po liczbie aktywnych + nieaktywnych:
select roles.name roles, 
	count(case when users.is_active then TRUE end) as active,
	count(case when users.is_active = FALSE then FALSE end) as inactive,
	sum(case when users.id_role is not NULL then 1 else 2 end) as all_users
from users join roles on users.id_role = roles.id
group by roles.name
order by all_users;

-- 4. Nazwa klasy oraz liczba studentów przypisanych do niej, malejąco po liczbie studentów, tylko dla klas w których jest przynajmniej jeden student:
SELECT classrooms.name, COUNT(*) AS count
FROM classrooms
         LEFT JOIN user_classrooms uc on classrooms.id = uc.classroom_id
         LEFT JOIN users u on uc.user_id = u.id
WHERE classroom_id is not null
  AND id_role = 1
GROUP BY classrooms.name
ORDER BY count DESC;

-- 5. Nazwa klasy oraz liczba studentów przypisanych do niej, malejąco po liczbie studentów, dla wszystkich klas z tabeli (jeżeli nie ma studentów to liczba 0)
SELECT cls.name, COUNT(uc.user_id) AS amount
	FROM classrooms cls
	LEFT JOIN user_classrooms uc
		ON uc.classroom_id = cls.id
	LEFT JOIN users u
		ON uc.user_id = u.id
	LEFT JOIN roles r
		ON r.id = u.id_role
	WHERE 
		(u.is_active = true AND r.name = 'Student') OR
		u.id IS NULL
GROUP BY cls.name
ORDER BY amount DESC;

-- 6. Nazwa klasy, liczba studentów przypisanych do niej oraz % z całkowitej liczby studentów w szkole, malejąco po liczbie studentów, alfabetycznie po nazwie klasy, dla wszystkich klas z tabeli (jeżeli nie ma studentów to liczba 0):
SELECT
        cls.name,
        COUNT(uc.user_id) AS amount,
        CONCAT(CAST(COUNT(uc.user_id)::float8 * 100 / (
            SELECT COUNT (u.id) 
                FROM 
                    users u
                INNER JOIN roles r
                    ON r.id = u.id_role
                WHERE
                    u.is_active = true AND
                    r.name = 'Student')::float8 AS int), '%')
        AS percentage
    FROM classrooms cls
    LEFT JOIN user_classrooms uc
        ON uc.classroom_id = cls.id
    LEFT JOIN users u
        ON uc.user_id = u.id
    LEFT JOIN roles r
        ON r.id = u.id_role
    WHERE 
        (u.is_active = true AND r.name = 'Student') OR
        u.id IS NULL
GROUP BY cls.name
ORDER BY amount DESC;

-- 7. Status questu, liczba questów o takim statusie, % z całkowitej liczby questów w zadanej kolejności:
SELECT status, count, count * 100 / quests_students || '%' AS percent_of_all
FROM (SELECT quests_total * students_total AS quests_students
      FROM (SELECT count(*) AS quests_total FROM quests) AS x,
           (SELECT count(*) AS students_total FROM users WHERE id_role = 1 AND is_active = TRUE) AS y) AS d,

     (SELECT 'Finished' AS status, count(*) AS count
      FROM quests
               LEFT JOIN user_quests uc ON uc.quest_id = quests.id
      WHERE uc.accepted = TRUE
      UNION

      SELECT 'Uncompleted' AS status, count(*) AS count
      FROM quests
               LEFT JOIN user_quests uc ON uc.quest_id = quests.id
      WHERE uc.accepted = FALSE
      UNION

      SELECT 'Never started' AS status, quests_total * students_total - started_quests AS count
      FROM (SELECT count(*) AS started_quests
            FROM quests
                     LEFT JOIN user_quests uc ON uc.quest_id = quests.id
            WHERE uc.accepted is not null) AS g,
           (SELECT count(*) AS quests_total FROM quests) AS h,
           (SELECT count(*) AS students_total FROM users WHERE id_role = 1 AND is_active = TRUE) AS j
      GROUP BY quests_total, students_total, started_quests
     ) AS b

GROUP BY status, count, quests_students
ORDER BY CASE status
             WHEN 'Finished' THEN 1
             WHEN 'Uncompleted' THEN 2
             WHEN 'Never started' THEN 3
             ELSE 4
             END;

-- 8. Nazwa questa oraz liczba studentów którzy go ukończyli - top 3 najpopularniejszych skończonych questów, w kolejności od najpopularniejszego:
SELECT q.name, COUNT(quest_id) AS amount
	FROM 
		user_quests uq
	INNER JOIN quests q
		ON q.id = uq.quest_id
GROUP BY q.name
ORDER BY amount DESC
LIMIT 3;

-- 9. Nazwa questa oraz liczba studentów którzy go rozpoczęli i/lub ukończyli - top 3 najpopularniejszych rozpoczętych questów, w kolejności od najpopularniejszego:
select quests.name as quest, count(user_quests.accepted) as top_3_most_popular 
from quests,user_quests
where quests.id = user_quests.quest_id
group by quests.name
order by top_3_most_popular desc
limit 3;

-- 10. Nazwisko i imię studenta oraz liczba ukończonych questów - top 5 studentów, w kolejności od studenta z największą liczbą ukończonych questów a następnie w kolejności alfabetycznej:
select distinct surname || ' ' || u.name as student, count(*) as quests_done
from quests q
         left join user_quests uq on q.id = uq.quest_id
         left join users u on uq.user_id = u.id
where accepted = true
group by u.name, surname
order by quests_done desc, student
limit 5;

-- 11. Nazwiska i imiona studentów, którzy nie rozpoczęli ani nie ukończyli żadnego questa, w kolejności alfabetycznej.
SELECT u.surname, u.name 
	FROM 
		users u
	INNER JOIN roles r
		ON r.id = u.id_role
	WHERE
		u.is_active = true AND
		u.id NOT IN (SELECT user_id FROM user_quests) AND
		r.name = 'Student'
ORDER BY u.surname, u.name;

-- 12. Nazwa artefaktu oraz liczba jego zakupów - top 3 najczęściej kupowanych artefaktów (od najpopularniejszego).
select items.name as item, count(user_items.item_id) as purchases 
from items, user_items
where items.id = user_items.item_id
group by items.name
order by purchases desc
limit 3;


-- 13. Nazwisko i imię studenta oraz liczba zakupionych artefaktów - top 5 studentów, którzy kupili najwięcej artefaktów:
select student, sum(artifacts_bought) as bought_items
from (select surname || ' ' || u.name as student, count(*) as artifacts_bought
      from items i
               left join user_items ui on i.id = ui.item_id
               left join users u on ui.user_id = u.id
      where u.name is not null
      group by u.name, u.surname

      union all
      select u.name || ' ' || surname as student, count(*) as artifacts_bought
      from students_shopping ss
               left join users u on ss.user_id = u.id
               left join group_shopping gs on shopping_id = gs.id
      where gs.is_paid = true
      group by u.name, u.surname) as sabsab
group by student
order by bought_items desc
limit 5;

-- 14. Nazwiska i imiona studentów, którzy nie kupili żadnego artefaktu, w kolejności alfabetycznej.
SELECT u.surname, u.name 
	FROM 
		users u
	INNER JOIN roles r
		ON r.id = u.id_role
	WHERE
		u.is_active = true AND
		u.id NOT IN (SELECT user_id FROM user_items) AND
		u.id NOT IN (
			SELECT user_id 
				FROM 
					students_shopping
				WHERE
					confirmed = true
		) AND
		r.name = 'Student'
ORDER BY u.surname, u.name;

-- 15. Nazwa kategorii, liczba artefaktów oraz % z całkowitej liczby artefaktów dla zadanych kategorii:
SELECT 'bought at least once',
        (SELECT COUNT(id) FROM items) - ui.amount,
        CONCAT(CAST(((SELECT COUNT(id) FROM items) - ui.amount)::float8 * 100
              / (SELECT COUNT(id) FROM items)::float8 AS int), '%')
        FROM
(SELECT DISTINCT COUNT(item_id) + 
    (SELECT DISTINCT COUNT(item_id)
     FROM group_shopping) AS amount
    FROM user_items) AS ui
UNION
SELECT 'never bought',
        ui.amount,
        CONCAT(CAST(ui.amount::float8 * 100
              / (SELECT COUNT(id) FROM items)::float8 AS int), '%')
        FROM
(SELECT DISTINCT COUNT(item_id) + 
    (SELECT DISTINCT COUNT(item_id)
     FROM group_shopping) AS amount
    FROM user_items) AS ui;
