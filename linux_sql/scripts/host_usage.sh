#!/bin/bash

#Check if enough arguments were passed in
if [ $# -ne 5 ]; then
	echo "host_usage.sh should have 5 arguments. For example: "
	echo "./host_usage psql_host psql_port db_name psql_user psql_password"
fi

#Saving the arguments into variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
export PGPASSWORD=$5

#Preliminary command for simpler data retrieval and insertion
top_calc=$(top -bn1)
hostname=$(hostname -f)
timestamp=$(date "+%Y-%m-%d %T")

#Grabbing device specs and storing them into variables
memory_free=$(free --mega | grep Mem: | awk '{print $4}' | xargs)
cpu_idle=$(echo "$top_calc" | egrep "%Cpu" | awk '{print $8}')
cpu_kernel=$(echo "$top_calc" | egrep "%Cpu" | awk '{print $4}')
disk_io=$(vmstat -d | tail -n1 | awk '{print $10}' | xargs)
disk_available=$(df -m | egrep "/dev/sda2" | awk '{print $4}' | xargs)

#Building the insert query
insert_query="INSERT INTO host_usage
  (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
  SELECT
  '$timestamp', info.id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available
	FROM
		host_info info
	WHERE
		info.hostname = '$hostname'
	"

echo "$insert_query"

#Run insert query
psql -h $psql_host -U $psql_user -d $db_name -p $psql_port -c "$insert_query"