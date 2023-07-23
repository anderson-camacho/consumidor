package controllers

import play.api.libs.ws._

import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext
import play.api.libs.json._
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromArray}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}
import model.{ReqResponse, UserGet}
import requests.Response
import play.api.libs.json.Writes

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
}
