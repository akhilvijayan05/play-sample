package controllers

import javax.inject.Inject

import play.api.cache
import play.api.cache.CacheApi
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import services._


class ProfileController @Inject() (cache: CacheApi,cacheService:CacheTrait) extends Controller {

  def default=Action{implicit request=>
    val message=request.flash.get("success").getOrElse("Welcome!")
    request.session.get("username").map { user =>

      val maybeUser: Option[UserDetails] = cacheService.getcache(user)
Console.println(maybeUser)
      maybeUser match {
        case Some(result) if (user.equals(result.username) && result.isAdmin==false)=>Ok(views.html.profile("",message,result.firstname,result.middlename,result.lastname,user,result.mobile,result.gender,result.age,result.hobbies))
        case Some(result) if (user.equals(result.username) && result.isAdmin==true)=>Ok(views.html.profile("Maintenance",message,result.firstname,result.middlename,result.lastname,user,result.mobile,result.gender,result.age,result.hobbies))
        case None =>Ok(views.html.profile("","","","","",0,"",0,""))
      }
      //Console.println(maybeUser)
      //Ok(views.html.profile(message,result.firstname,result.middlename,result.lastname,user,result.mobile,result.gender,result.age,result.hobbies))
      //Ok("")
      /*else
        Ok(views.html.profile("","","","","",0,"",0,""))*/
    }.getOrElse {
      Ok(views.html.profile("","","","","",0,"",0,""))
    }
  }
}
