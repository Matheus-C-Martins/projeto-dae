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

    @EJB
    ModalidadeBean modalidadeBean;

    @EJB
    SocioBean socioBean;

    @EJB
    ProdutoBean produtoBean;

    @PostConstruct
    public void populateDB() {
        try {
            System.out.print("A Inserir Administradores na Bade Dados...");
            administradorBean.create("Admin1", "1234", "Master", "1@ipleiria.pt");
            administradorBean.create("Admin2", "1234", "Master", "2@ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Inserir Modalidades na Bade Dados...");
            modalidadeBean.create("Basquetebol");
            modalidadeBean.create("Futebol");
            modalidadeBean.create("Natação");
            modalidadeBean.create("Voleibol");
            modalidadeBean.create("Ginástica");
            System.out.println("OK");

            System.out.print("A Inserir Administradores na Bade Dados...");
            socioBean.create("2171316", "1234", "Matheus Martins", "2171316@my.ipleiria.pt");
            socioBean.create("9999999", "1234", "Sócio", "9999999@my.ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Inserir Produtos na Bade Dados...");
            produtoBean.create("Artigo Desportivo", "Descrição.... Artigo Desportivo", 9.99);
            produtoBean.create("Seguro", "Descrição.... Seguro", 2.50);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}