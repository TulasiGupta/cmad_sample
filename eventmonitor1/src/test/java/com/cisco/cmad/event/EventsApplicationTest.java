package com.cisco.cmad.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
//@SpringBootTest
public class EventsApplicationTest {

	@Test
	public void contextLoads() {
		System.out.println("in spring test");
	}

}
