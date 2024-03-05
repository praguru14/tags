use tags;
CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    blood_group VARCHAR(5) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (phone)
);
CREATE TABLE login (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user(id)
);

