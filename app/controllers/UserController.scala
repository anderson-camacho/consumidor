package controllers

import play.api.libs.ws._
import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext
import play.api.libs.json._

@Singleton
class UserController @Inject()(ws: WSClient, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getUsersByEmail(email: String): Action[AnyContent] = Action.async { implicit request =>
    println("Paso 1")
    val apiUrl = "https://reqres.in/api/users"
    println("Paso 2")
    val emailFilter = email.toLowerCase()
    println("Paso 3")
    ws.url(apiUrl).get().map { response =>
      println("Paso 4")
      val users = (response.json \ "data").as[JsArray].value
      println(users.contains())
      val filteredUsers = users.filter { user =>
        (user \ "email").as[String].toLowerCase().contains(emailFilter)
      }
      println(filteredUsers)

      //Ok("Tod bien")
      Ok(Json.obj("data" -> users))
    }
  }
}
