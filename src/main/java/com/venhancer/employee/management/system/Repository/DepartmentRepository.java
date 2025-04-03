package com.venhancer.employee.management.system.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.venhancer.employee.management.system.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT d.* FROM department d WHERE d.name ILIKE CONCAT('%', :departmentName, '%')", nativeQuery = true)
    Department findDepartmentByDepartmentName(@Param("departmentName") String departmentName);

}
