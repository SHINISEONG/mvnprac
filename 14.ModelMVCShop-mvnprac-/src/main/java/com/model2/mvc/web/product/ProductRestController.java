package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/api/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("${pageUnit}")
	int pageUnit;
	
	@Value("${pageSize}")
	int pageSize;
	
	///Constructor
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@PostMapping("displaytypes/{displayType}")
	public List<Product> listProduct(@RequestBody Search search, @PathVariable String displayType	) throws Exception {
		if(displayType.equals("list")) {
			search.setPageSize(pageSize);	
		} else if(displayType.equals("thumbnail")){
			search.setPageSize(10000);
		} else {
			search.setPageSize(pageSize);
		}
		
		
		
		Map<String,Object> map = productService.getProductList(search);
		System.out.println("여기는 프로덕트 리스트 내부"+(List<Product>)map.get("list"));
		// Business logic 수행
		return (List<Product>)map.get("list");
	} 
	
	@GetMapping("list/displaytypes/{displayType}")
	public List<Product> listProductGet(@PathVariable String displayType) throws Exception {
		Search search = new Search();
		if(displayType.equals("list")) {
			search.setPageSize(pageSize);	
		} else if(displayType.equals("thumbnail")){
			search.setPageSize(10000);
		} else {
			search.setPageSize(pageSize);
		}
		
		
		
		Map<String,Object> map = productService.getProductList(search);
		System.out.println("여기는 프로덕트 리스트 내부"+(List<Product>)map.get("list"));
		// Business logic 수행
		return (List<Product>)map.get("list");
	}
	
	@GetMapping("detail/productno/{prodNo}")
	public Product getProductDetail(@PathVariable int prodNo) throws Exception {
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping("getProductNameList")
	public List<String> getProductNameList() throws Exception {
		return productService.getProductNameList();
	}
	
	

}
