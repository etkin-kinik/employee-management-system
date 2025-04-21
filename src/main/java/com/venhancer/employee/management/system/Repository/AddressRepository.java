package com.venhancer.employee.management.system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venhancer.employee.management.system.Entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
