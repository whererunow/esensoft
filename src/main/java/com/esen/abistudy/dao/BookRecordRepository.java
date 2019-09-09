package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.BookRecordEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;

/**
 * 对应图书借阅记录表的持久层
 * @author yangk
 * @since 2019年9月6日
 */
@ApplicationRepository
public class BookRecordRepository extends AbstractRepository<BookRecordEntity> {

}
