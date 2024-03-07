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

ALTER TABLE user
ADD COLUMN dob DATE,
ADD COLUMN gender VARCHAR(1),
ADD COLUMN hair_color VARCHAR(20),
ADD COLUMN eye_color VARCHAR(20),
ADD COLUMN height DECIMAL(5,2),
ADD COLUMN weight DECIMAL(5,2),
ADD COLUMN identification_mark TEXT,
ADD COLUMN ec_name VARCHAR(100),
ADD COLUMN ec_relationship VARCHAR(50),
ADD COLUMN ec_number VARCHAR(20),
ADD COLUMN address VARCHAR(255),
ADD COLUMN city VARCHAR(100),
ADD COLUMN state VARCHAR(100),
ADD COLUMN zip VARCHAR(20),
ADD COLUMN country VARCHAR(100),
ADD COLUMN allergy_name VARCHAR(100),
ADD COLUMN allergy_notes TEXT,
ADD COLUMN medicine_name VARCHAR(100),
ADD COLUMN medicine_notes TEXT,
ADD COLUMN medicine_dosage DECIMAL(5,2),
ADD COLUMN medicine_dosage_unit VARCHAR(20),
ADD COLUMN medicine_frequency INT,
ADD COLUMN medicine_frequency_type VARCHAR(20),
ADD COLUMN insurance_name VARCHAR(100),
ADD COLUMN insurance_notes TEXT,
ADD COLUMN medical_condition_name VARCHAR(100),
ADD COLUMN medical_condition_notes TEXT,
ADD COLUMN registration_dttm DATE,
ADD COLUMN is_verified BOOLEAN,
ADD COLUMN is_active BOOLEAN,
ADD COLUMN profile_photo VARCHAR(255),
ADD COLUMN last_login_dttm DATE,
ADD COLUMN is_banned BOOLEAN,
ADD COLUMN middle_name VARCHAR(255),
ADD COLUMN user_type VARCHAR(255);

select * from user;
delete from user where id >35;