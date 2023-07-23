package model

case class ReqResponse( //General Schema for get request
                        page: Int,
                        per_page: Int,
                        total: Int,
                        total_pages: Int,
                        data: List[UserGet],
                        support: Support
                      )
