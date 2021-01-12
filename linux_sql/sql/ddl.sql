\c host_agent; -- Connects to database just in case
CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
  id SERIAL PRIMARY KEY,
  hostname VARCHAR UNIQUE NOT NULL,
  cpu_number INTEGER NOT NULL,
  cpu_architecture VARCHAR NOT NULL,
  cpu_model VARCHAR NOT NULL,
  cpu_mhz FLOAT(3) NOT NULL,
  L2_cache INTEGER NOT NULL,
  total_mem INTEGER NOT NULL,
  "timestamp" TIMESTAMP NOT NULL
);
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
  "timestamp" TIMESTAMP NOT NULL,
  host_id SERIAL NOT NULL,
  memory_free INTEGER NOT NULL,
  cpu_idle FLOAT(2) NOT NULL,
  cpu_kernel FLOAT(2) NOT NULL,
  disk_io INTEGER NOT NULL,
  disk_available INTEGER NOT NULL,
  CONSTRAINT machine_id FOREIGN KEY(host_id) REFERENCES host_info(id)
);