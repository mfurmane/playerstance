package pl.mfurmane.rest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@PropertySource(value= {"classpath:application.properties"})
public class SecurityConfig {

        @Autowired
        Environment environment;

//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    @Primary
//    public DataSource datasource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
//        dataSource.setUrl();
//        dataSource.setUsername();
//        dataSource.setPassword();
//        return dataSource;
//    }

    @Bean
    @Primary
    public DataSource datasource() {
        String driver = environment.getProperty("spring.datasource.driver-class-name");
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");

        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
//        properties.setProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_default", "false");
//        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.setProperty("hibernate.default_schema", "integr_softmaks");
//        properties.setProperty("hibernate.physical_naming_strategy", "pl.sciezka.config.CamelCaseToSnakeCaseNamingStrategy");
        return properties;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dupaDataSource") DataSource dataSource) {
        //JpaVendorAdapteradapter can be autowired as well if it's configured in application properties.
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        //Add package to scan for entities.
        factory.setPackagesToScan("pl.mfurmane.db");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(additionalProperties());
        return factory;
    }

    @Bean(name = "entityManager")
    @Primary
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

//    @Bean
//    public DataSource dataSource() {
//        PGSimpleDataSource ds = new PGSimpleDataSource() ;  // Empty instance.
//        ds.setServerNames(new String[]{"localhost"});  // The value `localhost` means the Postgres cluster running locally on the same machine.
//        ds.setDatabaseName( "playerstance" );   // A connection to Postgres must be made to a specific database rather than to the server as a whole. You likely have an initial database created named `public`.
//        ds.setUser( "postgres" );         // Or use the super-user 'postgres' for user name if you installed Postgres with defaults and have not yet created user(s) for your application.
//        ds.setPassword( "postgres" );
//        ds.setPortNumbers(new int[]{5432});
//        Hibernate.dialect
        //ds.setPortNumbers();

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("mysqluser");
//        dataSource.setPassword("mysqlpass");
//        dataSource.setUrl("jdbc:mysql://localhost:5432/myDb?createDatabaseIfNotExist=true");

//        return ds;
//    }

//    @Value("${spring.datasource.driver-class-name}")
//    private String driverName;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String userName;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//
//    @Bean
//    public DataSource datasource() throws PropertyVetoException {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        return dataSource;
//    }






//    @Autowired
//    Environment environment;
//
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    @Primary
//    public DataSource datasource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
//        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//        return dataSource;
////        return DataSourceBuilder
////                .create()
////                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
//        vendor.setDatabasePlatform(environment.getProperty("hibernate.dialect"));
//       // vendor.getJpaDialect().
////        vendor.setGenerateDdl(true);
//        LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendor);
//        //factory.setPersistenceProvider();
//        factory.setPackagesToScan("pl.mfurmane");
//        factory.setDataSource(datasource());
//
//        return factory;
//
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager dbTransactionManager() {
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                entityManagerFactory().getObject());
//        return transactionManager;
//    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("user1Pass"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                        .anyRequest().hasRole("ADMIN")
                );

        return http.build();

//                http.csrf()
//                .disable()
//                .authorizeRequests()
//                .requestMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .requestMatchers("/anonymous*")
//                .anonymous()
//                .requestMatchers("/login*")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
////                .and()
////                .formLogin()
////                .loginPage("/login.html")
////                .loginProcessingUrl("/perform_login")
////                .defaultSuccessUrl("/homepage.html", true)
////                .failureUrl("/login.html?error=true")
////                .failureHandler(authenticationFailureHandler())
////                .and()
////                .logout()
////                .logoutUrl("/perform_logout")
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessHandler(logoutSuccessHandler());
//        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("user1Pass"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.withUsername("user2")
//                .password(passwordEncoder().encode("user2Pass"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("adminPass"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2, admin);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .antMatchers("/anonymous*")
//                .anonymous()
//                .antMatchers("/login*")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
////                .and()
////                .formLogin()
////                .loginPage("/login.html")
////                .loginProcessingUrl("/perform_login")
////                .defaultSuccessUrl("/homepage.html", true)
////                .failureUrl("/login.html?error=true")
////                .failureHandler(authenticationFailureHandler())
////                .and()
////                .logout()
////                .logoutUrl("/perform_logout")
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessHandler(logoutSuccessHandler());
//        return http.build();
//    }
//
//    private LogoutSuccessHandler logoutSuccessHandler() {
//        return null;
//    }
//
//    private AuthenticationFailureHandler authenticationFailureHandler() {
//        return null;
//    }

}
