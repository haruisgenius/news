package com.example.news.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.news.constants.Constant;
import com.example.news.constants.RtnCode;
import com.example.news.entity.Category;
import com.example.news.entity.Group;
import com.example.news.entity.News;
import com.example.news.repository.CategoryDao;
import com.example.news.repository.GroupDao;
import com.example.news.repository.NewsDao;
import com.example.news.service.ifs.NewsService;
import com.example.news.vo.NewsResponse;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	// -------------------------------------

	// ニュースの新規登録
	@Override
	public NewsResponse createNews(String title, String subTitle, LocalDate releaseDate, String category, String content) {
		// タイトル&サブタイトル　チェック
		NewsResponse checkTitleResult = checkTitle(title, subTitle);
		if(checkTitleResult != null) {
			return checkTitleResult;
		}
		
		// リリース日 チェック
		NewsResponse checkReleaseDateResult = checkReleaseDate(releaseDate);
		if(checkReleaseDateResult != null) {
			return checkReleaseDateResult;
		}
		
		NewsResponse checkCategoryResult = checkCategory(category);
		if(checkCategoryResult != null) {
			return checkCategoryResult;
		}

		// TODO
		// カテゴリー　存在かどうか
		Category saveCategory = categoryDao.findByCategoryName(category);
		if(saveCategory == null) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// グループ名称検索
		Group saveGroup = groupDao.findByGroupName(saveCategory.getCategoryGroup());
		
		// 内容　入力したかどうか
		NewsResponse checkContentResult = checkContent(content);
		if(checkContentResult != null) {
			return checkContentResult;
		}
		
		// 閲覧可能　リリース日が登録日より後かどうか
		boolean isOpen = Constant.OpenDefault.getOpenFalse();
		if(releaseDate.isEqual(Constant.Today.getDate())) {
			isOpen = !isOpen;
		}
		
		// 編集時間　編集した時点
//		LocalDateTime updateTime = Constant.Now.getTime();
		
		saveCategory.setAmount(saveCategory.getAmount() + 1);
		saveGroup.setNewsAmount(saveGroup.getNewsAmount() + 1);
		categoryDao.save(saveCategory);
		groupDao.save(saveGroup);
		
		// データベースへの保存
		News news = new News(title, subTitle, releaseDate, saveGroup.getGroupName(), category, 
				content, isOpen, Constant.Now.getTime());
		newsDao.save(news);
		
		return new NewsResponse(news, RtnCode.SUCCESSFUL.getMessage());
	}

	
	// ニュースの編集
	@Override
	public NewsResponse updateNews(Integer newsNumber, String title, String subTitle, LocalDate releaseDate, String category,
			String content) {
		// ニュースナンバー　入力したかどうか
		if(newsNumber == null) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		//　ニュース　存在しているかどうか
		Optional<News> updateNewsOp = newsDao.findById(newsNumber);
		if(!updateNewsOp.isPresent()) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		News updateNews = updateNewsOp.get();
		
		// タイトル&サブタイトル　チェック
		NewsResponse checkTitleResult = checkTitle(title, subTitle);
		if(checkTitleResult != null) {
			return checkTitleResult;
		}
		updateNews.setTitle(title);
		updateNews.setSubTitle(subTitle);
		
		// リリース日　チェック
		NewsResponse checkReleaseDateResult = checkReleaseDate(releaseDate);
		if(checkReleaseDateResult != null) {
			return checkReleaseDateResult;
		}
		updateNews.setReleaseDate(releaseDate);
		
		// カテゴリー　変更
		NewsResponse updateGroupAndCategoryResult = updateGroupAndCategoryData(category, updateNews);
		if(updateGroupAndCategoryResult != null) {
			return updateGroupAndCategoryResult;
		}
		
		// 内容入力したかどうか
		NewsResponse checkContentResult = checkContent(content);
		if(checkContentResult != null) {
			return checkContentResult;
		}
		
		updateNews.setContent(content);
		
		// 閲覧可能のセット
		if(releaseDate.isAfter(Constant.Today.getDate())) {
			updateNews.setOpen(false);
		}
		
		//　編集時間のセット
		updateNews.setUpdateTime(Constant.Now.getTime());
		//　データベースへの保存
		newsDao.save(updateNews);
		
		return new NewsResponse(updateNews, RtnCode.SUCCESSFUL.getMessage());
	}


	
	// ニュースの削除
	@Override
	public NewsResponse deleteNews(List<Integer> deleteNewsNumberList) {
		// 存在かつ開放中かどうか
		List<News> deleteNewsList = newsDao.findAllByNewsNumberInAndIsNewsDeleteFalse(deleteNewsNumberList);
		if(deleteNewsList.size() != deleteNewsNumberList.size()) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// グループ文章数-1
		List<String> deleteGroupNameList = new ArrayList<>();
		for(News deleteNews : deleteNewsList) {
			deleteGroupNameList.add(deleteNews.getNewsGroup());
		}
		List<Group> deleteGroupList = groupDao.findAllByGroupNameInAndIsGroupDeleteFalse(deleteGroupNameList);
		for(Group deleteGroup : deleteGroupList) {
			deleteGroup.setNewsAmount(deleteGroup.getNewsAmount() - 1);
		}
		groupDao.saveAll(deleteGroupList);
		
		// カテゴリー文章数-1
		List<String> deleteCategoryNameList = new ArrayList<>();
		for(News deleteNews : deleteNewsList) {
			deleteCategoryNameList.add(deleteNews.getCategory());
		}
		List<Category> deleteCategoryList = categoryDao.findAllByCategoryNameInAndIsCategoryDeleteFalse(deleteCategoryNameList);
		for(Category deleteCategory : deleteCategoryList) {
			deleteCategory.setAmount(deleteCategory.getAmount() - 1);
		}
		categoryDao.saveAll(deleteCategoryList);
		
		// 削除 > true
		for(News deleteNews : deleteNewsList) {
			deleteNews.setOpen(false);
			deleteNews.setNewsDelete(true);
		}
		newsDao.saveAll(deleteNewsList);
		
		return new NewsResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	// ニュースの内容表示
	@Override
	public NewsResponse findNews(Integer newsNumber) {
		// 入力かどうか
		if(newsNumber == null) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// 検索
		News findNews = newsDao.findByNewsNumberAndIsNewsDeleteFalse(newsNumber);
		if(findNews == null) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		return new NewsResponse(findNews, RtnCode.SUCCESSFUL.getMessage());
	}

	// 全ニュースの表示(開放中&閉鎖) > 管理者
	@Override
	public NewsResponse findAllNews() {
		List<News> allIsExistedNews = newsDao.findAllByIsNewsDeleteFalse();
		return new NewsResponse(allIsExistedNews, RtnCode.SUCCESSFUL.getMessage());
	}

	// ニュースを閉鎖
	@Override
	public NewsResponse closeNews(List<Integer> newsNumberList) {
		// 入力かどうか
		for(Integer newsNumber : newsNumberList) {
			if(newsNumber == null) {
				return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
			}
		}
		
		// 検索
		List<News> allCloseNewsList = newsDao.findAllByNewsNumberInAndIsNewsDeleteFalse(newsNumberList);
		if(allCloseNewsList.size() != newsNumberList.size()) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 閉鎖
		for(News closeNews : allCloseNewsList) {
			closeNews.setOpen(false);
		}
		newsDao.saveAll(allCloseNewsList);
		
		return new NewsResponse(allCloseNewsList, RtnCode.SUCCESSFUL.getMessage());
	}
	

	// ニュースを開放
	@Override
	public NewsResponse openNews(List<Integer> newsNumberList) {
		// 入力かどうか
		for(Integer newsNumber : newsNumberList) {
			if(newsNumber == null) {
				return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
			}
		}
		
		// 検索
		List<News> allOpenNewsList = newsDao.findAllByNewsNumberInAndIsNewsDeleteFalse(newsNumberList);
		if(allOpenNewsList.size() != newsNumberList.size()) {
			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
		}
		
		// 開放可能確認
		for(News openNews : allOpenNewsList) {
			if(openNews.getReleaseDate().isAfter(Constant.Today.getDate())) {
				return new NewsResponse(RtnCode.INCORRECT.getMessage());
			}
			openNews.setOpen(true);
		}
		newsDao.saveAll(allOpenNewsList);
		
		return new NewsResponse(allOpenNewsList, RtnCode.SUCCESSFUL.getMessage());
	}

	// ニュースを開放
	@Override
	public NewsResponse autoSetOpen() {
		List<News> allNews = newsDao.findAllByIsNewsDeleteFalse();
		
		// リリース日付確認
		for(News news : allNews) {
			if(!news.getReleaseDate().isAfter(Constant.Today.getDate())) {
				news.setOpen(true);
			}
		}
		
		newsDao.saveAll(allNews);
		
		return new NewsResponse(allNews, RtnCode.SUCCESSFUL.getMessage());
	}

	// -------------------------------------
	
	// タイトルチェック
	private NewsResponse checkTitle(String title, String subTitle) {
		// 入力かどうか
		if(!StringUtils.hasText(title) || !StringUtils.hasText(subTitle)) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		return null;
	}
	
	// リリース日チェック
	private NewsResponse checkReleaseDate(LocalDate releaseDate) {
		// 入力かどうか
		if(releaseDate == null) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// リリース日　本日より前かどうか
		if(releaseDate.isBefore(Constant.Today.getDate())) {
			return new NewsResponse(RtnCode.INCORRECT.getMessage());
		}
		
		return null;
	}
	
	// カテゴリーチェック
	private NewsResponse checkCategory(String category) {
		// 入力かどうか
		if(!StringUtils.hasText(category)) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// カテゴリー　存在かどうか
//		Category saveCategory = categoryDao.findByCategoryName(category);
//		if(saveCategory == null) {
//			return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
//		}
		
		return null;
	}
	
	// 内容チェック
	private NewsResponse checkContent(String content) {
		// 入力かどうか
		if(!StringUtils.hasText(content)) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		return null;
	}
	
	// カテゴリー&グループチェック
	private NewsResponse updateGroupAndCategoryData(String newCategory, News oldNews) {
		// 入力かどうか
		if(!StringUtils.hasText(newCategory)) {
			return new NewsResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		
		// カテゴリー変更の場合
		if(!newCategory.equals(oldNews.getCategory())) {
			// カテゴリー名称リスト
			List<String> categoryNameStringList = new ArrayList<>(Arrays.asList(newCategory, oldNews.getCategory()));
			// 変更カテゴリーリスト
			List<Category> updateCategoryList = categoryDao.findAllByCategoryNameInAndIsCategoryDeleteFalse(categoryNameStringList);
			// カテゴリー存在しない
			if(updateCategoryList.size() != categoryNameStringList.size()) {
				return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
			}
			
			// グループ名称リスト
			List<String> groupNameStringList = new ArrayList<>();
			
			// 変更グループのリスト作成
			for(Category updateCategory : updateCategoryList) {
				groupNameStringList.add(updateCategory.getCategoryGroup());
			}
			List<Group> updateGroupList = groupDao.findAllByGroupNameInAndIsGroupDeleteFalse(groupNameStringList);
			// グループ存在しない
			if(updateGroupList.size() != groupNameStringList.size()) {
				return new NewsResponse(RtnCode.NOT_FOUND.getMessage());
			}
			
			for(Category updateCategory : updateCategoryList) {
				// old category文章数
				if(updateCategory.getCategoryName().equals(oldNews.getCategory())) {
					updateCategory.setAmount(updateCategory.getAmount() - 1);
					// old group文章数
					for(Group updateGroup : updateGroupList) {
						if(updateGroup.getGroupName().equals(updateCategory.getCategoryGroup())) {
							updateGroup.setNewsAmount(updateGroup.getNewsAmount() - 1);
						}
					}
				}
				
				// new category文章数
				if(updateCategory.getCategoryName().equals(newCategory)) {
					updateCategory.setAmount(updateCategory.getAmount() + 1);
					
					// new group文章数
					for(Group updateGroup : updateGroupList) {
						if(updateGroup.getGroupName().equals(updateCategory.getCategoryGroup())) {
							updateGroup.setNewsAmount(updateGroup.getNewsAmount() + 1);
							oldNews.setNewsGroup(updateGroup.getGroupName());
						}
					}

					oldNews.setCategory(newCategory);
				}				
			}
			
			// データベース保存
			categoryDao.saveAll(updateCategoryList);
			groupDao.saveAll(updateGroupList);
			
		}
		
		return null;
	}
	
	
	
	
}
