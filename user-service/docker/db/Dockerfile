FROM mysql:5.6

ENV MYSQL_ROOT_PASSWORD=root

ADD my.cnf /etc/mysql/my.cnf
COPY create-schema.sql /docker-entrypoint-initdb.d/