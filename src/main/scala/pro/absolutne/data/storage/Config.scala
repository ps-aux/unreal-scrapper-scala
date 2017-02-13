package pro.absolutne.data.storage

import javax.sql.DataSource

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Configuration
class Config {

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
