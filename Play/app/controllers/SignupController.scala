package controllers

import javax.inject.Inject

import play.api.Configuration
import play.api.cache._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.mvc.{Action, Controller}
import services._

/**
  * Created by knoldus on 4/3/17.
  */
class SignupController @Inject() (cache: CacheApi,cacheService: CacheTrait,configuration: Configuration) extends Controller {

  val userForm = Form(
    mapping(
      "firstname" -> nonEmptyText,
      "middlename" -> text,
      "lastname" -> nonEmptyText,
      "username" -> text,
      "password" -> text,
      "repassword" -> text,
      "mobile" -> longNumber,
      "gender" -> text,
      "age" -> number(min=18 ,max=75),
      "hobbies" -> text,
      "isAdmin" ->boolean
    )(UserDetails.apply)(UserDetails.unapply)
  )

  def default = Action {
    Console.println(Service.list)
    Console.println()
  Ok(views.html.signup(""))
  }
  def store = Action {implicit request =>

    //val mobile=userForm.bindFromRequest.get

    //mobile.age.toS
    userForm.bindFromRequest.fold(
      formWithErrors => {

        Ok(views.html.signup("Please check you input !!"))
      },
        value => {
          if(value.mobile.toString.length==10 && value.password.equals(value.repassword))
            {
              val config=configuration.getString("type")
              config match{
                case Some(check) if check.equals("Admin")=>{
                  val newObject=value.copy(password = HashingPassword.getHash(value.password),repassword = HashingPassword.getHash(value.repassword),isAdmin = true)

                  cacheService.setcache(value.username, newObject)

                  Redirect(routes.ProfileController.default).withSession("username" -> value.username).flashing("success"->"Successfull logged in. Your details are...")
                }
                case Some(check) if check.equals("Normal")=>{

                  val newObject=value.copy(password = HashingPassword.getHash(value.password),repassword = HashingPassword.getHash(value.repassword))

                  cacheService.setcache(value.username, newObject)

                  Redirect(routes.ProfileController.default).withSession("username" -> value.username).flashing("success"->"Successfull logged in. Your details are...")
                }
                case None=>Ok("")
              }

            }
          else
            {
              Ok(views.html.signup("Please check you input !!"))
            }
        }
    )
  }
}
