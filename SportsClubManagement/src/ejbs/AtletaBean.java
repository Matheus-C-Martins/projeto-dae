package ejbs;

import entities.Atleta;
import entities.Modalidade;
import entities.User;
import ws.ModalidadePK;

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

@Stateless(name = "AtletaEJB")
public class AtletaBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String username, String password, String name, String email){
        try {
            Atleta atleta = entityManager.find(Atleta.class, username);
            if (atleta != null) {
                throw new EJBException("JA EXISTE UM ATLETA COM ESSE USERNAME");
            }
            entityManager.persist(new Atleta(username, password, name, email));
        }catch (Exception e){
            throw new EJBException(e);
        }
    }

    public List<Atleta> all() {
        try {
            return (List<Atleta>) entityManager.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR A OBTER ATLETAS", e);
        }
    }

    public Atleta findAtleta(String username) {
        try{
            return entityManager.find(Atleta.class, username);
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR ATLETA ("+username+")", e);
        }
    }

    public void updateAtleta(String username, String password, String name, String email){
        try{
            Atleta atleta = findAtleta(username);
            if(atleta != null){
                entityManager.lock(atleta, LockModeType.OPTIMISTIC);
                atleta.setName(name);
                atleta.setEmail(email);
                if(password != null){
                    atleta.setPassword(hashPassword(password));
                }
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR ATLETA ("+username+") ----> "+ e.toString());
        }
    }

    public void removeAtleta(String username){
        try{
            Atleta atleta = findAtleta(username);
            if(atleta != null){
                entityManager.remove(atleta);
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER ATLETA ("+username+") ----> "+ e.toString());
        }
    }

    public void enrollAtletaInModalidade(String username, String nome, String escalao){
        try{
            Atleta atleta = findAtleta(username);
            if (atleta != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.adicionarAtleta(atleta);
                    atleta.addModalidade(modalidade);
                }else {
                    System.err.println("ERRO A ENCONTRAR MODALIDADE");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A ADICIONAR ATLETA ("+ username+") NA MODALIDADE ("+nome+", "+escalao+")", e);
        }
    }

    public void unrollAtletaInModalidade(String username, String nome, String escalao){
        try{
            Atleta atleta = findAtleta(username);
            if (atleta != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.removerAtleta(atleta);
                    atleta.removeModalidade(modalidade);
                }else {
                    System.err.println("ERRO A ENCONTRAR MODALIDADE");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A REMOVER ATLETA ("+ username+") DA MODALIDADE ("+nome+", "+escalao+")", e);
        }
    }

    public void unrollAllModalidadesFromAtleta(String username){
        try{
            Atleta atleta = findAtleta(username);
            if (atleta != null) {
                if (!atleta.getModalidades().isEmpty()) {
                    Modalidade modAux = new Modalidade();
                    for (Modalidade modalidade : atleta.getModalidades()) {
                        modAux = entityManager.find(Modalidade.class, new ModalidadePK(modalidade.getNome(), modalidade.getEscalao()));
                        modAux.removerAtleta(atleta);
                    }
                    atleta.removeAllModalidade();
                } else {
                    System.err.println("ATLETA NAO POSSUI MODALIDADES");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A REMOVER TODAS AS MODALIDADES DO ATLETA ("+ username+")", e);
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
