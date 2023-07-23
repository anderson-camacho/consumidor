package controllers

import play.api.libs.ws._

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext
import play.api.libs.json._
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromArray}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import model.{ReqResponse, UserGet}

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

  def getAllUsers: List[UserGet] = {
    implicit val reqResponsecodec: JsonValueCodec[ReqResponse] = JsonCodecMaker.make(CodecMakerConfig())
    val getUserURI = "https://reqres.in/api/users"
    val res: Response = requests.get(s"$getUserURI?page=1")
    val totalPages: Int = readFromArray(res.bytes).total_pages
    (1 to totalPages).flatMap { page =>
      println(s"querying page: $getUserURI?page=$page")
      val res: Response = requests.get(s"$getUserURI?page=$page")
      val responseText = res.text
      val parsed_response: ReqResponse = readFromArray(res.bytes)
      println(responseText)
      parsed_response.data
    }.toList
  }
}
