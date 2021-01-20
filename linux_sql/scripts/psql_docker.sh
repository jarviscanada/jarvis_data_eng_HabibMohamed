#!/bin/bash

#Assigning bash arguments to variables
command=$1
db_username=$2
db_password=$3

# Check if docker is up and running. If not, start it up.
sudo systemctl status docker || systemctl start docker

#Check what command the first argument contains
case $command in
  create)

    #For the create command, we first check if there isn't already a container
    count=$(docker container ls -a -f name=jrvs-psql | wc -l)
    if ((count >= 2)); then
      echo "Container has already been created."
      exit 1
    fi

    #We check if the database password has been provided as arguments
    if [ -z "$2" ] || [ -z "$3" ]
    then
      echo "Please provide database username and password when using the create command."
      exit 1
    fi

    #We now create a volume for our postgres database
    docker volume create pgdata

    #We now create the postgres container
    docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres

    exit $?

  ;;
  start)

	#For this command, simply start up the docker container
    docker container start jrvs-psql
    exit $?

  ;;
  stop)

	#For this command, simply stop the docker container
    docker container stop jrvs-psql
    exit $?

  ;;
  *)

	#All other input is handled appropriately
    echo "To use this program, the commands are: "
    echo "psql_docker.sh create db_username db_password"
    echo "psql_docker.sh start"
    echo "psql_docker.sh stop"
    exit 1
  ;;
esac

exit 0


