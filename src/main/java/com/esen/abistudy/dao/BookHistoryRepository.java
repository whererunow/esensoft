package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.BookHistoryEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;
/**
 * 操作BOOK_HISTORY表的持久层
 * @author yangk
 * @since 2019年8月28日
 */
@ApplicationRepository
public class BookHistoryRepository extends AbstractRepository<BookHistoryEntity> {

}
