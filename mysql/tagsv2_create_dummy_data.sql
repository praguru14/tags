-- Step 1: Insert into users
INSERT INTO users (
    email, 
    phone, 
    name, 
    blood_group, 
    dob, 
    gender, 
    registration_dttm, 
    is_verified, 
    is_active, 
    last_login_dttm, 
    is_banned, 
    user_type
) VALUES (
    'john.doe@example.com', 
    '1234567890', 
    'John Doe', 
    'O+', 
    '1990-01-01', 
    'M', 
    NOW(), 
    TRUE, 
    TRUE, 
    NOW(), 
    FALSE, 
    'Regular'
);

-- Step 2: Get the last inserted user ID
SET @userId = LAST_INSERT_ID();

-- Step 3: Insert into emergency_contacts
INSERT INTO emergency_contact (user_id, name, phone, relationship)
VALUES 
    (@userId, 'Jane Doe', '9876543210', 'Sister'),
    (@userId, 'Bob Smith', '5555555555', 'Friend');

-- Step 4: Insert into allergies
INSERT INTO allergy (user_id, name, severity)
VALUES 
    (@userId, 'Peanuts', 'High'),
    (@userId, 'Penicillin', 'Moderate');

-- Step 5: Insert into med_info
INSERT INTO med_info (user_id, medication, dosage)
VALUES 
    (@userId, 'Lisinopril', '10 mg'),
    (@userId, 'Metformin', '500 mg');

-- Step 6: Insert into insurance_information
INSERT INTO insurance_information (user_id, insurance_name, insurance_notes)
VALUES 
    (@userId, 'Health Insurance Co.', 'Policy covers all major illnesses.');

-- Step 7: Insert into medical_conditions
INSERT INTO medical_condition (user_id, condition_name, severity, description)
VALUES 
    (@userId, 'Hypertension', 'Moderate', 'High blood pressure condition.'),
    (@userId, 'Cholesterol', 'High', 'Elevated cholesterol levels.');

SELECT 
    u.id AS user_id,
    u.blood_group,
    u.dob,
    u.email,
    u.gender,
    u.image_data,
    u.is_active,
    u.is_banned,
    u.is_verified,
    u.last_login_dttm,
    u.name AS user_name,
    u.phone AS user_phone,
    u.registration_dttm,
    u.user_type,
    ec.id AS emergency_contact_id,
    ec.name AS emergency_contact_name,
    ec.phone AS emergency_contact_phone,
    ec.relationship AS emergency_contact_relationship,
    a.id AS allergy_id,
    a.name AS allergy_name,
    a.severity AS allergy_severity,
    mi.id AS med_info_id,
    mi.dosage AS med_info_dosage,
    mi.medication AS med_info_medication,
    ii.id AS insurance_info_id,
    ii.insurance_name AS insurance_info_name,
    ii.insurance_notes AS insurance_info_notes,
    mc.id AS medical_condition_id,
    mc.condition_name AS medical_condition_name,
    mc.description AS medical_condition_description,
    mc.severity AS medical_condition_severity
FROM 
    users u
LEFT JOIN 
    emergency_contact ec ON ec.user_id = u.id
LEFT JOIN 
    allergy a ON a.user_id = u.id
LEFT JOIN 
    med_info mi ON mi.user_id = u.id
LEFT JOIN 
    insurance_information ii ON ii.user_id = u.id
LEFT JOIN 
    medical_condition mc ON mc.user_id = u.id;
    
    use tags;
    
    select * from 
    
    truncate users;
    truncate allergy;
    truncate insurance_information;
    truncate med_info;
    truncate medical_condition;
    truncate emergency_contact;
    
    delete from emergency_contact where id = 5;
    delete from users where id =5;
