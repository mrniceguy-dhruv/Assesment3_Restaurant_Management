package restaurantapplication.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import restaurantapplication.core.Admin;
import restaurantapplication.core.Employee;
import restaurantapplication.db.AdminDAO;
import restaurantapplication.db.EmployeeDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final EmployeeDAO employeeDAO;
    private final AdminDAO adminDAO;

    public EmployeeResource(EmployeeDAO employeeDAO, AdminDAO adminDAO) {
        this.employeeDAO = employeeDAO;
        this.adminDAO = adminDAO;
    }

    @GET
    @UnitOfWork
    public List<Employee> findByName(
            @QueryParam("name") Optional<String> name, @Auth Admin admin
    ) {
        if (name.isPresent()) {
            return employeeDAO.findByName(name.get());
        } else {
            return employeeDAO.findAll();
        }
    }

    @GET
    @Path("/{emp_id}")
    @UnitOfWork
    public Optional<Employee> findById(@PathParam("emp_id") OptionalLong emp_id, @Auth Admin admin) {
        return employeeDAO.findById(emp_id.getAsLong());
    }

    @POST
    @UnitOfWork
    public Employee createEmployee(@Valid Employee employee,@Auth Admin admin) {
        return employeeDAO.create(employee);
    }

    @PUT
    @Path("/{emp_id}")
    @UnitOfWork
    public Response modifyDish(@PathParam("emp_id") Long emp_id, Employee employee,
                                   @Auth Admin admin) {

        if (employee != null) {
            employee.setEmp_id(emp_id);
            employeeDAO.put(employee);
            return Response.ok(employee).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @DELETE
    @Path("/{emp_id}")
    @UnitOfWork
    public Employee deleteEmployee(@PathParam("emp_id") OptionalLong emp_id, @Auth Admin admin) {
        Employee employee= findEmployeeOrThrowException(emp_id,admin);
        employeeDAO.delete(emp_id.getAsLong());
        return employee;
    }

    private Employee findEmployeeOrThrowException(OptionalLong id,
                                                  @Auth Admin admin) {
        Employee employee = employeeDAO.findById(id.getAsLong())
                .orElseThrow(()
                        -> new NotFoundException("Employee requested was not found."));
        return employee;
    }

}
