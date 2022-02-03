package restaurantapplication.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import restaurantapplication.core.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO extends AbstractDAO<Employee> {

    // hibernate purpose

    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    // implemented methods
    public List <Employee> findAll() {
        return list(namedTypedQuery("core.Employee.findAll"));
    }

    public List<Employee> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list(
                namedTypedQuery("core.Employee.findByName")
                        .setParameter("name", builder.toString())
        );
    }

    public Employee create(Employee employee) {
        return persist(employee);
    }

    public Optional<Employee> findById(long emp_id) {
        return Optional.ofNullable(get(emp_id));
    }

    public void delete(Long id) {
        namedQuery("core.Employee.remove")
                .setParameter("emp_id", id)
                .executeUpdate();
    }

    public Employee put(Employee employee) {
        return persist(employee);
    }
}
