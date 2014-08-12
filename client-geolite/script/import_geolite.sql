delete from geolite_block;
delete from geolite_loc;
alter table geolite_block auto_increment = 1;


load data infile 'GeoLiteCity-Blocks.csv'
INTO TABLE geolite_block
CHARACTER SET 'utf8'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY '\\'
LINES TERMINATED BY '\n' STARTING BY ''
IGNORE 2 LINES
(ip_from,ip_to,block);

load data infile 'GeoLiteCity-Location.csv'
INTO TABLE geolite_loc
CHARACTER SET 'utf8'
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' ESCAPED BY '\\'
LINES TERMINATED BY '\n' STARTING BY ''
IGNORE 2 LINES
(id,country,state,city,zip_code,latitude,longitude,@dummy,@dummy);