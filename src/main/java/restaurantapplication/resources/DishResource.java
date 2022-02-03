package restaurantapplication.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import restaurantapplication.core.Admin;
import restaurantapplication.core.Dish;
import restaurantapplication.core.Employee;
import restaurantapplication.db.DishDAO;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)

public class DishResource {

    private final DishDAO dishDAO;


    public DishResource(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    @GET
    @UnitOfWork
    public List<Dish> findByName(@QueryParam("name") Optional <String> name) {
        if (name.isPresent()) {
            return dishDAO.findByName(name.get());
        } else {
            return dishDAO.findAll();
        }
    }

    @PermitAll
    @GET
    @Path("/{dish_id}")
    @UnitOfWork
    public Optional<Dish> findById(@PathParam("dish_id") OptionalLong dish_id) {
        return dishDAO.findById(dish_id.getAsLong());
    }

    @POST
    @UnitOfWork
    public Dish createEmployee(@Valid Dish dish, @Auth Admin admin) {
        return dishDAO.create(dish);
    }

    @PUT
    @Path("/{dish_id}")
    @UnitOfWork
    public Response modifyDish(@PathParam("dish_id") Long dish_id, Dish dish,
                                   @Auth Admin admin) {

        if (dish != null) {
            dish.setDish_id(dish_id);
            dishDAO.put(dish);
            return Response.ok(dish).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();

    }

    @DELETE
    @Path("/{dish_id}")
    @UnitOfWork
    public Dish deleteDish(@PathParam("dish_id") OptionalLong dish_id, @Auth Admin admin) {
        Dish dish = findDishOrThrowException(dish_id,admin);
        dishDAO.delete(dish_id.getAsLong());
        return dish;
    }

    private Dish findDishOrThrowException(OptionalLong dish_id,
                                                  @Auth Admin admin) {
        Dish dish = dishDAO.findById(dish_id.getAsLong())
                .orElseThrow(()
                        -> new NotFoundException("Employee requested was not found."));
        return dish;
    }


}
