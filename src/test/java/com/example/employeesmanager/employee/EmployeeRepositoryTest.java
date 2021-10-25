package com.example.employeesmanager.employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findEmployeeByEmailIfExists() {
        // given
        String email = "jsmith@example.com";
        Employee employee = new Employee("John", "Smith", email, 5000);
        underTest.save(employee);

        // when
        Optional<Employee> employeeFromRepoOptional = underTest.findEmployeeByEmail(email);
        boolean isInRepo = employeeFromRepoOptional.isPresent();
        Employee employeeFromRepo = null;
        if (isInRepo) {
            employeeFromRepo = employeeFromRepoOptional.get();
        }
        //then
        assertThat(isInRepo).isTrue();
        assertThat(employeeFromRepo).isEqualTo(employee);
    }

    @Test
    void findEmployeeByEmailIfDoesNotExist() {
        // given
        String email = "jsmith@example.com";

        // when
        Optional<Employee> employeeFromRepoOptional = underTest.findEmployeeByEmail(email);
        boolean isInRepo = employeeFromRepoOptional.isPresent();

        //then
        assertThat(isInRepo).isFalse();
    }
    
}