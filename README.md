# Book My Sport

Book My Sports is a Java(play) application that allows users to register for events on Sports Day.

## Language/ Framework

- Play Framework 2.8.15
- Java 1.8

## Build Tool

SBT is the built tool used for this Java-based project. Some useful commands are listed below


## Docker Setup
1. Change Database connection endpoint in `application.conf` file 


If the database server is running on the host machine, need to use the host's IP address


change `jdbc:mysql://localhost:3306` to `jdbc:mysql://host.docker.internal:3306`

2. Build Image:

`docker build -t book-my-sport-service .`

3. Run Docker Image:

`docker run -p 9001:9001 book-my-sport-service`


### IDE 

You may use Eclipse as your IDE. Use the following command to create Eclipse project files 

```
sbt eclipse
```

### Compile Service

```
sbt clean compile
```

### Start Service

```
sbt run // uses port 9000, by default

OR 

sbt "run 9001" // uses user specified port, 9001 for this example
```

### Debugging 

```
 sbt -jvm-debug 9999 "run 9001" // starts debugger on port 9999 and application on port 9001
```
## Database

- DBMS: MySQL
- Database: bookmysport
- Tables:

```
CREATE TABLE user (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```
CREATE TABLE event_type (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category` varchar(64) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_UNIQUE` (`category`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```
CREATE TABLE events (
    id int(10) NOT NULL AUTO_INCREMENT,
    event_name varchar(255) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    event_type_id int(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (event_type_id) REFERENCES event_type(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```
CREATE TABLE user_event_registration (
    id INT(10) NOT NULL AUTO_INCREMENT,
    user_id INT(10) NOT NULL,
    event_id INT(10) NOT NULL,
    created_at timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Adding unique index to tables

```
 ALTER TABLE `client_plugin` ADD UNIQUE `client_plugin_unique_index`(`client`, `feature_id`);
 ALTER TABLE `early_access` ADD UNIQUE `early_access_unique_index`(`client`, `feature_id`);
```

Insert Commands for testing purposes:
```
INSERT INTO user (`name`, `email`) VALUES ('test test', 'test@gmail.com');
```

```
INSERT INTO event_type (`category`, `image`)
VALUES 
('Boxing', 'https://thumbs2.imgbox.com/7c/61/VTNdTwPU_t.jpeg'),
('Swimming', 'https://thumbs2.imgbox.com/c5/40/AEWGBSWx_t.jpeg'),
('Athelics', 'https://thumbs2.imgbox.com/b6/06/TqWLEGpN_t.jpeg'),
('Badminton', 'https://thumbs2.imgbox.com/1b/38/QJkFnIs6_t.jpeg'),
('General', 'https://thumbs2.imgbox.com/2c/88/NmFSaHNg_t.jpg');
```

```
INSERT INTO events (`event_name`, `start_time`, `end_time`, `event_type_id`)
VALUES
	  ('Butterfly 100M', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 2),
    ('Triple Jump', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 3),
    ('Middleweight 75 kg', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 1),
    ('Badmintion Doubles', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 4),
    ('Freestyle 400M', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 2),
    ('100M Sprint', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 3),
    ('Heavyweight 91kg', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 1),
    ('Long Jump', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 3),
    ('Lightweight 60kg', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 1),
    ('High Jump', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 3),
    ('Backstroke 100M', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 2),
    ('Badmintion Singles', '2023-12-17 13:00:00', '2023-12-17 14:00:00', 4);

```

## Frontend Code Repo
Link: https://github.com/Akanksha872/book-my-sports


## Keywords

Java, Play, Backend, Database, MySQL