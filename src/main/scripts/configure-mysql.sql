## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE szu_dev;
CREATE DATABASE szu_prod;

#Create database service accounts
CREATE USER 'szu_dev_user'@'localhost' IDENTIFIED BY 'zxczxc';
CREATE USER 'szu_prod_user'@'localhost' IDENTIFIED BY 'zxczxc';
CREATE USER 'szu_dev_user'@'%' IDENTIFIED BY 'zxczxc';
CREATE USER 'szu_prod_user'@'%' IDENTIFIED BY 'zxczxc';

#Database grants
GRANT SELECT ON szu_dev.* to 'szu_dev_user'@'localhost';
GRANT INSERT ON szu_dev.* to 'szu_dev_user'@'localhost';
GRANT DELETE ON szu_dev.* to 'szu_dev_user'@'localhost';
GRANT UPDATE ON szu_dev.* to 'szu_dev_user'@'localhost';
GRANT SELECT ON szu_prod.* to 'szu_prod_user'@'localhost';
GRANT INSERT ON szu_prod.* to 'szu_prod_user'@'localhost';
GRANT DELETE ON szu_prod.* to 'szu_prod_user'@'localhost';
GRANT UPDATE ON szu_prod.* to 'szu_prod_user'@'localhost';
GRANT SELECT ON szu_dev.* to 'szu_dev_user'@'%';
GRANT INSERT ON szu_dev.* to 'szu_dev_user'@'%';
GRANT DELETE ON szu_dev.* to 'szu_dev_user'@'%';
GRANT UPDATE ON szu_dev.* to 'szu_dev_user'@'%';
GRANT SELECT ON szu_prod.* to 'szu_prod_user'@'%';
GRANT INSERT ON szu_prod.* to 'szu_prod_user'@'%';
GRANT DELETE ON szu_prod.* to 'szu_prod_user'@'%';
GRANT UPDATE ON szu_prod.* to 'szu_prod_user'@'%';