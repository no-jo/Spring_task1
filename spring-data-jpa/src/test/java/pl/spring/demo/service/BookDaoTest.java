package pl.spring.demo.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookDaoTest {
	
	@Autowired
	BookDao bookdao;
	
	@Test
	public void test() {
		//given
		BookTo book = new BookTo(null, "title", "author");
		Long l = 7L;
		
		//when
		bookdao.save(book);
		
		//then
		assertEquals(l,book.getId());
	}

}
