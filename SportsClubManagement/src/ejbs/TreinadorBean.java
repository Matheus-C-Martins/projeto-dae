package ejbs;

import entities.*;

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

@Stateless(name = "TreinadorEJB")
public class TreinadorBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String username, String password, String name, String email){
        try {
            Treinador treinador = entityManager.find(Treinador.class, username);
            if (treinador != null) {
                throw new EJBException("JA EXISTE UM TREINADOR COM ESSE USERNAME");
            }
            entityManager.persist(new Treinador(username, password, name, email));
        }catch (Exception e){
            throw new EJBException(e);
        }
    }

    public List<Treinador> all() {
        try {
            return (List<Treinador>) entityManager.createNamedQuery("getAllTreinadores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR A OBTER TREINADORES", e);
        }
    }

    public Treinador findTreinador(String username) {
        try{
            return entityManager.find(Treinador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR TREINADOR ("+username+")", e);
        }
    }

    public void updateTreinador(String username, String password, String name, String email){
        try{
            Treinador treinador = findTreinador(username);
            if(treinador != null) {
                entityManager.lock(treinador, LockModeType.OPTIMISTIC);
                treinador.setName(name);
                treinador.setEmail(email);
                if(password != null) {
                    treinador.setPassword(hashPassword(password));
                }
            } else {
                System.err.println("ERRO A ENCONTRAR TREINADOR");
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR TREINADOR ("+username+") ----> "+ e.toString());
        }
    }

    public void removeTreinador(String username){
        try{
            Treinador treinador = findTreinador(username);
            if(treinador != null) {
                entityManager.remove(treinador);
            } else {
                System.err.println("ERRO A ENCONTRAR TREINADOR");
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER TREINADOR ("+username+") ----> "+ e.toString());
        }
    }

    public void enrollTreinadorInModalidade(String username, String nome, String escalao){
        try{
            Treinador treinador = findTreinador(username);
            if (treinador != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.adicionarTreinador(treinador);
                    treinador.addModalidade(modalidade);
                }else {
                    System.err.println("ERRO A ENCONTRAR MODALIDADE");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR ATLETA");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A ADICIONAR TREINADOR ("+ username+") NA MODALIDADE ("+nome+", "+escalao+")", e);
        }
    }

    public void unrollTreinadorInModalidade(String username, String nome, String escalao){
        try{
            Treinador treinador = findTreinador(username);
            if (treinador != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.removerTreinador(treinador);
                    treinador.removeModalidade(modalidade);
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

    public void unrollAllModalidadesFromTreinador(String username){
        try{
            Treinador treinador = findTreinador(username);
            if (treinador != null) {
                if (!treinador.getModalidades().isEmpty()) {
                    Modalidade modAux = new Modalidade();
                    for (Modalidade modalidade : treinador.getModalidades()) {
                        modAux = entityManager.find(Modalidade.class, new ModalidadePK(modalidade.getNome(), modalidade.getEscalao()));
                        modAux.removerTreinador(treinador);
                    }
                    treinador.removeAllModalidade();
                } else {
                    System.err.println("TREINADOR NAO POSSUI MODALIDADES");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR TREINADOR");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A REMOVER TODAS AS MODALIDADES DO TREINADOR ("+ username+")", e);
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
