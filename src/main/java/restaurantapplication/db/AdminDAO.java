package restaurantapplication.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import restaurantapplication.core.Admin;

import java.util.List;
import java.util.Optional;

public class AdminDAO extends AbstractDAO<Admin> {

    public AdminDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Admin> findAll() {
        return list(namedTypedQuery("Admin.findAll"));
    }

    public Optional<Admin> findByUsernameAndPassword(
            String username,
            String password
    ) {
        return Optional.ofNullable(
                uniqueResult(
                        namedTypedQuery("Admin.findByUsernameAndPassword")
                                .setParameter("username", username)
                                .setParameter("password", password)
                ));
    }

    public Optional<Admin> findById(Integer ad_id) {
        return Optional.ofNullable(get(ad_id));
    }

    public Optional<Admin> findByUsername(String username) {
        return Optional.ofNullable(
                uniqueResult(
                        namedTypedQuery("Admin.findByUsername")
                                .setParameter("username", username)
                ));
    }
}
