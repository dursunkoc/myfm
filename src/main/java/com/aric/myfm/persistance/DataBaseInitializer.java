package com.aric.myfm.persistance;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.aric.myfm.domain.FileEntry;
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
		
		FileEntry fileEntry = new FileEntry();
		fileEntry.setName("Dursuns CV");
		fileEntry.setAddress("https://docs.google.com/file/d/0BzVhRIRv4HBqcjdCM2RuN3pHWngzMXlIdzBuY1NqbzhSYnU4/edit?pli=1");
		fileEntryDao.save(fileEntry);

		System.out.println(fileEntryDao);
	}

}
