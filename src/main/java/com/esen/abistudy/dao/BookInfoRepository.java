package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;
/**
 * 操作BOOK_INFO表的持久层
 * @author yangk
 * @since 2019年8月28日
 */
@ApplicationRepository
public class BookInfoRepository extends AbstractRepository<BookInfoEntity> {

}
