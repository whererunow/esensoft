package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.BookInfoEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;

/**
 * 对应图书信息表的持久层
 * @author yangk
 * @since 2019年9月6日
 */
@ApplicationRepository
public class BookInfoRepository extends AbstractRepository<BookInfoEntity> {

}
