package com.aric.myfm.persistance;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.aric.myfm.domain.User;


/**
 * 
 */
public class DataBaseInitializer {

	private FileEntryDao fileEntryDao;

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer() {

		/* Default constructor for reflection instantiation */
	}


	public DataBaseInitializer(UserDao userDao, FileEntryDao newsEntryDao, PasswordEncoder passwordEncoder) {

		this.userDao = userDao;
		this.fileEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
	}


	public void initDataBase() {

		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		this.userDao.save(adminUser);

//		long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
//		for (int i = 0; i < 10; i++) {
//			FileEntry newsEntry = new FileEntry();
//			newsEntry.setContent("This is example content " + i);
//			newsEntry.setDate(new Date(timestamp));
//			this.newsEntryDao.save(newsEntry);
//			timestamp += 1000 * 60 * 60;
//		}
		System.out.println(fileEntryDao);
	}

}
