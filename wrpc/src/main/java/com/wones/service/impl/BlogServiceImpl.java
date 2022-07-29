package com.wones.service.impl;

import com.wones.common.Blog;
import com.wones.service.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogByBid(int id) {
        Blog blog = new Blog(id,"wrpc","手写RPC框架");
        return blog;
    }
}
