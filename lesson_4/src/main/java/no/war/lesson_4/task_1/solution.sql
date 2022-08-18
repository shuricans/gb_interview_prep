/*
ошибки в расписании (фильмы накладываются друг на друга),
отсортированные по возрастанию времени.
Выводить надо колонки: «фильм 1», «время начала», «длительность»,
                       «фильм 2», «время начала», «длительность»;
*/

-- *Для наглядности добавил стобик shift, отображающий смещение времени.

WITH movies_after AS (
    SELECT
        name AS "movie_1",
        time_start AS "time_start_1",
        running_time AS "running_time_1",
        LEAD(name) OVER (ORDER BY time_start ASC) AS "movie_2",
        LEAD(time_start) OVER (ORDER BY time_start ASC) AS "time_start_2",
        LEAD(running_time) OVER (ORDER BY time_start ASC) AS "running_time_2"
    FROM sessions JOIN movies USING (id_movie)
), movies_shift AS (
    SELECT
        *,
        time_start_2 -
        (time_start_1 + (running_time_1 * interval '1 minute')) AS "shift"
    FROM movies_after
)
SELECT
    movie_1,
    time_start_1::varchar,
    running_time_1,
    movie_2,
    time_start_2::varchar,
    running_time_2,
    shift::varchar AS "shift"
FROM movies_shift
WHERE shift < '00:00:00'::time
ORDER BY shift ASC;


/*
перерывы 30 минут и более между фильмами
— выводить по уменьшению длительности перерыва.
Колонки «фильм 1», «время начала», «длительность»,
«время начала второго фильма», «длительность перерыва»;
*/

WITH movies_after AS (
    SELECT
        name AS "movie_1",
        time_start AS "time_start_1",
        running_time,
        LEAD(time_start) OVER (ORDER BY time_start ASC) AS "time_start_2",
        (LEAD(time_start) OVER (ORDER BY time_start ASC)) -
            (time_start + (running_time * interval '1 minute')) AS "break_duration"
    FROM sessions JOIN movies USING (id_movie)
)
SELECT
    movie_1,
    time_start_1::varchar,
    running_time,
    time_start_2::varchar,
    break_duration::varchar
FROM movies_after
WHERE break_duration >= '00:30:00'::time
ORDER BY break_duration DESC;


/*
список фильмов, для каждого
— с указанием общего числа посетителей за все время,
- среднего числа зрителей за сеанс
- общей суммы сборов по каждому фильму
- (отсортировать по убыванию прибыли).
- Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу;
*/

WITH movies_stats AS (
    SELECT DISTINCT
        name AS "movie",
        COUNT(*) OVER (PARTITION BY id_movie) AS "total_clients",
        (COUNT(*) OVER (PARTITION BY id_movie)) /
            (SELECT COUNT(*) FROM sessions WHERE movies.id_movie = id_movie) AS "avg_clients_per_session",
        SUM(tiket_price) OVER (PARTITION BY id_movie) AS "profit"
    FROM movies
    JOIN sessions USING (id_movie)
    JOIN tikets USING (id_session)
    ORDER BY profit DESC
)
SELECT * FROM movies_stats
UNION ALL
SELECT
    'Total:',
    SUM(total_clients),
    ROUND(SUM(total_clients) / (SELECT COUNT(*) FROM sessions)),
    SUM(profit)
FROM movies_stats;


/*
число посетителей и кассовые сборы,
сгруппированные по времени начала фильма:
с 9 до 15,
с 15 до 18,
с 18 до 21,
с 21 до 00:00 (сколько посетителей пришло с 9 до 15 часов и т.д.).
*/

WITH from_9_to_15 AS (
    SELECT
        tiket_price,
        id_tiket
    FROM sessions JOIN tikets USING (id_session)
    WHERE time_start::time >= '09:00:00'::time
    AND time_start::time < '15:00:00'::time
    ), from_15_to_18 AS (
SELECT
    tiket_price,
    id_tiket
FROM sessions JOIN tikets USING (id_session)
WHERE time_start::time >= '15:00:00'::time
  AND time_start::time < '18:00:00'::time
    ), from_18_to_21 AS (
SELECT
    tiket_price,
    id_tiket
FROM sessions JOIN tikets USING (id_session)
WHERE time_start::time >= '18:00:00'::time
  AND time_start::time < '21:00:00'::time
    ), from_21_to_00 AS (
SELECT
    tiket_price,
    id_tiket
FROM sessions JOIN tikets USING (id_session)
WHERE time_start::time >= '21:00:00'::time
    )
SELECT
    'From 9 to 15:' AS "time interval",
    COUNT(*) AS "amount_clients",
    SUM(tiket_price) AS "profit"
FROM from_9_to_15

UNION ALL
SELECT
    'From 15 to 18:' AS "time interval",
    COUNT(*) AS "amount_clients",
    SUM(tiket_price) AS "profit"
FROM from_15_to_18

UNION ALL
SELECT
    'From 18 to 21:' AS "time interval",
    COUNT(*) AS "amount_clients",
    SUM(tiket_price) AS "profit"
FROM from_18_to_21

UNION ALL
SELECT
    'From 21 to 00:' AS "time interval",
    COUNT(*) AS "amount_clients",
    SUM(tiket_price) AS "profit"
FROM from_21_to_00;