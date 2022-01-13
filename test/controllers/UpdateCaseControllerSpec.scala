/*
 * Copyright 2022 HM Revenue & Customs
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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.{ContentTypes, Status}
import play.api.libs.json.{JsDefined, JsObject, Json}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}
import java.util.{Locale, UUID}

class UpdateCaseControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  private val httpDateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneId.of("GMT"))
  private val request = FakeRequest(controllers.routes.UpdateCaseController.updateCase()).withBody(Json.obj())
  private val controller = new UpdateCaseController(stubControllerComponents())

  trait Test {
    def caseId: String = "C18"

    def validRequest: FakeRequest[JsObject] = request.withHeaders(
      "customprocesseshost" -> "Digital",
      "x-correlation-id" -> UUID.randomUUID().toString,
      "date" -> LocalDateTime.now().format(httpDateFormatter),
      "content-type" -> ContentTypes.JSON,
      "accept" -> ContentTypes.JSON,
      "authorization" -> s"Bearer some-really-long-token"
    ).withBody(Json.obj("Content" -> Json.obj("CaseID" -> caseId)))
  }

  "Calling updateCase with a valid request" should {

    "return 200" in new Test {
      private val result = controller.updateCase()(validRequest)
      status(result) shouldBe Status.OK
    }

    "return a JSON payload" in new Test {
      private val result = controller.updateCase()(validRequest)
      contentType(result) shouldBe Some(ContentTypes.JSON)
      contentAsJson(result) \ "CaseID" shouldBe JsDefined(Json.toJson(caseId))
    }

    "return a correlation ID in the headers" in new Test {
      private val result: Result = await(controller.updateCase()(validRequest))
      result.header.headers.keys should contain("x-correlation-id")
    }

    "return 400 for a case that doesn't exist" in new Test {
      override val caseId: String = controller.InvalidCaseId
      private val result = controller.updateCase()(validRequest)
      status(result) shouldBe Status.BAD_REQUEST
    }

    "return 400 for a case that's closed" in new Test {
      override val caseId: String = controller.ClosedCaseId
      private val result = controller.updateCase()(validRequest)
      status(result) shouldBe Status.BAD_REQUEST
    }
  }

  "Calling createCase with an invalid request" should {

    "return 400" in {
      val result = controller.updateCase()(request)
      status(result) shouldBe Status.BAD_REQUEST
    }

    "return a JSON payload" in {
      val result = controller.updateCase()(request)
      contentType(result) shouldBe Some(ContentTypes.JSON)
    }

    "return a correlation ID in the headers" in {
      val result = controller.updateCase()(request)
      header("x-correlation-id", result) shouldBe defined
    }
  }

}
