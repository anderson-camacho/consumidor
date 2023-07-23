package model

import com.github.plokhotnyuk.jsoniter_scala.macros.stringified

import java.time.Instant

case class UserPost( //General Schema for post request
                     @stringified id: Int,
                     job: String,
                     name: String,
                     createdAt: Instant
                   )
