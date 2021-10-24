package com.example.employeesmanager.employee;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests for the database")
class EmployeeServiceTest {

    @Autowired
    private static EmployeeRepository underTest;
    private static EmployeeService employeeService;

    @BeforeAll
    static void prepareTests() {
        employeeService = new EmployeeService(underTest);
    }

    @Test
    void testszybki() {
        assertThat(1).isEqualTo(1);
    }

    @Nested
    @DisplayName("Tests for updating the database")
    class UpdateTests {
        String initialEmail;
        Employee initialEmployee, changedEmployee;

        @BeforeEach
        void updateTestInit() {
            initialEmail = "jsmith@example.com";
            initialEmployee = new Employee("John", "Smith", initialEmail, 5000);
            changedEmployee = new Employee("John", "Smith",initialEmail, 10000);
            employeeService.addEmployee(initialEmployee);
        }

        @Test
        void updateTest() {
            employeeService.updateEmployee(changedEmployee);


        }

        @AfterEach
        void updateTestTerminate() {

        }
        @Nested
        class UpdateTestWhenX {

        }
    }

    @Nested
    @DisplayName("Tests for get command")
    class GetTests{

        Employee John, Paul, Candice;

        @BeforeEach
        void updateTestInit() {
            John = new Employee("John", "Smith", "smith@gmail.com", 5000);
            employeeService.addEmployee(John);
            employeeService.addEmployee(new Employee("Paul", "Smith", "paul@gmail.com", 9000));
            employeeService.addEmployee(new Employee("Candice", "Smith", "candice@gmail.com", 9000));
        }
        @Test
        void getTest(){
            Employee gottenEmployee = employeeService.getEmployee(1L);
            assertThat(gottenEmployee).isEqualTo(John);
        }
    }
}
