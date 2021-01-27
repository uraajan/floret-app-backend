package com.floret.floretappbackend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.cache.StoreCache;
import com.floret.floretappbackend.entity.StoreVO;

@RestController
//@CrossOrigin(origins = "${rest.cross.origin.exception.dns}")
@CrossOrigin(origins = "*")
public class HomeController {

	@Autowired
	private StoreCache storeCache;

	@GetMapping(path = "/home")
	public ResponseEntity<Map<Integer, StoreVO>> loadHomepage() {
		Map<Integer, StoreVO> storeMap = storeCache.get_storeMap();
		return ResponseEntity.ok(storeMap);
	}

}
