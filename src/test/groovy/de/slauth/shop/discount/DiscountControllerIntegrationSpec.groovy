package de.slauth.shop.discount

import de.slauth.shop.discount.DiscountController.DiscountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification
import tools.jackson.databind.json.JsonMapper

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest
@AutoConfigureMockMvc
class DiscountControllerIntegrationSpec extends Specification {

    @Shared
    def today = LocalDate.now()

    @Autowired
    MockMvc mockMvc

    @Autowired
    JsonMapper jsonMapper

    void "member since #memberSince, order of #total EUR gets #expected EUR discount" (
            LocalDate memberSince, BigDecimal total, BigDecimal expected) {
        when:
            def response = mockMvc.perform(get("/api/discount")
                    .queryParam("total", total.toString())
                    .queryParam("memberSince", memberSince.toString()))
                    .andReturn().response

        then:
            response.status == 200
            deserialize(response) == new DiscountResponse(expected)

        where:
            memberSince         |  total || expected
            today               |  99.00 ||     0.00
            today               | 100.00 ||     5.00
            today.minusYears(1) |  99.00 ||     0.00
            today.minusYears(2) |  99.00 ||     9.90
            today.minusYears(2) | 100.00 ||    15.00
    }

    DiscountResponse deserialize(MockHttpServletResponse response) {
        jsonMapper.readValue(response.contentAsString, DiscountResponse)
    }
}
