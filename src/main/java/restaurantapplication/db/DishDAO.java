package restaurantapplication.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import restaurantapplication.core.Dish;
import restaurantapplication.core.Employee;

import java.util.List;
import java.util.Optional;

public class DishDAO extends AbstractDAO<Dish> {

    public DishDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Dish> findAll() {
        return list(namedTypedQuery("core.Dish.findAll"));
    }

    public List<Dish> findByName(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list(
                namedTypedQuery("core.Dish.findByName")
                        .setParameter("name", builder.toString())
        );
    }

    public List<Dish> findByType(String name) {
        StringBuilder builder = new StringBuilder("%");
        builder.append(name).append("%");
        return list(
                namedTypedQuery("core.Dish.findByType")
                        .setParameter("name", builder.toString())
        );
    }

    public Dish create(Dish dish) {
        return persist(dish);
    }

    public Dish put(Dish dish) {
        return persist(dish);
    }

    public Optional<Dish> findById(long dish_id) {
        return Optional.ofNullable(get(dish_id));
    }

    public void delete(Long id) {
        namedQuery("core.Dish.remove")
                .setParameter("dish_id", id)
                .executeUpdate();
    }

}
