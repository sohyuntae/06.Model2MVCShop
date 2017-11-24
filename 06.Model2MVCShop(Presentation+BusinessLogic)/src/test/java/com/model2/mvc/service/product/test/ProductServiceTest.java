package com.model2.mvc.service.product.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductDaoImpl;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
											"classpath:config/context-aspect.xml",
											"classpath:config/context-mybatis.xml",
											"classpath:config/context-transaction.xml"})
public class ProductServiceTest {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService; 
	
	
	//=============10번 리펙토링=====================================================
	//@Test
	public void testAddProduct() throws Exception {

		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		Product product = new Product();
		
		product.setProdName("이승섭짱");
		product.setProdDetail("은애를 무서워하는 형입니다.");
		product.setManuDate("20171117");
		product.setPrice(5000);
		product.setFileName("이승섭.jsp");
		
		
		sqlSession.insert("ProductMapper.addProduct",product);
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		Product product = new Product();
		product.setProdNo(10021);
		sqlSession.selectOne("ProductMapper.getProduct",product.getProdNo());
	
		//product.setProdName("이승섭");
		//product.setProdDetail("은애를 무서워하는 형입니다.");
		//product.setManuDate("20171117");
		//product.setPrice(5000);
		//product.setFileName("이승섭.jsp");
		System.out.println(""+sqlSession.selectOne("ProductMapper.getProduct",product.getProdNo()));
	
		//sqlSession.selectOne("ProductMapper.getProduct",product.getProdName());
	}
	
	//@Test
	public void testUpdateProduct() throws Exception {

		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		Product product = new Product();
		product.setProdNo(10021);
		
		product.setProdName("장은애짱");
		product.setProdDetail("오졋다리.");
		product.setManuDate("20171116");
		product.setPrice(50000);
		product.setFileName("장은애짱.jsp");
		
		sqlSession.update("ProductMapper.updateProduct",product);
		
		//System.out.println(""+sqlSession.selectOne("ProductMapper.getProduct",product.getProdNo()));
		
	}
	
	//@Test
	public void testGetProductList() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("");
	 	//ArrayList<String> arrayList = new ArrayList<String>();
	 	
		System.out.println("list 소환");
	 	System.out.println(""+sqlSession.selectList("ProductMapper.getProductList",search));
		
	}

	//@Test
	public void testRemoveProduct() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		Product product = new Product();
		product.setProdNo(10021);
		
		System.out.println(":: "+sqlSession.delete("ProductMapper.removeProduct",product.getProdNo()) );	
	}

