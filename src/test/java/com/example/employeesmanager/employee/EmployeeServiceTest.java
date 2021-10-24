package com.example.employeesmanager.employee;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Tests for the database")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository);
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
            changedEmployee = new Employee("John", "Smith", initialEmail, 10000);
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
    @DisplayName("Tests for removing from the database")
    class RemoveTests {

    }

    @Nested
    @DisplayName("Tests for add command")
    class AddTests {

        @Test
        @DisplayName("Proper use of add command")
        void proper_add_command_test() {
            Employee John = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            employeeService.addEmployee(John);
            assertThat(employeeService.getEmployee(1L)).isEqualTo(John);
        }

        @Test
        @DisplayName("Improper use of add command - duplicates")
        void get_command_with_improper_data() {
            assertThatThrownBy(() -> {
                Employee John = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
                employeeService.addEmployee(John);
                employeeService.addEmployee(John);
            }).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee already exists");
        }

    }

    @Nested
    @DisplayName("Tests for get command")
    class GetTests {

        Employee John, Paul, Candice;

        @BeforeEach
        void updateTestInit() {
            John = new Employee("John", "Smith", "smith@gmail.com", 5000);
            employeeService.addEmployee(new Employee("Paul", "Smith", "paul@gmail.com", 9000));
            employeeService.addEmployee(new Employee("Candice", "Smith", "candice@gmail.com", 9000));
        }

        @Test
        @DisplayName("Proper use of get command")
        void proper_get_command_test() {
            Employee gottenEmployee = employeeService.getEmployee(1L);
            assertThat(gottenEmployee).isEqualTo(John);
        }

        @Test
        @DisplayName("Improper use of get command - wrong id number")
        void get_command_with_improper_data() {
            assertThatThrownBy(() -> {
                Employee gottenEmployee = employeeService.getEmployee(4L);
            }).isInstanceOf(IllegalStateException.class).hasMessageContaining("4");
        }

        @Test
        void get_all_command() {
            List<Employee> gottenList = employeeService.getAllEmployees();
            assertThat(gottenList.size()).isEqualTo(3);
        }
    }
}
