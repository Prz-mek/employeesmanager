package com.example.employeesmanager.employee;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("Tests for the database")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    private AutoCloseable autoCloseable;
    private EmployeeService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EmployeeService(employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Nested
    @DisplayName("Tests for add command")
    class AddTests {

        @Test
        @DisplayName("Proper use of add command")
        void add_command_with_proper_data() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            underTest.addEmployee(employee);
            ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
            verify(employeeRepository).save(employeeArgumentCaptor.capture());
            Employee capturedEmployee = employeeArgumentCaptor.getValue();
            assertThat(capturedEmployee).isEqualTo(employee);
        }

        @Test
        @DisplayName("Improper use of add command - duplicates")
        void add_command_with_duplicate() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(java.util.Optional.of(employee));
            assertThatThrownBy(() -> underTest.addEmployee(employee)).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee already exists");
            verify(employeeRepository, never()).save(any());
        }

        @Test
        @DisplayName("Improper use of add command - multiple workers with the same email")
        void add_command_with_email_already_taken() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            underTest.addEmployee(employee);
            Employee employee2 = new Employee(2L, "Paul", "Nowak", "jonkowalski@gmail.com", 4000);
            given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(java.util.Optional.of(employee));
            assertThatThrownBy(() -> underTest.addEmployee(employee2)).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee already exists");
        }
    }


    @Nested
    @DisplayName("Tests for get command")
    class GetTests {

        @Test
        @DisplayName("Proper use of get command")
        void get_command_with_proper_data() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            given(employeeRepository.findById(employee.getId())).willReturn(java.util.Optional.of(employee));
            assertThat(underTest.getEmployee(employee.getId())).isEqualTo(employee);
        }

        @Test
        @DisplayName("Improper use of get command - wrong id number")
        void get_command_with_wrong_id() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            assertThatThrownBy(() -> underTest.getEmployee(employee.getId())).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee with id " + employee.getId() + " does not exist");
        }

        @Test
        @DisplayName("Proper use of getAll command")
        void get_all_command() {
            underTest.getAllEmployees();
            verify(employeeRepository).findAll();
        }
    }


    @Nested
    @DisplayName("Tests for update command")
    class UpdateTests {

        @Test
        @DisplayName("Proper use of update command")
        void update_command_with_proper_data() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            given(employeeRepository.existsById(employee.getId())).willReturn(true);
            underTest.updateEmployee(employee);
            verify(employeeRepository).save(employee);
        }

        @Test
        @DisplayName("Improper use of update command - employee with same email exists")
        void upgrade_command_with_email_already_taken() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            Employee otherEmployee = new Employee(2L, "Jonkow", "Alski", "jonkowalski@gmail.com", 6000);
            given(employeeRepository.findEmployeeByEmail(employee.getEmail())).willReturn(java.util.Optional.of(otherEmployee));
            assertThatThrownBy(() -> underTest.updateEmployee(employee)).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee with the same email already exists");
            verify(employeeRepository, never()).save(any());
        }

        @Test
        @DisplayName("Improper use of update command - employee does not exist")
        void upgrade_command_with_employee_not_existing() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            given(employeeRepository.existsById(employee.getId())).willReturn(false);
            assertThatThrownBy(() -> underTest.updateEmployee(employee)).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee with id " + employee.getId() + " does not exist");
        }
    }


    @Nested
    @DisplayName("Tests for remove command")
    class RemoveTests {

        @Test
        @DisplayName("Proper use of remove command")
        void remove_command_with_proper_data() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            given(employeeRepository.existsById(employee.getId())).willReturn(true);
            underTest.deleteEmployee(employee.getId());
            verify(employeeRepository).deleteById(employee.getId());
        }

        @Test
        @DisplayName("Improper use of remove command - employee does not exist")
        void remove_command_with_employee_not_existing() {
            Employee employee = new Employee(1L, "John", "Kowalski", "jonkowalski@gmail.com", 3000);
            assertThatThrownBy(() -> underTest.deleteEmployee(employee.getId())).isInstanceOf(IllegalStateException.class).hasMessageContaining("Employee with id " + employee.getId() + " does not exist");
        }
    }
}
