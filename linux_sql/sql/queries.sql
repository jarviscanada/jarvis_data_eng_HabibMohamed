SELECT
  cpu_number,
  host_id,
  total_mem
FROM
  (
    SELECT
      cpu_number,
      id AS host_id,
      total_mem,
      rank() over(
        ORDER BY
          total_mem desc
      ) ranks
    FROM
      host_info hi
    GROUP BY
      cpu_number,
      id
  ) AS results;
SELECT
  host_id,
  hostname,
  (
    date_trunc('hour', hu.timestamp) + date_part('minute', hu.timestamp):: int / 5 * interval '5 min'
  ) AS time_stamp,
  AVG(hi.total_mem - hu.memory_free) AS avg_used_mem_percentage
FROM
  host_info hi
  inner join host_usage hu on hi.id = hu.host_id
GROUP BY
  host_id,
  hostname,
  time_stamp
ORDER BY
  time_stamp;
SELECT
  host_id,
  (
    date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min'
  ) AS time_stamp,
  COUNT(*) AS num_of_jobs
FROM
  host_usage hu
GROUP BY
  host_id,
  time_stamp
HAVING
  COUNT(*) < 3;