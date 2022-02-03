package restaurantapplication.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import restaurantapplication.core.Admin;
import restaurantapplication.db.AdminDAO;

import java.util.Objects;
import java.util.Optional;

public class AdminAuthenticator implements Authenticator<BasicCredentials, Admin> {

    private final AdminDAO adminDAO;
    private final SessionFactory sessionFactory;

    public AdminAuthenticator(AdminDAO adminDAO, SessionFactory sessionFactory) {
        this.adminDAO = adminDAO;
        this.sessionFactory = sessionFactory;
    }

    @UnitOfWork
    @Override
    public final Optional<Admin> authenticate(BasicCredentials credentials)
            throws AuthenticationException {
        Session session = sessionFactory.openSession();
        Optional<Admin> result;
        try {
            ManagedSessionContext.bind(session);

            result = adminDAO.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());

            if (!result.isPresent()) {
                return result;
            } else               {
                if (Objects.equals(credentials.getPassword(), result.get().getPassword())) {
                    return result;
                }
//                if (passwordEncryptor.checkPassword(
//                        credentials.getPassword(),
//                        result.get().getPassword())) {
//                    return result;
//                }
                else {
                    return Optional.empty();
                }
            }

        } catch (Exception e) {
            throw new AuthenticationException(e);
        } finally {
            ManagedSessionContext.unbind(sessionFactory);
            session.close();
        }

    }
}
