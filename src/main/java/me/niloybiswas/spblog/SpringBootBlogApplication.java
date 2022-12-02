package me.niloybiswas.spblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringBootBlogApplication /* implements CommandLineRunner */{

	/* @Autowired
	private PasswordEncoder passwordEncoder; */

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApplication.class, args);
	}


	/* @Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("123"));
	} */
}
