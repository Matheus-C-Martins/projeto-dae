package ejbs;

import entities.Administrador;
import entities.User;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String username, String password, String name, String email){
        try {
            Administrador administrador = entityManager.find(Administrador.class, username);
            if (administrador != null) {
                throw new EJBException("JA EXISTE UM ADMINISTRADOR COM ESSE USERNAME");
            }
            entityManager.persist(new Administrador(username, password, name, email));
        }catch (Exception e){
            throw new EJBException(e);
        }
    }

    public List<Administrador> all() {
        try {
            return (List<Administrador>) entityManager.createNamedQuery("getAllAdministradores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR A RECEBER ADMINISTRADORES", e);
        }
    }

    public Administrador findAdmin(String username) {
        try{
            return entityManager.find(Administrador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR ADMINISTRADOR ("+username+")", e);
        }
    }

    public void updateAdmin(String username, String password, String name, String email){
        try{
            Administrador admin = findAdmin(username);
            if(admin != null){
                entityManager.lock(admin, LockModeType.OPTIMISTIC);
                admin.setName(name);
                admin.setEmail(email);
                if(password != null){
                    admin.setPassword(hashPassword(password));
                }
            } else {
                System.err.println("ERRO A ENCONTRAR AMDMINISTRADOR");
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR AMDMINISTRADOR ("+username+") ----> "+ e.toString());
        }
    }

    public void removeAdmin(String username){
        try{
            Administrador administrador = findAdmin(username);
            if(administrador != null){
                entityManager.remove(administrador);
            } else {
                System.err.println("ERRO A ENCONTRAR AMDMINISTRADOR");
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER AMDMINISTRADOR ("+username+") ----> "+ e.toString());
        }
    }

    private String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwdBuffer =
                    Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(encoded);
    }
}
