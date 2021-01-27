package com.floret.floretappbackend.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.floret.floretappbackend.constants.FloretConstants;
import com.floret.floretappbackend.entity.StoreVO;

@Repository
public class StoreCache {

	private static Map<Integer, StoreVO> _storeMap = new HashMap<Integer, StoreVO>();

	private static int productId = 0;

	static {

		StoreVO tomatoSoapVO = new StoreVO(++productId, FloretConstants.PRODUCT_NAME_TOMATO, 100, 10, 90, FloretConstants.DRY, 2);
		_storeMap.put(productId, tomatoSoapVO);

		StoreVO gingerSoapVO = new StoreVO(++productId, FloretConstants.PRODUCT_NAME_GINGER, 110, 15, 94, FloretConstants.OILY, 4);
		_storeMap.put(productId, gingerSoapVO);

		StoreVO jasmineSoapVO = new StoreVO(++productId, FloretConstants.PRODUCT_NAME_JASMINE, 120, 20, 96, FloretConstants.DRY, 0);
		_storeMap.put(productId, jasmineSoapVO);

		StoreVO turmericSoapVO = new StoreVO(++productId, FloretConstants.PRODUCT_NAME_TURMERIC, 130, 25, 98, FloretConstants.OILY, 8);
		_storeMap.put(productId, turmericSoapVO);

		StoreVO aloeveraSoapVO = new StoreVO(++productId, FloretConstants.PRODUCT_NAME_ALOEVERA, 140, 30, 98, FloretConstants.DRY, 10);
		_storeMap.put(productId, aloeveraSoapVO);

		System.out.println(tomatoSoapVO);
		System.out.println(gingerSoapVO);
		System.out.println(jasmineSoapVO);
		System.out.println(turmericSoapVO);
		System.out.println(aloeveraSoapVO);

	}

	public StoreVO getStoreVoForProductId(int productId) {
		return _storeMap.get(productId);
	}

	public Map<Integer, StoreVO> get_storeMap() {
		return _storeMap;
	}

	public void addOrUpdateStoreCache(int productId, StoreVO soapVO) {
		_storeMap.put(productId, soapVO);
	}

	public int generateProductId() {
		return ++productId;
	}

}
