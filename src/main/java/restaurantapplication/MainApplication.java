package restaurantapplication;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;
import restaurantapplication.auth.AdminAuthenticator;
import restaurantapplication.core.Admin;
import restaurantapplication.core.Dish;
import restaurantapplication.core.Employee;
import restaurantapplication.db.AdminDAO;
import restaurantapplication.db.DishDAO;
import restaurantapplication.db.EmployeeDAO;
import restaurantapplication.resources.DishResource;
import restaurantapplication.resources.EmployeeResource;

public class MainApplication extends Application<MyConfiguration> {


    private final HibernateBundle<MyConfiguration> hibernateBundle
            = new HibernateBundle<MyConfiguration>(
            Employee.class, Dish.class, Admin.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(MyConfiguration myConfiguration) {
            return myConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(
            final Bootstrap<MyConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(MyConfiguration myConfiguration, Environment environment) throws Exception {

        final EmployeeDAO employeeDAO
                = new EmployeeDAO(hibernateBundle.getSessionFactory());

        final DishDAO dishDAO
                = new DishDAO(hibernateBundle.getSessionFactory());

        final AdminDAO adminDAO
                = new AdminDAO(hibernateBundle.getSessionFactory());

        final AdminAuthenticator authenticator
                = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(AdminAuthenticator.class,
                        new Class<?>[]{AdminDAO.class, SessionFactory.class},
                        new Object[]{adminDAO,
                                hibernateBundle.getSessionFactory()});

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<Admin>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(new Authorizer<Admin>() {
                            @Override
                            public boolean authorize(Admin principal, String role) {
                                return true;
                            }
                        })
                        .setRealm("SECURITY REALM")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //Necessary if @Auth is used to inject a custom Principal
        // type into your resource
        environment.jersey().register(
                new AuthValueFactoryProvider.Binder<>(Admin.class));

        environment.jersey().register(new EmployeeResource(employeeDAO, adminDAO));
        environment.jersey().register(new DishResource(dishDAO));
    }

    public static void main(String[] args) throws Exception {
        new MainApplication().run(args);
    }
}
