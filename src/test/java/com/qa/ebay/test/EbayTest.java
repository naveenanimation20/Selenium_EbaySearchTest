package com.qa.ebay.test;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.ui.util.TestBase;


public class EbayTest extends TestBase {
	TestBase testBase;
	
	/*
	 * Page Factory Objects for Search Page
	 */
	@FindBy(how=How.ID, using="gh-ac")
	public WebElement searchTxt;
	
	@FindBy(how=How.ID, using="gh-btn")
	public WebElement searchBtn;
	
	@FindBy(how=How.LINK_TEXT, using="Best Match")
	public WebElement bestMatchLink;
	
	@FindBy(how=How.LINK_TEXT, using="Price + Shipping: lowest first")
	public WebElement lowerFirstLink;
	
	@FindBy(how=How.LINK_TEXT, using="Price + Shipping: highest first")
	public WebElement higherFirstLink;
	
	@FindBy(how=How.XPATH, using="//ul[@id='ListViewInner']/li//h3//a")
	public WebElement itemDescription;
	
	SoftAssert softAssert = new SoftAssert();

	

	/*
	 * setUp method: Initializing all the pre-requisites for the test methods
	 */
	@BeforeMethod
	public void setUp(){
		testBase = new TestBase();
		TestBase.initialize();
		PageFactory.initElements(driver, this);
	}
	
	
	/*
	 * This test case is validating search results on the basis of <searchkeyword>. It will display the total number of results on the basis of <totalItemNumber> passed.
	 * This test cases is parameterized with two params: <SearchKeyWord> and <totalItemNumber> configured in testng.xml file.
	 * Displaying data from lower to higher price
	 */
	@Test
	@Parameters({"SearchKeyWord","TotalItemNumber"})
	public void ebaySearch_LowerToHigherPriceTest(String searchKeyWord, int totalItemNumber){
		System.out.println("search keyword:"+searchKeyWord + " and " + "total results required:"+ totalItemNumber );
		sendKeyValue(driver, searchTxt, TIMEOUT, searchKeyWord);
		clickOn(driver, searchBtn, TIMEOUT);
		getWebElement(driver, bestMatchLink, TIMEOUT).click();
		getWebElement(driver, lowerFirstLink, TIMEOUT).click();		
		
		List<String> itemsList = getItemsNameList(totalItemNumber);
		System.out.println(itemsList);
		for(String name : itemsList){
			System.out.println(name);
			softAssert.assertTrue(name.toUpperCase().indexOf(searchKeyWord.toUpperCase())>=0);
		}
		softAssert.assertAll();
		
		List<Double> itemsPrice = getItemsPriceList(totalItemNumber);
		System.out.println(itemsPrice);
		for(Double price : itemsPrice){
			System.out.println(price);
		}
		
		Assert.assertTrue(isSorted(itemsPrice));
		
	}
	
	
	/*
	 * This test case is validating search results on the basis of <searchkeyword>. It will display the total number of results on the basis of <totalItemNumber> passed.
	 * This test cases is parameterized with two params: <SearchKeyWord> and <totalItemNumber> configured in testng.xml file.
	 * Displaying data from higher to lower price
	 */
	@Test
	@Parameters({"SearchKeyWord","TotalItemNumber"})
	public void ebaySearch_HigherToLowerPriceTest(String searchKeyWord, int totalItemNumber){
		System.out.println("search keyword:"+searchKeyWord + " and " + "total results required:"+ totalItemNumber );
		sendKeyValue(driver, searchTxt, TIMEOUT, searchKeyWord);
		clickOn(driver, searchBtn, TIMEOUT);
		getWebElement(driver, bestMatchLink, TIMEOUT).click();
		getWebElement(driver, higherFirstLink, TIMEOUT).click();		

		List<String> itemsList = getItemsNameList(totalItemNumber);
		System.out.println(itemsList);
		for(String name : itemsList){
			System.out.println(name);
			softAssert.assertTrue(name.toUpperCase().indexOf(searchKeyWord.toUpperCase())>=0);
		}
		softAssert.assertAll();

		List<Double> itemsPrice = getItemsPriceList(totalItemNumber);
		System.out.println(itemsPrice);
		for(Double price : itemsPrice){
			System.out.println(price);
		}
		
		Collections.reverse(itemsPrice);
		Assert.assertTrue(isSorted(itemsPrice));
		
	}
	
	/*
	 * tear down method: closing the browser after every test case
	 */
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	
	
	

}
