package com.example.ecommerce_a.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.LoginUser;
import com.example.ecommerce_a.form.SortForm;
import com.example.ecommerce_a.service.ItemService;

/**
 * Itemを操作するコントローラクラス.
 * 
 * @author sho.ikehara
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	ItemService itemService;
	/** 表示する最大の列の数 */
	private static final int MAX_COLS = 3;
	
	@ModelAttribute
	public SortForm setUpForm() {
		return new SortForm();
	}

	/**
	 * 全件検索またはあいまい検索.
	 * 
	 * @param model モデル
	 * @param name  入力した名前
	 * @param page  押されたページ
	 * @return 商品一覧ページ
	 */
	@RequestMapping("/showList")
	public String showList(Model model, String name, @AuthenticationPrincipal LoginUser loginUser) {
		// ログイン情報をコントローラで取得するサンプル
		try {
			System.out.println(loginUser.getUser().getName() + "さんがログイン中");
		} catch (NullPointerException e) {
			System.err.println("誰もログインしていません");
		}

		if (name == null) {
			name = "";
		}
		List<String> nameList = itemService.itemAllName();
		List<Item> itemList = itemService.findByName(name);
		List<Item> list = new ArrayList<>();
		List<List<Item>> listList = new ArrayList<>();
		for(int i=0;i<itemList.size();i++) {
			if( i%MAX_COLS==0 ) {
				list = new ArrayList<>();
				listList.add(list);
			}
			list.add(itemList.get(i));
		}
		model.addAttribute("listList", listList);
		model.addAttribute("nameList", nameList);
		return "item_list";
	}

	/**
	 * 指定した並び順に表示内容を変更.
	 * 
	 * @param model モデル
	 * @param sort  ソートする順番の指定
	 * @param page  押されたページ
	 * @return 商品一覧ページ
	 */
	@RequestMapping("/sort")
	public String sort(SortForm form,Model model) {
		List<Item> itemList = itemService.sort(form.getSortName());
		List<String> nameList = itemService.itemAllName();
		List<Item> list = new ArrayList<>();
		List<List<Item>> listList = new ArrayList<>();
		for (int i = 0; i < itemList.size(); i++) {
			list.add(itemList.get(i));
			if ((i + 1) % MAX_COLS == 0) {
				listList.add(list);
				list = new ArrayList<>();
			}
		}
		model.addAttribute("listList", listList);
		model.addAttribute("nameList", nameList);
		return "item_list";
	}
}