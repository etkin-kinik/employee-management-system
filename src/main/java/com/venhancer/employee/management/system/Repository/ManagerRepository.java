package com.venhancer.employee.management.system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.venhancer.employee.management.system.Entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{

    @Query(value = "SELECT m.* FROM manager m JOIN department d ON m.department_id = d.id WHERE d.name ILIKE CONCAT('%', :departmentName, '%')", nativeQuery = true)
    Manager findByDepartmentName(@Param("departmentName") String departmentName);


}
