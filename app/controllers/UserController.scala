package controllers

import play.api.libs.ws._

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext
import play.api.libs.json._
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromArray}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import com.github.plokhotnyuk.jsoniter_scala.macros.stringified
import model.{ReqResponse, UserGet}
import requests.Response
import play.api.libs.json.Writes
import play.api.Logger


@Singleton
class UserController @Inject()(ws: WSClient, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getAllUsersAction: Action[AnyContent] = Action { implicit request =>
    val allUsers = getAllUsers
    Ok(Json.toJson(allUsers)(Writes.list(UserGet.userWrites)))
  }


  def getAllUsers: List[UserGet] = {
    implicit val reqResponsecodec: JsonValueCodec[ReqResponse] = JsonCodecMaker.make(CodecMakerConfig)
    println("Paso 1")
    val getUserURI = "https://reqres.in/api/users"
    println("getUserURI =" + getUserURI)
    println("Paso 2")
    val res: Response = requests.get(s"$getUserURI?page=1")
    println("Response =" + Response)
    println("Paso 3")
    val totalPages: Int = readFromArray(res.bytes).total_pages
    println("totalPages =" + totalPages)
    println("Paso 4")
    (1 to totalPages).flatMap { page =>
      println(s"querying page: $getUserURI?page=$page")
      val res: Response = requests.get(s"$getUserURI?page=$page")
      val responseText = res.text()
      val parsed_response: ReqResponse = readFromArray(res.bytes)
      println(responseText)
      parsed_response.data
    }.toList
  }

  def findUserByEmailAction(email: String): Action[AnyContent] = Action { implicit request =>
    val userByEmail: Option[UserGet] = findUserByEmail(email)

    userByEmail match {
      case Some(user) =>
        // Devolver el usuario encontrado como JSON
        Ok(Json.toJson(user))

      case None =>
        NotFound(s"No se encontró ningún usuario con el email: $email")
    }
  }


  // returns requested user or null if not found
  def findUserByEmail(userEmail: String): Option[UserGet] = {
    println("Email: " + userEmail)
    println("prueba 1")
    implicit val reqResponsecodec: JsonValueCodec[ReqResponse] = JsonCodecMaker.make(CodecMakerConfig)
    println("prueba 2")
    val res: Response = requests.get("https://reqres.in/api/users?page=1")
    println("prueba 3")
    val totalPages: Int = readFromArray(res.bytes).total_pages
    println("totalPages " + totalPages)
    println("prueba 4")
    (1 to totalPages).flatMap { page =>
      println("inicio clico -------------")
      println("page " + page)
      val res: Response = requests.get(s"https://reqres.in/api/users?page=$page")
      println("res " + res)
      val parsed_response: ReqResponse = readFromArray(res.bytes)
      println("parsed_response " + parsed_response)
      val userEmails: List[String] = parsed_response.data.map(_.email)
      println("parsed_response emails: " + userEmails)


      val userByEmail: Option[UserGet] = parsed_response.data.find(_.email == userEmail)
      println("userByEmail" + userByEmail)
      userByEmail match {
        case None => println(s"User by email: $userEmail NOT found on page $page")
        case _ => println(s"User by email: $userEmail found on page $page")
      }
      println("prueba interno al ciclo -------------")
      println("userByEmail --->" + userByEmail)
      println("inicio clico -------------")
      userByEmail

    }.headOption
  }
}
