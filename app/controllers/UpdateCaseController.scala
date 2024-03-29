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

import play.api.Logging
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.Instant
import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class UpdateCaseController @Inject() (cc: ControllerComponents) extends BackendController(cc) with Logging {

  private[controllers] val InvalidCaseId = "C189999999999999999999"
  private[controllers] val ClosedCaseId  = "C188888888888888888888"

  def updateCase(): Action[JsValue] = Action(parse.json).async { request =>
    val requiredHeaders = Set(
      "customprocesseshost",
      "x-correlation-id",
      "date",
      "content-type",
      "accept",
      "authorization"
    )

    val correlationId = request.headers.get("x-correlation-id").getOrElse(UUID.randomUUID().toString)
    val responseHeaders = Seq(
      "x-correlation-id" -> correlationId
    )

    val missingHeaders = requiredHeaders.diff(request.headers.keys.map(_.toLowerCase))

    val result = for {
      _ <- Either.cond(missingHeaders.isEmpty, (), Json.obj("missing-headers" -> missingHeaders.toList))
      caseId <- (request.body \ "Content" \ "CaseID").validate[String].asEither.left.map(err =>
        Json.obj("errors" -> err.toString())
      )
    } yield caseId

    val resp = result match {
      case Right(`InvalidCaseId`) =>
        val error = Json.obj("correlationId" -> correlationId, "errorMessage" -> "9xx : 03- Invalid Case ID")
        BadRequest(Json.obj("errorDetail" -> error))

      case Right(`ClosedCaseId`) =>
        val error =
          Json.obj("correlationId" -> correlationId, "errorMessage" -> "9xx : 04 - Requested case already closed")
        BadRequest(Json.obj("errorDetail" -> error))

      case Right(caseId) =>
        val successBody = Json.obj(
          "CaseID"         -> caseId,
          "ProcessingDate" -> Instant.now().toString,
          "Status"         -> "Success",
          "StatusText"     -> "Case updated successfully"
        )

        Ok(successBody)

      case Left(error) =>
        logger.info(s"headers: ${request.headers.keys}")
        // the error body is not representative of the real backend, details are for debugging purposes ONLY
        BadRequest(error)
    }

    Future.successful(resp.withHeaders(responseHeaders: _*))
  }

}
