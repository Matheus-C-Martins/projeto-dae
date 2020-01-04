package ejbs;

import entities.Modalidade;

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
    @EJB
    AtletaBean atletaBean;
    @EJB
    TreinadorBean treinadorBean;
    @EJB
    HorarioBean horarioBean;

    @PostConstruct
    public void populateDB() {
        try {
            System.out.print("A Inserir Administradores na Bade Dados...");
            administradorBean.create("Admin1", "1234", "Master", "1@ipleiria.pt");
            administradorBean.create("Admin2", "1234", "Master", "2@ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Inserir Modalidades na Bade Dados...");
            modalidadeBean.create("Basquetebol", "Sub-18");
            modalidadeBean.create("Basquetebol", "Sub-16");
            modalidadeBean.create("Futebol", "Sub-20");
            modalidadeBean.create("Voleibol", "Sub-18");
            System.out.println("OK");

            System.out.print("A Inserir Horarios na Bade Dados...");
            horarioBean.create("Terça-feira", "19:00", "20:30");
            horarioBean.create("Terça-feira", "20:30", "22:00");
            horarioBean.create("Quarta-feira", "17:30", "19:30");
            horarioBean.create("Sexta-feira", "19:30", "21:00");
            System.out.println("OK");

            System.out.print("A Adicionar Modalidades a Horarios...");
            horarioBean.enrollHorarioInModalidade("Terça-feira", "19:00", "20:30", "Basquetebol", "Sub-16");
            horarioBean.enrollHorarioInModalidade("Quarta-feira", "17:30", "19:30", "Basquetebol", "Sub-16");
            horarioBean.enrollHorarioInModalidade("Terça-feira", "20:30", "22:00", "Basquetebol", "Sub-18");
            horarioBean.enrollHorarioInModalidade("Sexta-feira", "19:30", "21:00", "Basquetebol", "Sub-18");
            horarioBean.enrollHorarioInModalidade("Terça-feira", "20:30", "22:00", "Futebol", "Sub-20");
            horarioBean.enrollHorarioInModalidade("Sexta-feira", "19:30", "21:00", "Futebol", "Sub-20");
            horarioBean.enrollHorarioInModalidade("Terça-feira", "20:30", "22:00", "Voleibol", "Sub-18");
            horarioBean.enrollHorarioInModalidade("Sexta-feira", "19:30", "21:00", "Voleibol", "Sub-18");
            System.out.println("OK");

            System.out.print("A Inserir Socios na Bade Dados...");
            socioBean.create("9999999", "1234", "Sócio", "9999999@my.ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Inserir Atletas na Bade Dados...");
            atletaBean.create("2171316", "1234", "Matheus Martins", "2171316@my.ipleiria.pt");
            atletaBean.create("8888888", "1234", "Atleta", "8888888@my.ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Inserir Treinadores na Bade Dados...");
            treinadorBean.create("8080808", "1234", "Treinador", "8080808@my.ipleiria.pt");
            treinadorBean.create("0000000", "1234", "Treinador2", "0000000@my.ipleiria.pt");
            System.out.println("OK");

            System.out.print("A Adicionar Modalidades a Treinadores...");
            treinadorBean.enrollTreinadorInModalidade("8080808", "Basquetebol", "Sub-18");
            treinadorBean.enrollTreinadorInModalidade("0000000", "Voleibol", "Sub-18");
            System.out.println("OK");

            System.out.print("A Adicionar Modalidades a Atletas...");
            atletaBean.enrollAtletaInModalidade("2171316", "Basquetebol", "Sub-18");
            atletaBean.enrollAtletaInModalidade("8888888", "Basquetebol", "Sub-18");
            atletaBean.enrollAtletaInModalidade("2171316", "Voleibol", "Sub-18");
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