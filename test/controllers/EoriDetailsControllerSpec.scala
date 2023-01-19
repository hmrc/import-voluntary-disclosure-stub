/*
 * Copyright 2023 HM Revenue & Customs
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
import play.api.http.Status
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout, status}

class EoriDetailsControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  private val fakeRequest = FakeRequest("GET", "/")

  private val controller = new EoriDetailsController(Helpers.stubControllerComponents())

  "GET onLoad" should {
    "return 200" in {
      val result = controller.onLoad(
        regime = "CDS",
        acknowledgementReference = "11a2b17559e64b14be257a112a7d9e8e",
        EORI = "GB123456789"
      )(fakeRequest)
      status(result) shouldBe Status.OK
    }
    "return 200 without response details" in {
      val result = controller.onLoad(
        regime = "CDS",
        acknowledgementReference = "11a2b17559e64b14be257a112a7d9e8e",
        EORI = "GB200000000001"
      )(fakeRequest)
      status(result) shouldBe Status.OK
      contentAsString(result).contains("responseDetail") shouldBe false
    }
    "return 404" in {
      val result = controller.onLoad(
        regime = "CDS",
        acknowledgementReference = "11a2b17559e64b14be257a112a7d9e8e",
        EORI = "GB404000000001"
      )(fakeRequest)
      status(result) shouldBe Status.NOT_FOUND
    }
  }

}
