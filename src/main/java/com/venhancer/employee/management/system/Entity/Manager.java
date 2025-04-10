package com.venhancer.employee.management.system.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.PERSIST)
    private List<Employee> employee;

    @OneToOne(mappedBy = "manager")
    private Users users;
}
