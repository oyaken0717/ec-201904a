package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.repository.ItemRepository;

@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> findByName(String name){
		return itemRepository.findByName(name);
	}
	
	public Item load(Integer id) {
		return itemRepository.load(id);
	}
	
	public List<Item> sort(String sort){
		return itemRepository.sort(sort);
	}
}
