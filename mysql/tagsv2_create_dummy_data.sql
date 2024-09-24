
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
    'example@example.com', 
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


SET @userId = LAST_INSERT_ID();

- 
INSERT INTO emergency_contact (user_id, contact_name, contact_phone)
VALUES 
    (@userId, 'Jane Doe', '9876543210'),
    (@userId, 'Bob Smith', '5555555555');


INSERT INTO allergies (user_id, allergy_name)
VALUES 
    (@userId, 'Peanuts'),
    (@userId, 'Penicillin');


INSERT INTO med_info (user_id, info_detail)
VALUES 
    (@userId, 'Diabetic'),
    (@userId, 'Asthma');


INSERT INTO insurance_information (user_id, insurance_provider, policy_number)
VALUES 
    (@userId, 'Health Insurance Co.', 'ABC123456');

INSERT INTO medical_conditions (user_id, condition_name)
VALUES 
    (@userId, 'Hypertension'),
    (@userId, 'Cholesterol');
