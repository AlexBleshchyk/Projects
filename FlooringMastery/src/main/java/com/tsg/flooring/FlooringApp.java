package com.tsg.flooring;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.tsg.flooring.controller.FlooringController;
import com.tsg.flooring.dao.FlooringPersistenceException;
import com.tsg.flooring.service.NoProductException;
import com.tsg.flooring.service.NoStateException;
/**
 * @author Alexandr Bleshchyk
 */

public class FlooringApp {

	public static void main(String[] args) throws FlooringPersistenceException, NoStateException, NoProductException {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.scan("com.tsg.flooring");
		appContext.refresh();
		
		FlooringController controller = appContext.getBean("flooringController", FlooringController.class);
		
		controller.run();
		
		appContext.close();
	}
}
