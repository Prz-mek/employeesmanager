INSERT INTO employee (id, first_name, last_name, email, salary)
VALUES (nextval('employee_sequence'), 'Piotr', 'Nowak', 'piotr.nowak@gmail.com', '3000'), 
    (nextval('employee_sequence'), 'Jan', 'Kowalski', 'jan.kowalski@outlook.com', '5000'),
    (nextval('employee_sequence'), 'Damian', 'Wójcik', 'd.woj@o2.pl', '3800'),
    (nextval('employee_sequence'), 'Alan', 'Partyka', 'alan1998@gmail.com', '2800');