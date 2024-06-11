package sg.irtest.neoapi;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import sg.irtest.neoapi.controller.NeoapiController;


@WebMvcTest(controllers = {NeoapiController.class})
class NeoapiApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	@Autowired
    private MockMvc mockMvc;

	@Test
	void top10neo_inrangedate(){
		
		try {
			this.mockMvc.perform(
				MockMvcRequestBuilders.get("/top10neo").param("start_date","2020-10-10").param("end_date", "2020-10-11"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(10));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void top10neo_invalidparam(){
		
		try {
			this.mockMvc.perform(
				MockMvcRequestBuilders.get("/top10neo").param("start_date","2020-17-10").param("end_date", "2020-10-11"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void top10neo_invalidrange(){
		
		try {
			this.mockMvc.perform(
				MockMvcRequestBuilders.get("/top10neo").param("start_date","2020-10-10").param("end_date", "2020-10-08"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
