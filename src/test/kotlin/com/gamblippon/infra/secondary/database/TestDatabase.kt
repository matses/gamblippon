import com.gamblippon.infra.secondary.database.PlayerDao
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.testcontainers.containers.PostgreSQLContainer

object TestDatabase {
    private val container = PostgreSQLContainer("postgres:16.1").apply {
        withDatabaseName("test-db")
        withUsername("test-user")
        withPassword("test-password")
        start() // Start the container
    }

    init {
        val config = HikariConfig().apply {
            jdbcUrl = container.jdbcUrl
            username = container.username
            password = container.password
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
        }
        val dataSource = HikariDataSource(config)

        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(PlayerDao.Player)
        }
    }
}