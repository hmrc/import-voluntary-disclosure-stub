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

import config.AppConfig
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.inject.Injector
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}

class AddressLookUpControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  private val fakeRequest = FakeRequest("GET", "/")

  lazy val injector: Injector = app.injector

  implicit lazy val appConfig: AppConfig = injector.instanceOf[AppConfig]

  private val controller = new AddressLookUpController(Helpers.stubControllerComponents(), appConfig)

  "GET initialise" should {
    "return 202" in {
      val result = controller.initialise()(fakeRequest)
      status(result) shouldBe Status.ACCEPTED
    }
  }

  "GET getAddress" should {
    "return 200" in {
      val result = controller.getAddress(Some("12345"))(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }

}
