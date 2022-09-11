package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	@Scheduled(fixedDelay = 20000, initialDelay = 2000)
	public void scheduleByFixedRate() throws Exception {
		System.out.println("Proceso una vez starting");
		
		GetListFilesProcess lista = new GetListFilesProcess();
		
		lista.ListaReglas();
		
	}
}
