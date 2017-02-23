package pro.absolutne.data.storage

import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Configuration
class Config @Autowired()(val env:Environment) {

  @Bean
  def dataSource(): DataSource = {
    val ds = new DriverManagerDataSource()

    ds.setUsername("unreal")
    ds.setPassword("unreal..")
    ds.setDriverClassName("org.postgresql.Driver")
    ds.setUrl("jdbc:postgresql://localhost:5432/unreal-scraper")

    ds
  }

}
