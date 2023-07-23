package model

case class UserGet(
                    id: Int,
                    email: String,
                    first_name: String,
                    last_name: String,
                    avatar: String
                  )