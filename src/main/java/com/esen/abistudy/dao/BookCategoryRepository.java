package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.BookCategoryEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;

/**
 * 对应主分类表的持久层
 * @author yangk
 * @since 2019年8月30日
 */
@ApplicationRepository
public class BookCategoryRepository extends AbstractRepository<BookCategoryEntity> {

}
