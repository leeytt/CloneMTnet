package com.leeyunt.clonemtnet.service.implement;

import com.leeyunt.clonemtnet.entity.Article;
import com.leeyunt.clonemtnet.dao.ArticleDao;
import com.leeyunt.clonemtnet.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发布号作者表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class ArticleServiceImplement extends ServiceImpl<ArticleDao, Article> implements ArticleService {

}
