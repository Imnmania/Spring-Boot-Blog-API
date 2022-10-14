package me.niloybiswas.spblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.niloybiswas.spblog.repositories.UserRepo;

@SpringBootTest
class SpringBootBlogApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void repoTest() {
		
		String repoName = userRepo.getClass().getName();
		String repoPackageName = userRepo.getClass().getPackageName();
		System.out.println(repoName);
		System.out.println(repoPackageName);
		
		// TL;DR => interface classes get initialized at RunTime by jdk and are kept in jdk.proxy2 package
		// these are called proxy classes which is handled by Spring framework
		// they allow us to Autowire interfaces without doing any implementation
		// and these type of Autowiring do not need to be annotated as @Service
		// also you can use @Repository annotation for these types of interfaces
		
	}

}
