/*
 * Copyright 2024 HM Revenue & Customs
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

import config.AppConfig
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import scala.concurrent.Future

@Singleton
class AddressLookUpController @Inject() (cc: ControllerComponents, appConfig: AppConfig) extends BackendController(cc) {

  def initialise(): Action[AnyContent] = Action.async {
    Future.successful(
      Accepted.withHeaders(("Location", s"${appConfig.ivdFrontendContextUrl}/importer-address-callback?id=12345"))
    )
  }

  def getAddress(id: Option[String]): Action[AnyContent] = Action.async {
    val successBody =
      s"""{
         |"address": {
         |  "lines": [
         |    "64 South Audley Street",
         |    "Mayfair",
         |    "London"
         |    ],
         |      "postcode": "W1K 2QT",
         |      "country": {
         |        "code": "GB",
         |        "name": "United Kingdom"
         |      }
         |    }
         |}
         |""".stripMargin

    Future.successful(Ok(successBody))
  }

}
