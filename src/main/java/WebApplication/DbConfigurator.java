package WebApplication;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Per ogni Server Apllication la classe DBConfigurator è diversa.
 * In quest'applicazione utilizziamo WildFly la quale configurazione è la seguente
 */
@DataSourceDefinition(
        /**
         * la variabile name indica il nome del DataSource utilizzato
        */

        name = DbConfigurator.DS_JINDI_NAME,
        className = DbConfigurator.MARIADB_CLASS_NAME,
        serverName= DbConfigurator.MARIADB_HOST,
        portNumber= DbConfigurator.MARIADB_PORT,
        user= DbConfigurator.MARIADB_USER,
        password = DbConfigurator.MARIADB_PWD,
        databaseName=DbConfigurator.MARIADB_DATABASE_NAME
)
/**
 * L'annotazione @Singleton() istanzia questa classe UNA SOLA volta, indifferentemente dal numero di Client che ne accedono
 * non è tenuto a mantenere il suo stato in caso di arresto o arresti del server
 * Le applicazioni che utilizzano un bean di sessione singleton possono specificare che il singleton deve essere istanziato all'avvio dell'applicazione
 * come nel nostro caso che grazie all'annotazione @Startup questo bean viene istanziato all'inizio della nostra applicazione
 * L'annotazione @TransactionAttribute specifica se il contenitore deve invocare una classe di business logic di default è REQUIRED
 */
@Singleton()
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DbConfigurator {
    public static final String MARIADB_CLASS_NAME = "org.mariadb.jdbc.MariaDbDataSource";
    public static final String DS_JINDI_NAME = "java:global/jdbc/pw";
    public static final String MARIADB_PROTOCOL = "tcp";
    public static final int MARIADB_PORT = 3306;
    public static final String MARIADB_DATABASE_NAME = "pwnew";
    public static final String MARIADB_PWD = "pwapp";
    public static final String MARIADB_USER = "pwapp";
    public static final String MARIADB_HOST = "localhost";
    /**
     * L'annotazione @Resource indica quale risorsa è necessaria all'applicazione
     * può essere applicata a una classe del componente dell'applicazione o a campi o metodi della classe del componente
     *  Quando l'annotazione viene applicata a un campo o metodo
     * il contenitore inietterà un'istanza della risorsa richiesta nel componente dell'applicazione
     * quando il componente viene inizializzato.
     * Se l'annotazione viene applicata alla classe componente, l'annotazione dichiara una risorsa
     * che l'applicazione cercherà in fase di esecuzione.
     * Nota che questa annotazione può apparire su campi privati ​​e metodi di superclassi;
     * il contenitore è necessario per eseguire l'iniezione anche in questi casi.
     */

    @Resource(lookup = "DS_JINDI_NAME")
    private DataSource pw;

    private String jdbcBaseUrl;

    /**
     * L'annotazione PostConstruct viene utilizzata su un metodo che deve essere eseguito dopo
     * l'iniezione di dipendenza per eseguire qualsiasi inizializzazione.
     * Questo metodo DEVE essere invocato prima che la classe venga messa in servizio.
     * Questa annotazione DEVE essere supportata su tutte le classi che supportano l'iniezione delle dipendenze.
     * Il metodo annotato con PostConstruct DEVE essere invocato anche se la classe non richiede l'iniezione di risorse.
     * Solo un metodo può essere annotato con questa annotazione.
     */

    @PostConstruct
    public void init(){
        System.out.println("----------- Inizializzazione DBConfigurator -------------");
        /**
         * Dichiariamo il nostro jdbcBaseUrl aggiungendo al url di
         * default ( jdbc:mariadb:// ) il nostro HOST e la PORTA del DB
         */
        jdbcBaseUrl = "jdbc:mariadb://" + MARIADB_HOST + ":" + MARIADB_PORT +"/";

        checkDataSource();
        migrate();
        System.out.println("------------ Database inizializzato correttamente ---------");
    }

    /**
     * Questo metodo genera la nostra connessione al DB
     * Utilizziamo la classe Connection per eseguire la connessione
     * La nostra connessione deve essere annidata nel costrutto try e catch
     */
    private void checkDataSource() {
        try ( Connection connection = pw.getConnection()) {
            System.out.println(
                    connection.getMetaData().getDatabaseProductName() + "-"
                            + connection.getCatalog()
            );
            System.out.println("---------------- check datasource ok -------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * FlyWay è un servizio di controllo della versione del database
     * Importiamo la classe FlyWay con i metodi in cascata
     * A dataSource passiamo il nostro jdbcBaseUrl ( viene preso dal nostro metodo init )
     * e aggiungiamo dopo lo / il nome del databse, NomeUser e PWD
     * flyway.migrate() tiene il numero di versione del DB
     * la versione è contenuta all'interno della tabella flyway_schema_history
     * @return  Se la migrazione ha successo ritorna true, in quanto la versione è maggiore di 0.
     */
    private boolean migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(jdbcBaseUrl + MARIADB_DATABASE_NAME, MARIADB_USER, MARIADB_PWD)
                /**
                 * Per introdurre Flyway nei database esistenti basandoli su una versione specifica.
                 * Ciò farà sì che Migrate ignori tutte le migrazioni fino alla versione di base inclusa.
                 * Le migrazioni più recenti verranno quindi applicate come al solito.
                 */
                .baselineOnMigrate(true)
                /**
                 * Costruisce l'istanza di configurazione di FlyWay
                 */
                .load();
        int result = flyway.migrate();
        System.out.println("-------------------------result migrate------------- " + result);
        return result > 0;
    }
}