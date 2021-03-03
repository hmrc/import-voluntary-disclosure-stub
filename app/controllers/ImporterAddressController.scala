/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class ImporterAddressController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) {

  def onLoad(id: String): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(
      Ok(
        Json.obj(
          fields = "responseCommon" -> responseCommon,
          "responseDetail" -> responseDetail
        )
      )
    )
  }

  private final val responseCommon = Json.obj(
    fields = "status" -> "OK",
    "statusText" -> "Optional status text from ETMP",
    "processingDate" -> "2021-03-1T19:33:47Z",
    "returnParameters" -> Json.arr(
      Json.obj("paramName" -> "POSITION", "paramValue" -> "LINK")
    )
  )

  private final val responseDetail = Json.obj(
    fields = "EORINo" -> "GB987654321000",
    "CDSFullName" -> "Fast Food ltd",
    "CDSEstablishmentAddress" -> Json.obj(
      "streetAndNumber" -> "99 Avenue Road",
      "city" -> "Anyold Town",
      "postalCode" -> "99JZ 1AA",
      "countryCode" -> "GB"
    )
  )

}
