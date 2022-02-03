package restaurantapplication.core;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "core.Employee.findAll",
                query = "select e from Employee e"
        ),
        @NamedQuery(name = "core.Employee.findByName",
                query = "select e from Employee e "
                + "where e.firstName like :name "
                + "or e.lastName like :name"
        ),
        @NamedQuery(name = "core.Employee.remove",
                query = "delete from Employee e "
                + "where e.emp_id = :emp_id"
        )
})

public class Employee {

    // primary key auto generation

    @Column(name = "emp_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long emp_id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "e_mail")
    private String e_mail;



    @Column(name = "emp_position")
    private String position;


    public Employee() {
    }

    public Employee(long emp_id, String firstName, String lastName, String phone, String e_mail, String position) {
        this.emp_id = emp_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.e_mail = e_mail;
        this.position = position;
    }

    public long getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(long emp_id) {
        this.emp_id = emp_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return emp_id == employee.emp_id && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(phone, employee.phone) && Objects.equals(e_mail, employee.e_mail) && Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emp_id, firstName, lastName, phone, e_mail, position);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

}