//===============11번 리펙토링==============================================================
	//@Test
	public void testAddProduct1() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		Product product = new Product();
		product.setProdName("장은애짱짱맨");
		product.setProdDetail("은애는 무섭습니다.");
		product.setManuDate("20171116");
		product.setPrice(12345);
		product.setFileName("장은애.jsp");
		
		productDao.addProduct(product);
		System.out.println("1. addProduct (INSERT) ");
		
		
	}

	//@Test
	public void testGetProduct1() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		Product product = new Product();
		product.setProdNo(10040);
		
		//productDao.getProduct(product.getProdNo());
		System.out.println(":: 2. getUser(SELECT)  ");
		System.out.println(" :: "+ productDao.getProduct(product.getProdNo()));
	}

	//@Test
	public void testUpdateProduct1() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		Product product = new Product();
		product.setProdNo(10040);
		
		product.setProdName("장은애짱");
		product.setProdDetail("오졋다리");
		product.setManuDate("20171116");
		product.setPrice(300);
		product.setFileName("장은애짱.jsp");
		
		
		
		System.out.println("3. update(UPDATE)  ");
		productDao.updateProduct(product);
	}

	//@Test
	public void testGetProductList1() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("");
	 	//ArrayList<String> arrayList = new ArrayList<String>();
	 	System.out.println(":: List<User> 내용 : "+
	 									productDao.getProductList(search));
	}

	//===========12번 리펙토링==================================================================
	
	//@Test
	public void testAddProduct2() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		ProductServiceImpl productService = new ProductServiceImpl();
		productService.setProductDao(productDao);
		
		Product product = new Product();
		product.setProdName("장은애애주가");
		product.setProdDetail("은애는 술을잘먹습니다.");
		product.setManuDate("20171111");
		product.setPrice(54321);
		product.setFileName("장은애애주가.jsp");
		
		System.out.println(":: 1. addUser(INSERT)  ? ");
		productService.addProduct(product);
	
	}
	
	//@Test
	public void testGetProduct2() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		ProductServiceImpl productService = new ProductServiceImpl();
		productService.setProductDao(productDao);
		
		Product product = new Product();
		product.setProdNo(10047);
		
		System.out.println(":: 2. getUser(SELECT)  ? ");
		System.out.println(":: "+ productService.getProduct(product.getProdNo()));
	}
	
	//@Test
	public void testUpdateProduct2() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		ProductServiceImpl productService = new ProductServiceImpl();
		productService.setProductDao(productDao);
		
		Product product = new Product();
		product.setProdNo(10047);
		product.setProdName("장은애애주녀");
		product.setProdDetail("은애는 술을정말...");
		product.setManuDate("20171111");
		product.setPrice(12345);
		product.setFileName("장은애애주녀.jsp");
		
		System.out.println(":: 3. update(UPDATE)  ? ");
		productService.updateProduct(product);
	}
	
	//@Test
	public void testGetProductList2() throws Exception {
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		
		ProductServiceImpl productService = new ProductServiceImpl();
		productService.setProductDao(productDao);
		
		Search search = new Search();
		search.setCurrentPage(2);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("");
	 	System.out.println(":: List<User> 내용 : "+
						productDao.getProductList(search));
	}

	//===========13번 리펙토링====================================================================
	
	//@Test
	public void testAddProduct3() throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						new String[] {	"/config/commonservice.xml",
											"/config/productservice.xml" });
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		
		Product product = new Product();
		product.setProdName("장은애선덕여왕");
		product.setProdDetail("은애는 술을짱잘먹");
		product.setManuDate("20172222");
		product.setPrice(22222);
		product.setFileName("장은애선덕.jsp");
		
		System.out.println(":: 1. addUser(INSERT)  ? ");
		productService.addProduct(product);
		
	}
	
	//@Test
	public void testGetProduct3() throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						new String[] {	"/config/commonservice.xml",
											"/config/productservice.xml" });
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		
		Product product = new Product();
		product.setProdNo(10050);
		
		System.out.println(":: 2. getUser(SELECT)  ? ");		
		System.out.println(productService.getProduct(product.getProdNo()));	
	}
	
	//@Test
	public void testUpdateProduct3() throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						new String[] {	"/config/commonservice.xml",
											"/config/productservice.xml" });
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		
		Product product = new Product();
		product.setProdNo(10050);
		product.setProdName("장은애여장군");
		product.setProdDetail("혼자서 북한돌진가능");
		product.setManuDate("20173333");
		product.setPrice(4444);
		product.setFileName("장은애여장군.jsp");	
		
		System.out.println(":: 3. update(UPDATE)  ? ");
		productService.updateProduct(product);
	}

	//@Test
	public void testGetProductList3() throws Exception {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						new String[] {	"/config/commonservice.xml",
											"/config/productservice.xml" });
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("장은애여장군");
		
	 	System.out.println("list 내용");
	 	System.out.println(productService.getProductList(search));
	 	
	}

	//============14번 리펙토링==========================================================

	@Test
	public void testAddProduct4() throws Exception {
		
		Product product = new Product();
		product.setProdName("다은찡");
		product.setProdDetail("다은찡찡");
		product.setManuDate("2017-06-66");
		product.setPrice(3213);
		product.setFileName("다은찡찡.jsp");
		
		productService.addProduct(product);
		
		//product = productService.getProduct();
	}

	//@Test
	public void testGetProduct4() throws Exception {
		Product product = new Product();
		
		product = productService.getProduct(10051);
		
		System.out.println(product);
	}
	
	//@Test
	public void testUpdateProduct4() throws Exception {
		Product product = productService.getProduct(10051);
		Assert.assertEquals("방장님",product.getProdName());
		
		product.setProdName("성환이형님");
		product.setProdDetail("비트 방장님");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10051);
		System.out.println(product);
	}

	//@Test
	public void testGetProductList4() throws Exception {
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);

	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	}
}
	