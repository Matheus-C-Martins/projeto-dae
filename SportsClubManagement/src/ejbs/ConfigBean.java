package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    @EJB
    AdministradorBean administradorBean;

    @PostConstruct
    public void populateDB() {
        try {
            System.out.print("A Inserir Administradores na Bade Dados...");
            administradorBean.create("Admin1", "1234", "Master", "1@ipleiria.pt");
            administradorBean.create("Admin2", "1234", "Master", "2@ipleiria.pt");
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}