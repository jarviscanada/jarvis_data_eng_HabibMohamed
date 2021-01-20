#!/bin/bash

#Check if enough arguments were passed in
if [ $# -ne 5 ]; then
	echo "host_info.sh should have 5 arguments. For example: "
	echo "./host_info psql_host psql_port db_name psql_user psql_password"
	exit 1
fi

#Saving the arguments into variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
export PGPASSWORD=$5

#Preliminary command for simpler data retrieval
lscpu_out=$(lscpu)

#Grabbing device specs and storing them into variables
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | head -n1 | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "Model [a-z]+:" | cut -d ":" -f 2 | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "CPU [A-Z]+[a-z]:" | awk '{print $3}' | xargs)
l2_cached=$(echo "$lscpu_out" | egrep "L2 [a-z]+:" | awk '{print $3}' | xargs)
l2_cache=$(echo "${l2_cached%?}")
total_mem=$(free --kilo | grep Mem: | awk '{print $2}' | xargs)
timestamp=$(date "+%Y-%m-%d %T")

#Building the insert query
insert_query="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
  VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $l2_cache, $total_mem, '$timestamp')"

#Run insert query
psql -h $psql_host -U $psql_user -d $db_name -p $psql_port -c "$insert_query"

exit $?