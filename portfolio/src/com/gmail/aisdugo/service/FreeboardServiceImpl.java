package com.gmail.aisdugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.aisdugo.dao.FreeboardDao;

@Service
public class FreeboardServiceImpl implements FreeboardService {

	@Autowired
	private FreeboardDao freeboardDao;
	
}
