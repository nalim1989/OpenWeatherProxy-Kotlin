@file:JvmName("WeatherApp")

import com.shapegames.services.DatabaseMaintenance
import com.shapegames.acceptor.DatabaseWeatherDataAcceptor
import com.shapegames.cache.WeatherCache
import com.shapegames.data.DataLoadTable
import com.shapegames.data.WeatherDal
import com.shapegames.data.WeatherTable
import com.shapegames.handler.WeatherRestHandler
import com.shapegames.model.IWeatherDataProducer
import com.shapegames.producer.OpenWeatherDataProducer
import com.shapegames.producer.WeatherDatabaseDataProducer
import com.shapegames.provider.WeatherDataProvider
import com.shapegames.rest.WeatherRest
import com.shapegames.services.OpenWeatherV25Service
import com.shapegames.services.WeatherDataLoader
import com.shapegames.services.WeatherService
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun main() {
    // The tables to create in the database.
    val tables = arrayOf(WeatherTable, DataLoadTable)

    // Connect to the database and create the needed tables. Drop any existing data.
    val db = Database.connect("jdbc:sqlite::memory:test?mode=memory&cache=shared", "org.sqlite.JDBC")
        .also {
            TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
            transaction(it) {
                addLogger(StdOutSqlLogger)
                // Drop all existing tables to ensure a clean slate on each run
                SchemaUtils.drop(*tables)
                // Create all tables
                SchemaUtils.create(*tables)
            }
        }

    // Set up data access layer.
    val dal = WeatherDal(db = db)
    DatabaseWeatherDataAcceptor(dal) //Register new data acceptor
    val databaseDataProducer = WeatherDatabaseDataProducer(dal)
    val databaseMaintenance= DatabaseMaintenance(dal)
    databaseMaintenance.deleteInvalidData() // Clean data on startup

    // Set up open weather integration
    val openWeatherService = OpenWeatherV25Service()
    val openWeatherProducer = OpenWeatherDataProducer(openWeatherService)

    // Set up core services
    //The order is important, try local storage first and then switch to more "expensive" options
    val dataProducers:Set<IWeatherDataProducer> = linkedSetOf(databaseDataProducer, openWeatherProducer)
    val weatherDataLoader= WeatherDataLoader(dataProducers)
    val cache = WeatherCache(weatherDataLoader)
    val weatherDataProvider = WeatherDataProvider(cache)
    val weatherService = WeatherService(weatherDataProvider)

    // Set up rest api
    val restHandler = WeatherRestHandler(weatherService)
    WeatherRest(restHandler).run()

}