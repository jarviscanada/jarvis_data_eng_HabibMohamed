-- Show table schema 
\d + retail;
-- Show first 10 rows
SELECT
    *
FROM
    retail
        limit
    10;
-- Check # of records
SELECT
    COUNT(*) AS count
FROM
    retail;
-- number of clients (e.g. unique client ID)
SELECT
    COUNT(*) AS count
FROM
    (
    SELECT
    DISTINCT customer_id
    FROM
    retail
    WHERE
    customer_id IS NOT NULL
    GROUP BY
    customer_id
    ) AS tble;
-- earliest and latest dates
SELECT
    MAX(invoice_date) AS maximum,
    MIN(invoice_date) AS minimum
FROM
    retail;
-- Number of SKUs
SELECT
    COUNT(*) AS count
FROM
    (
    SELECT
    DISTINCT stock_code
    FROM
    retail
    GROUP BY
    stock_code
    ) AS tble;
-- Average of invoices
select
    avg(qty) as average
from
    (
        select
            sum(quantity * unit_price) as qty
        from
            retail
        group by
            invoice_no
        having
                sum(quantity * unit_price) > 0
    ) as subq;
-- Total revenue
SELECT
    SUM(quantity * unit_price) AS revenue
FROM
    retail;
-- Total revenue by month
SELECT
        CAST(
                EXTRACT(
                        'year'
                        FROM
                        invoice_date
                    ) AS VARCHAR
            ) || CAST(
                EXTRACT(
                        'month'
                        FROM
                        invoice_date
                    ) AS VARCHAR
            ) AS yyyymm,
        SUM(quantity * unit_price) AS invoice
FROM
    retail
GROUP BY
    yyyymm;

