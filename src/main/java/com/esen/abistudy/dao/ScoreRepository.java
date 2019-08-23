package com.esen.abistudy.dao;

import com.esen.abistudy.orm.entity.ScoreEntity;
import com.esen.ecore.annotation.ApplicationRepository;
import com.esen.ecore.repository.AbstractRepository;
/**
 * 操作score表的实体类
 * @author yangk
 * @since 2019年8月19日
 */
@ApplicationRepository(path="/config/mapping/scoreentity-mapping.xml")
public class ScoreRepository extends AbstractRepository<ScoreEntity>{
	
}
