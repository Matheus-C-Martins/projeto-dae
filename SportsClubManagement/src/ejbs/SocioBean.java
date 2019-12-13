package ejbs;

import entities.Socio;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name="SocioBeanEJB")
public class SocioBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String username, String password, String nome, String email) {
        try {
            Socio socio = entityManager.find(Socio.class, username);
            if (socio != null) {
                throw new EJBException("JA EXISTE UM SOCIO COM ESSE USERNAME");
            }

            entityManager.persist(new Socio(username, password, nome, email));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Socio> all() {
        try {
            return (List<Socio>) entityManager.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERRO A RECEBER SOCIOS", e);
        }
    }

    public Socio findSocio(String username) {
        try {
            return entityManager.find(Socio.class, username);
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR SOCIO COM O USERNAME " + username, e);
        }
    }

    public void updateSocio(String oldUsername, String newUsername, String password, String name, String email) {
        try {
            Socio socio = entityManager.find(Socio.class, oldUsername);
            if (socio == null) {
                throw new EJBException("O SOCIO COM O USERNAME " + oldUsername + "NAO FOI ENCONTRADO");
            }

            entityManager.lock(socio, LockModeType.OPTIMISTIC);
            if (!newUsername.equals(oldUsername)) {
                socio.setUsername(newUsername);
            }

            if (!socio.getPassword().equals(password)) {
                socio.setPassword(password);
            }

            if (!socio.getName().equals(name)) {
                socio.setName(name);
            }

            if (!socio.getEmail().equals(email)) {
                socio.setEmail(email);
            }

        } catch (Exception e) {
            throw new EJBException("ERRO A ACTUALIZAR SOCIO COM O USERNAME " + oldUsername, e);
        }
    }

    public void removeSocio(String username) {
        try {
            Socio socio = entityManager.find(Socio.class, username);
            if (socio == null) {
                throw new EJBException("O SOCIO COM O USERNAME " + username + "NAO FOI ENCONTRADO");
            }

            entityManager.remove(socio);
        } catch (Exception e) {
            throw new EJBException("ERRO A APAGAR SOCIO COM O USERNAME " + username, e);
        }
    }
}
