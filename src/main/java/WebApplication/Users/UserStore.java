package WebApplication.Users;
//In this class are stored all methods for users

import WebApplication.Security.SecurityEncoding;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)

public class UserStore {
    @PersistenceContext(name = "pu")
    EntityManager em;

    /* Annotiamo questa classe con @PostConstruct
     * viene utilizzata su un metodo che deve essere eseguito dopo l'iniezione di dipendenza per eseguire qualsiasi inizializzazione
     * DEVE essere invocato prima che la classe venga messa in servizio
     * DEVE essere supportata su tutte le classi che supportano l'iniezione delle dipendenze
     * DEVE essere invocato anche se la classe non richiede l'iniezione di risorse */
    @PostConstruct
    private void init (){
        System.out.println("-----------------UserStore INIT---------------");
    }

    public User create(User u) {
        System.out.println("----------------------" + u + " ----------------------------------");
        if (findByUsr(u.getUsr()).isPresent()) {
            throw new UserAlreadyExistException(u.getUsr());
        }
        u.setPwd(SecurityEncoding.shaHash(u.getPwd()));
        return em.merge(u);
    }

    public Optional<User> findByUsr(String usr) {
        return em.createNamedQuery(User.FIND_BY_USR, User.class)
                .setParameter("usr", usr)
                .getResultStream()
                .findFirst();
    }

}
