package com.venhancer.employee.management.system.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private Long employeeId;

    @OneToOne(mappedBy = "employeeProfile", cascade = CascadeType.ALL)
    private Employee employee;

    public EmployeeProfile(Employee employee){
        this.employee = employee;
        this.email = null;
        this.phoneNumber = null;
        this.address = null;
    }

}
