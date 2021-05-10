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
class EoriDetailsController @Inject()(cc: ControllerComponents)
  extends BackendController(cc) {

  def onLoad(regime: String, acknowledgementReference: String, EORI: String): Action[AnyContent] = Action.async { implicit request =>
    EORI match {
      case eori if EORI == "GB200000000001" =>
        Future.successful(Ok(Json.obj(
          "subscriptionDisplayResponse" -> Json.obj(
             "responseCommon" -> responseCommonError
          ))))
      case eori if EORI == "GB404000000001" =>
        Future.successful(NotFound("taxPayerID or EORI exists but no detail returned"))
      case _ =>
        Future.successful(Ok(Json.obj(
          "subscriptionDisplayResponse" -> Json.obj(
            "responseCommon" -> responseCommon,
            "responseDetail" -> responseDetail(EORI)
        ))))
    }
  }

  private final val responseCommon = Json.obj(
    fields = "status" -> "OK",
    "statusText" -> "Optional status text from ETMP",
    "processingDate" -> "2021-03-1T19:33:47Z",
    "returnParameters" -> Json.arr(
      Json.obj("paramName" -> "POSITION", "paramValue" -> "LINK")
    )
  )

  private final def responseDetail(EORI: String) = Json.obj(
    fields = "EORINo" -> EORI,
    "CDSFullName" -> "Fast Food ltd",
    "CDSEstablishmentAddress" -> Json.obj(
      "streetAndNumber" -> "99 Avenue Road",
      "city" -> "Anyold Town",
      "postalCode" -> "99JZ 1AA",
      "countryCode" -> "GB"
    )
  )

  private final val responseCommonError = Json.obj(
    fields = "status" -> "OK",
    "statusText" -> "037 - Mandatory parameters missing or invalid",
    "processingDate" -> "2021-03-1T19:33:47Z",
    "returnParameters" -> Json.arr(
      Json.obj("paramName" -> "POSITION", "paramValue" -> "FAIL")
    )
  )

}
