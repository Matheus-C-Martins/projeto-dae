package ejbs;

import entities.Socio;
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

@Stateless(name = "SocioBeanEJB")
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
            throw new EJBException("ERRO A ENCONTRAR SOCIO ("+username+")", e);
        }
    }

    public void updateSocio(String username, String nome, String password, String email) {
        try{
            Socio socio = findSocio(username);
            if(socio != null){
                entityManager.lock(socio, LockModeType.OPTIMISTIC);
                socio.setName(nome);
                socio.setEmail(email);
                if(password != null){
                    socio.setPassword(hashPassword(password));
                }
            } else {
                System.err.println("ERRO A ENCONTRAR SOCIO");
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR SOCIO ("+username+") ----> "+ e.toString());
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
            System.err.println("ERRO A REMOVER SOCIO ("+username+") ----> "+ e.toString());
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
