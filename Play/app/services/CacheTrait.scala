package services

/**
  * Created by knoldus on 7/3/17.
  */
trait CacheTrait {

  def setcache(value:String,newObject:UserDetails)
  def getcache(value:String):Option[UserDetails]
}
