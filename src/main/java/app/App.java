package app;

import io.jsonwebtoken.impl.crypto.MacProvider;
import models.Role;
import models.User;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import providers.GuardProvider;
import services.authentication.Guard;

import javax.ws.rs.ApplicationPath;
import java.security.Key;

@ApplicationPath("")
public class App extends ResourceConfig {
	public final static Key key = MacProvider.generateKey();
	public static SessionFactory factory;
	public static Configuration configuration;
	public final static String endpoint = "http://localhost";

	public static void initHibernate() {
		try{
			initConfiguration();
			factory = configuration.buildSessionFactory();
		}catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void initConfiguration() {
		configuration = new Configuration()
				.configure()
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Role.class);
	}

	public App() {
		this.packages(true, "http/controllers");
		this.packages(true, "http/middleware");

		this.register(RolesAllowedDynamicFeature.class);


		register(new AbstractBinder(){
			@Override
			protected void configure() {
				bindFactory(GuardProvider.class)
						.to(Guard.class)
						.in(RequestScoped.class);
			}
		});
	}
}
