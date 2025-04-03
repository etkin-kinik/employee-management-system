package com.venhancer.employee.management.system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.venhancer.employee.management.system.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT e.* FROM employee e JOIN department d ON e.department_id = d.id WHERE d.name ILIKE CONCAT('%', :departmentName, '%')", nativeQuery = true)
    List<Employee> findByDepartmentName(@Param("departmentName") String departmentName);

}
