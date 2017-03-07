package modules

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{CacheService, CacheTrait}
/**
  * Created by knoldus on 7/3/17.
  */
class GuiceModule(environment: Environment,configuration: Configuration) extends AbstractModule{

  override def configure()={
    bind(classOf[CacheTrait]).to(classOf[CacheService])
  }
}
