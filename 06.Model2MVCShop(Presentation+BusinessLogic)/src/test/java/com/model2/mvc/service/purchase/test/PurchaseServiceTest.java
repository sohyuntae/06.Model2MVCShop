package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Product product = new Product();
		product.setProdNo(10046);
		
		User user = new User();
		user.setUserId("testUserId");
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption("2");
		purchase.setReceiverName("정채연");
		purchase.setReceiverPhone("010212122121"); 
		purchase.setDivyAddr("다이스");
		purchase.setDivyRequest("센터");
		purchase.setDivyDate("17/11/16");
		
		
		purchaseService.addPurchase(purchase);
		System.out.println(purchase);
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase(10020);
		
		System.out.println(purchase);
	}
	
	//@Test
	public void testGetPurchase2() throws Exception {
		Purchase purchase = new Purchase();
		purchase = purchaseService.getPurchase2(10000);
		
		System.out.println(purchase);
	}

	//@Test
	public void testUpdateTranCode() throws Exception {
		Purchase purchase = purchaseService.getPurchase2(10020);
		purchase.setTranCode("3");
		purchaseService.updateTranCode(purchase);
		
		purchase = purchaseService.getPurchase2(10020);
		System.out.println(purchase);
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception {
		Purchase purchase = purchaseService.getPurchase(10020);
		Assert.assertEquals("정수빈",purchase.getReceiverName());
		
		purchase.setReceiverName("정수빈씨");
		purchase.setDivyRequest("두산이탈했습니다.");
		
		purchaseService.updatePurcahse(purchase);
		
		purchase = purchaseService.getPurchase(10020);
		System.out.println(purchase);
	}
	
	@Test
	public void testGetPurchaseList() throws Exception {
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	//Purchase purchase = new Purchase();
	 	//String buyerId = purchase.getBuyer().getUserId();
	 	
	 	Map<String,Object> map = purchaseService.getPurchaseList(search,"user01");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(1, list.size());
	 	
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);

	}

}
	